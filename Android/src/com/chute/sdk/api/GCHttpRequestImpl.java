// Copyright (c) 2011, Chute Corporation. All rights reserved.
// 
//  Redistribution and use in source and binary forms, with or without modification, 
//  are permitted provided that the following conditions are met:
// 
//     * Redistributions of source code must retain the above copyright notice, this 
//       list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright notice,
//       this list of conditions and the following disclaimer in the documentation
//       and/or other materials provided with the distribution.
//     * Neither the name of the  Chute Corporation nor the names
//       of its contributors may be used to endorse or promote products derived from
//       this software without specific prior written permission.
// 
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.
// 
package com.chute.sdk.api;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCConstants;
import com.chute.sdk.utils.GCRest;
import com.chute.sdk.utils.GCRest.RequestMethod;

public abstract class GCHttpRequestImpl<T> implements GCHttpRequest {

    public static final String TAG = GCHttpRequestImpl.class.getSimpleName();

    protected GCRest client = new GCRest();
    private final RequestMethod requestMethod;
    private final Context context;

    private static Handler handler;

    private final GCHttpResponseParser<T> parser;
    private final GCHttpCallback<T> callback;

    public GCHttpRequestImpl(Context context, RequestMethod requestMethod,
	    GCHttpResponseParser<T> parser, GCHttpCallback<T> callback) {
	super();
	this.parser = parser;
	this.callback = callback;
	this.context = context.getApplicationContext();
	this.requestMethod = requestMethod;
	if (handler == null) {
	    handler = new Handler(context.getMainLooper());
	}
    }

    public Context getContext() {
	return context;
    }

    public RequestMethod getRequestMethod() {
	return requestMethod;
    }

    protected GCHttpCallback<T> getCallback() {
	return callback;
    }

    protected GCHttpResponseParser<T> getParser() {
	return parser;
    }

    protected static Handler getHandler() {
	return handler;
    }

    /** @param connectionTimeout
     *            default 6 seconds */
    public final void setConnectionTimeout(int connectionTimeout) {
	client.setConnectionTimeout(connectionTimeout);
    }

    /** @param socketTimeout
     *            default 9 seconds */
    public final void setSocketTimeout(int socketTimeout) {
	client.setSocketTimeout(socketTimeout);
    }

    public final void addParam(String name, String value) {
	client.addParam(name, value);
    }

    protected void runRequest(final String url) {

	try {
	    client.setUrl(url);
	    client.setAuthentication(GCAccountStore.getInstance(context));
	    prepareParams();
	    client.execute(requestMethod);
	    // Execute the HTTP request
	} catch (final Exception e) {
	    handler.post(new Runnable() {
		@Override
		public void run() {
		    if (callback != null) {
			callback.onHttpException(client.getRequestParameters(), e);
		    }
		}
	    });
	    return;
	}

	if (GCConstants.DEBUG) {
	    Log.d(TAG, client.getResponseCode() + " " + client.getErrorMessage());
	    Log.d(TAG, client.getResponse());
	}
	if (client.getResponseCode() < 200 || client.getResponseCode() > 300) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG,
			"Http Error " + client.getResponseCode() + " " + client.getErrorMessage());
	    }
	    handler.post(new Runnable() {
		@Override
		public void run() {
		    if (callback != null) {
			callback.onHttpError(client.getResponseCode(), client.getErrorMessage());
		    }
		}
	    });
	    return;
	}

	try {
	    final T responseData = parser.parse(client.getResponse());
	    handler.post(new Runnable() {
		@Override
		public void run() {
		    if (callback != null) {
			callback.onSuccess(responseData);
		    }
		}
	    });
	} catch (final Exception e) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG, "Parser Error", e);
	    }
	    handler.post(new Runnable() {
		@Override
		public void run() {
		    if (callback != null) {
			callback.onParserException(client.getResponseCode(), e);
		    }
		}
	    });
	}

    }

    @Override
    public void executeAsync() {
	GCHttpRequestStore.getInstance(context).launchServiceIntent(this);
    }

    protected abstract void prepareParams();
}
