package com.chute.sdk.api.account;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

public class AccountsGetRequest<T> extends GCParameterHttpRequestImpl<T> {

	public static final String TAG = AccountsGetRequest.class.getSimpleName();

	public AccountsGetRequest(Context context, GCHttpResponseParser<T> parser,
			GCHttpCallback<T> callback) {
		super(context, RequestMethod.GET, parser, callback);
	}

	@Override
	protected void prepareParams() {
	}

	@Override
	public void execute() {
		runRequest(String.format(GCRestConstants.URL_ACCOUNTS));
	}
}
