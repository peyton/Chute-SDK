package com.chute.sdk.api.account;

import android.content.Context;
import android.text.TextUtils;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

public class AccountsGetObjectsRequest<T> extends GCParameterHttpRequestImpl<T> {

	public static final String TAG = AccountsGetObjectsRequest.class
			.getSimpleName();
	private final String accountId;

	public AccountsGetObjectsRequest(Context context, String accountId,
			GCHttpResponseParser<T> parser, GCHttpCallback<T> callback) {
		super(context, RequestMethod.GET, parser, callback);
		if (TextUtils.isEmpty(accountId)) {
			throw new NullPointerException("Need to provide account ID");
		}
		this.accountId = accountId;
	}

	@Override
	protected void prepareParams() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		runRequest(String.format(GCRestConstants.URL_ACCOUNT_OBJECT, accountId));
	}

}
