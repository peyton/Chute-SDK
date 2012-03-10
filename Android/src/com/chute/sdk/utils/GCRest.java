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
package com.chute.sdk.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.text.TextUtils;
import android.util.Log;

import com.chute.sdk.exceptions.GCHttpException;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCHttpRequestParameters;

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
 *     client.execute(RequestMethod.POST);
 * } catch (Exception e) {
 *     Log.d(TAG, &quot;&quot;, e);
 * }
 * 
 * String response = client.getResponse();
 * </pre>
 * 
 * @author darko grozdanovski
 */
public class GCRest {

    public enum RequestMethod {
	GET, POST, PUT, DELETE
    }

    private static final String TAG = GCRest.class.getSimpleName();

    private final ArrayList<NameValuePair> params;
    private final ArrayList<NameValuePair> headers;

    private String url;

    private int responseCode;
    private String message;

    private String response;

    int connectionTimeout = 8000;
    int socketTimeout = 12000;

    private static DefaultHttpClient client;

    private RequestMethod method;

    public int getConnectionTimeout() {
	return connectionTimeout;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getUrl() {
	return url;
    }

    public void setConnectionTimeout(int connectionTimeout) {
	this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeout() {
	return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
	this.socketTimeout = socketTimeout;
    }

    public void setAuthentication(final GCAccountStore userAccount) {
	if (TextUtils.isEmpty(userAccount.getPassword())) {
	    Log.w(TAG, "Need to place authentication in GCAccount");
	}
	addHeader("Authorization", "OAuth " + userAccount.getPassword());
	headers.addAll(userAccount.getHeaders());
    }

    public String getResponse() {
	return response;
    }

    public String getErrorMessage() {
	return message;
    }

    public int getResponseCode() {
	return responseCode;
    }

    public ArrayList<NameValuePair> getParams() {
	return params;
    }

    public ArrayList<NameValuePair> getHeaders() {
	return headers;
    }

    public GCRest(String url) {
	this.url = url;
	params = new ArrayList<NameValuePair>();
	headers = new ArrayList<NameValuePair>();
    }

    public GCRest() {
	params = new ArrayList<NameValuePair>();
	headers = new ArrayList<NameValuePair>();
    }

    public void addParam(String name, String value) {
	if (TextUtils.isEmpty(value) == false) {
	    params.add(new BasicNameValuePair(name, value));
	}
    }

    public void addHeader(String name, String value) {
	if (TextUtils.isEmpty(value) == false) {
	    headers.add(new BasicNameValuePair(name, value));
	}
    }

    public GCHttpRequestParameters getRequestParameters() {
	return new GCHttpRequestParameters(url, method, params, headers);
    }

    private static DefaultHttpClient getClient() {
	if (client == null) {
	    client = generateClient();
	}
	return client;
    }

    private static DefaultHttpClient generateClient() {
	final HttpParams params = new BasicHttpParams();
	HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
	HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

	// Create and initialize scheme registry
	final SchemeRegistry schemeRegistry = new SchemeRegistry();
	schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

	// Create an HttpClient with the ThreadSafeClientConnManager.
	// This connection manager must be used if more than one thread will
	// be using the HttpClient.
	final ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params,
		schemeRegistry);
	final DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
	return httpClient;
    }

    public void execute(RequestMethod method) throws GCHttpException {
	this.method = method;

	try {
	    switch (method) {
	    case GET: {
		// add parameters
		String combinedParams = "";
		if (!params.isEmpty()) {
		    combinedParams += "?";
		    for (NameValuePair p : params) {
			String paramString = p.getName() + "="
				+ URLEncoder.encode(p.getValue(), "UTF-8");
			if (combinedParams.length() > 1) {
			    combinedParams += "&" + paramString;
			} else {
			    combinedParams += paramString;
			}
		    }
		}

		HttpGet request = new HttpGet(url + combinedParams);

		// add headers
		for (NameValuePair h : headers) {
		    request.addHeader(h.getName(), h.getValue());
		}

		executeRequest(request, url);
		break;
	    }
	    case POST: {
		HttpPost request = new HttpPost(url);

		// add headers
		for (NameValuePair h : headers) {
		    request.addHeader(h.getName(), h.getValue());
		}

		if (!params.isEmpty()) {
		    request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		}

		executeRequest(request, url);
		break;
	    }
	    case PUT: {
		HttpPut request = new HttpPut(url);
		// add headers
		for (NameValuePair h : headers) {
		    request.addHeader(h.getName(), h.getValue());
		}

		if (!params.isEmpty()) {
		    request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		}
		executeRequest(request, url);
		break;
	    }
	    case DELETE: {

		// add parameters
		String combinedParams = "";
		if (!params.isEmpty()) {
		    combinedParams += "?";
		    for (NameValuePair p : params) {
			String paramString = p.getName() + "="
				+ URLEncoder.encode(p.getValue(), "UTF-8");
			if (combinedParams.length() > 1) {
			    combinedParams += "&" + paramString;
			} else {
			    combinedParams += paramString;
			}
		    }
		}
		HttpDelete request = new HttpDelete(url + combinedParams);
		// add headers
		for (NameValuePair h : headers) {
		    request.addHeader(h.getName(), h.getValue());
		}

		executeRequest(request, url);
		break;
	    }
	    }
	} catch (UnsupportedEncodingException e) {
	    if (GCConstants.DEBUG) {
		Log.w(TAG, "", e);
	    }
	    throw new GCHttpException(e);
	} catch (IOException e) {
	    if (GCConstants.DEBUG) {
		Log.w(TAG, "", e);
	    }
	    throw new GCHttpException(e);
	}
    }

    private void executeRequest(HttpUriRequest request, String url) throws IOException {

	HttpParams httpParameters = new BasicHttpParams();
	// Set the timeout in milliseconds until a connection is established.
	HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeout);
	// Set the default socket timeout (SO_TIMEOUT)
	// in milliseconds which is the timeout for waiting for data.
	HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);

	getClient().setParams(httpParameters);
	HttpResponse httpResponse;
	InputStream instream = null;
	try {

	    httpResponse = getClient().execute(request);
	    responseCode = httpResponse.getStatusLine().getStatusCode();

	    message = httpResponse.getStatusLine().getReasonPhrase();

	    HttpEntity entity = httpResponse.getEntity();

	    if (entity != null) {

		instream = entity.getContent();
		response = convertStreamToString(instream);

		// Closing the input stream will trigger connection release
		instream.close();
	    }

	} catch (IOException e) {
	    throw e;
	} catch (RuntimeException ex) {
	    // In case of an unexpected exception you may want to abort
	    // the HTTP request in order to shut down the underlying
	    // connection immediately.
	    request.abort();
	    throw ex;
	} finally {
	    // Closing the input stream will trigger connection release
	    // getClient().getConnectionManager().shutdown();
	    try {
		instream.close();
	    } catch (Exception ignore) {
	    }
	}
    }

    private static String convertStreamToString(InputStream is) throws IOException {

	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	StringBuilder sb = new StringBuilder();

	String line = null;
	try {
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	} catch (IOException e) {
	    throw e;
	} finally {
	    try {
		is.close();
	    } catch (IOException e) {
		throw e;
	    }
	}
	return sb.toString();
    }
}
