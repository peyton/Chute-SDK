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
package com.chute.sdk.utils.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

import com.chute.sdk.exceptions.GCHttpException;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.utils.Logger;

/**
 * <pre>
 * ChuteRestClient client = new ChuteRestClient(LOGIN_URL);
 * client.addParam(&quot;accountType&quot;, &quot;GOOGLE&quot;);
 * client.addParam(&quot;source&quot;, &quot;tboda-widgalytics-0.1&quot;);
 * client.addParam(&quot;Email&quot;, _username);
 * client.addParam(&quot;Passwd&quot;, _password);
 * client.addParam(&quot;service&quot;, &quot;analytics&quot;);
 * client.addHeader(&quot;GData-Version&quot;, &quot;2&quot;);
 * 
 * try {
 * 	client.execute(RequestMethod.POST);
 * } catch (Exception e) {
 * 	Log.d(TAG, &quot;&quot;, e);
 * }
 * 
 * String response = client.getResponse();
 * </pre>
 * 
 * @author darko grozdanovski
 */
public class GCParametersRestClient extends GCBaseRestClient {

	private static final String TAG = GCParametersRestClient.class
			.getSimpleName();

	private final ArrayList<NameValuePair> params;

	private RequestMethod method;

	public ArrayList<NameValuePair> getParams() {
		return params;
	}

	public GCParametersRestClient() {
		super();
		params = new ArrayList<NameValuePair>();
	}

	public void addParam(final String name, final String value) {
		if (TextUtils.isEmpty(value) == false) {
			params.add(new BasicNameValuePair(name, value));
		}
	}

	public GCHttpRequestParameters getRequestParameters() {
		return new GCHttpRequestParameters(getUrl(), method, params,
				getHeaders());
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public void execute() throws GCHttpException {
		try {
			switch (method) {
			case GET: {
				final HttpGet request = new HttpGet(getUrl()
						+ generateParametersString(params));
				executeRequest(request);
				break;
			}
			case POST: {
				final HttpPost request = new HttpPost(getUrl());
				if (!params.isEmpty()) {
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
				}
				executeRequest(request);
				break;
			}
			case PUT: {
				final HttpPut request = new HttpPut(getUrl());
				if (!params.isEmpty()) {
					request.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
				}
				executeRequest(request);
				break;
			}
			case DELETE: {
				final HttpDelete request = new HttpDelete(getUrl()
						+ generateParametersString(params));
				executeRequest(request);
				break;
			}
			}
		} catch (final UnsupportedEncodingException e) {
			Logger.w(TAG, "", e);
			throw new GCHttpException(e);
		} catch (final IOException e) {
			Logger.w(TAG, "", e);
			throw new GCHttpException(e);
		}
	}

	public static String generateParametersString(
			final ArrayList<NameValuePair> params)
			throws UnsupportedEncodingException {
		// add parameters
		String combinedParams = "";
		if (params != null && !params.isEmpty()) {
			combinedParams += "?";
			for (final NameValuePair p : params) {
				final String paramString = p.getName() + "="
						+ URLEncoder.encode(p.getValue(), "UTF-8");
				if (combinedParams.length() > 1) {
					combinedParams += "&" + paramString;
				} else {
					combinedParams += paramString;
				}
			}
		}
		return combinedParams;
	}
}
