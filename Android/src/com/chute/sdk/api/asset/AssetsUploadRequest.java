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
package com.chute.sdk.api.asset;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.GCHttpRequestImpl;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.model.GCAssetModel;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.model.GCUploadToken;
import com.chute.sdk.parsers.GCTokenObjectParser;
import com.chute.sdk.parsers.GCUploadCompleteObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCConstants;
import com.chute.sdk.utils.GCRest.RequestMethod;

public class AssetsUploadRequest<T> extends GCHttpRequestImpl<T> {
    private final class TokenListener implements GCHttpCallback<GCUploadToken> {

	@Override
	public void onSuccess(GCUploadToken responseData) {
	    uploadToken = responseData;
	    resumeThread();
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	    try {
		obj.put("code", responseCode);
	    } catch (JSONException e) {
		Log.w(TAG, "", e);
	    }
	    resumeThread();
	}

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    try {
		obj.put("code", HttpStatus.SC_REQUEST_TIMEOUT);
	    } catch (JSONException e) {
		Log.w(TAG, "", e);
	    }
	    resumeThread();
	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	    try {
		obj.put("code", responseCode);
	    } catch (JSONException e) {
		Log.w(TAG, "", e);
	    }
	    resumeThread();
	}
    }

    private final class UploadCompleteListener implements GCHttpCallback<GCAssetModel> {

	@Override
	public void onSuccess(GCAssetModel responseData) {
	    try {
		obj.put("url", responseData.getUrl());
		obj.put("code", HttpStatus.SC_OK);
	    } catch (JSONException e) {
		Log.w(TAG, "", e);
	    }
	}

	@Override
	public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
	    try {
		obj.put("code", HttpStatus.SC_REQUEST_TIMEOUT);
	    } catch (JSONException e1) {
		Log.w(TAG, "", e1);
	    }
	}

	@Override
	public void onHttpError(int responseCode, String statusMessage) {
	    try {
		obj.put("code", responseCode);
	    } catch (JSONException e1) {
		Log.w(TAG, "", e1);
	    }
	}

	@Override
	public void onParserException(int responseCode, Throwable exception) {
	    try {
		obj.put("code", responseCode);
	    } catch (JSONException e1) {
		Log.w(TAG, "", e1);
	    }
	}
    }

    @SuppressWarnings("unused")
    private static final String TAG = AssetsUploadRequest.class.getSimpleName();
    private final GCLocalAssetCollection assetIds;
    private final GCUploadProgressListener onProgressUpdate;
    private JSONArray array;
    private JSONObject obj = new JSONObject();
    private GCUploadToken uploadToken;

    public void resumeThread() {
	synchronized (this) {
	    Log.w(TAG, "Resumed thread");
	    this.notifyAll();
	}

    }

    public AssetsUploadRequest(Context context, GCUploadProgressListener onProgressUpdate,
	    GCHttpResponseParser<T> parser, GCHttpCallback<T> callback,
	    GCLocalAssetCollection assetCollection) {
	super(context, RequestMethod.POST, parser, callback);
	if (assetCollection == null || assetCollection.size() == 0) {
	    throw new NullPointerException(
		    "Need to provide an array with at least 1 item (Not NULL)");
	}
	this.onProgressUpdate = onProgressUpdate;
	this.assetIds = assetCollection;
    }

    @Override
    protected void prepareParams() {
    }

    @Override
    public void execute() {
	final GCS3Uploader uploader = new GCS3Uploader(onProgressUpdate);
	array = new JSONArray();

	for (GCLocalAssetModel asset : assetIds) {
	    obj = new JSONObject();
	    try {
		obj.put("assetId", asset);
		GCHttpRequest token = getToken(getContext(), asset.getAssetId(),
			new TokenListener());

		synchronized (this) {
		    Log.w(TAG, "Wait thread");
		    token.execute();
		    this.wait(4000);
		}
		if (uploadToken != null) {
		    uploader.startUpload(uploadToken);
		    uploadComplete(getContext(), uploadToken.getAssetId(),
			    new UploadCompleteListener()).execute();
		} else {
		    if (GCConstants.DEBUG) {
			Log.e(TAG, "TOKEN IS NULL");
		    }
		}
	    } catch (Exception e) {
		if (GCConstants.DEBUG) {
		    Log.e(TAG, "Error in the Upload Request", e);
		}
	    } finally {
		array.put(obj);
	    }
	}
	try {
	    getCallback().onSuccess(getParser().parse(array.toString()));
	} catch (JSONException e) {
	    Log.w(TAG, "", e);
	    getCallback().onParserException(HttpStatus.SC_BAD_REQUEST, e);
	}

    }

    public static GCHttpRequest getToken(final Context context, final String assetId,
	    GCHttpCallback<GCUploadToken> callback) {
	return getToken(context, assetId, new GCTokenObjectParser(), callback);
    }

    public static GCHttpRequest uploadComplete(final Context context, final String assetId,
	    GCHttpCallback<GCAssetModel> callback) {
	return uploadComplete(context, assetId, new GCUploadCompleteObjectParser(), callback);
    }

    public static <T> GCHttpRequest getToken(final Context context, final String id,
	    final GCHttpResponseParser<T> parser, final GCHttpCallback<T> callback) {
	return new AssetsTokenRequest<T>(context, id, parser, callback);
    }

    public static <T> GCHttpRequest uploadComplete(final Context context, final String id,
	    final GCHttpResponseParser<T> parser, final GCHttpCallback<T> callback) {
	return new AssetsUploadCompleteRequest<T>(context, id, parser, callback);
    }
}
