package com.chute.sdk.utils.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.text.TextUtils;
import android.util.Log;

import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.utils.GCStreamUtil;

public abstract class GCBaseRestClient implements GCRest {

	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}

	private static final String TAG = GCParametersRestClient.class
			.getSimpleName();

	private static DefaultHttpClient client;

	private final ArrayList<NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;
	private String response;

	int connectionTimeout = 8000;
	int socketTimeout = 12000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getConnectionTimeout()
	 */
	@Override
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#setUrl(java.lang.String)
	 */
	@Override
	public void setUrl(final String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#setConnectionTimeout(int)
	 */
	@Override
	public void setConnectionTimeout(final int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getSocketTimeout()
	 */
	@Override
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#setSocketTimeout(int)
	 */
	@Override
	public void setSocketTimeout(final int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chute.sdk.utils.rest.GCRest#setAuthentication(com.chute.sdk.model
	 * .GCAccountStore)
	 */
	@Override
	public void setAuthentication(final GCAccountStore userAccount) {
		if (TextUtils.isEmpty(userAccount.getPassword())) {
			Log.w(TAG, "Need to place authentication in GCAccount");
		}
		addHeader("Authorization", "OAuth " + userAccount.getPassword());
		headers.addAll(userAccount.getHeaders());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getResponse()
	 */
	@Override
	public String getResponse() {
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getResponseCode()
	 */
	@Override
	public int getResponseCode() {
		return responseCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#getHeaders()
	 */
	@Override
	public ArrayList<NameValuePair> getHeaders() {
		return headers;
	}

	public GCBaseRestClient() {
		headers = new ArrayList<NameValuePair>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.utils.rest.GCRest#addHeader(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addHeader(final String name, final String value) {
		if (TextUtils.isEmpty(value) == false) {
			headers.add(new BasicNameValuePair(name, value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chute.sdk.utils.rest.GCRest#executeRequest(org.apache.http.client
	 * .methods.HttpUriRequest)
	 */
	@Override
	public void executeRequest(final HttpUriRequest request) throws IOException {

		// add headers
		for (NameValuePair h : getHeaders()) {
			request.addHeader(h.getName(), h.getValue());
		}

		final HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				connectionTimeout);
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

			final HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				instream = entity.getContent();
				response = GCStreamUtil.convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (final IOException e) {
			throw e;
		} catch (final RuntimeException ex) {
			// In case of an unexpected exception you may want to abort
			// the HTTP request in order to shut down the underlying
			// connection immediately.
			request.abort();
			throw ex;
		} finally {
			// Closing the input stream will trigger connection release
			GCStreamUtil.silentClose(instream);
		}
	}

	public static DefaultHttpClient getClient() {
		if (client == null) {
			client = GCStreamUtil.generateClient();
		}
		return client;
	}

}
