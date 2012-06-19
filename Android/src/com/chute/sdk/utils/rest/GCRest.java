package com.chute.sdk.utils.rest;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCHttpRequestParameters;

public interface GCRest {

	public abstract int getConnectionTimeout();

	public abstract void setUrl(final String url);

	public abstract String getUrl();

	public abstract void setConnectionTimeout(final int connectionTimeout);

	public abstract int getSocketTimeout();

	public abstract void setSocketTimeout(final int socketTimeout);

	public abstract void setAuthentication(final GCAccountStore userAccount);

	public abstract String getResponse();

	public abstract String getErrorMessage();

	public abstract int getResponseCode();

	public abstract ArrayList<NameValuePair> getHeaders();

	public abstract void addHeader(final String name, final String value);

	public abstract void executeRequest(final HttpUriRequest request)
			throws IOException;

	public abstract GCHttpRequestParameters getRequestParameters();

}