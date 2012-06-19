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
package com.chute.sdk.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.util.Log;

import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;
import com.chute.sdk.utils.rest.GCParametersRestClient;

public class GCHttpRequestParameters {

	public static final String TAG = GCHttpRequestParameters.class
			.getSimpleName();

	private final String url;
	private final ArrayList<NameValuePair> params;
	private final ArrayList<NameValuePair> headers;
	private final RequestMethod requestMethod;

	public GCHttpRequestParameters(final String url,
			RequestMethod requestMethod, final ArrayList<NameValuePair> params,
			final ArrayList<NameValuePair> headers) {
		this.url = url;
		this.requestMethod = requestMethod;
		this.params = params;
		this.headers = headers;
	}

	public GCHttpRequestParameters(String url,
			ArrayList<NameValuePair> headers, RequestMethod requestMethod) {
		super();
		this.url = url;
		this.headers = headers;
		this.params = null;
		this.requestMethod = requestMethod;
	}

	public String getUrl() {
		return url;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public ArrayList<NameValuePair> getParams() {
		return params;
	}

	public ArrayList<NameValuePair> getHeaders() {
		return headers;
	}

	public String generateRequestURL() {
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		try {
			sb.append(GCParametersRestClient.generateParametersString(params));
		} catch (UnsupportedEncodingException e) {
			Log.w(TAG, "", e);
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCRequestParameters [url=");
		builder.append(url);
		builder.append(", params=");
		builder.append(params);
		builder.append(", headers=");
		builder.append(headers);
		builder.append("]");
		return builder.toString();
	}

}
