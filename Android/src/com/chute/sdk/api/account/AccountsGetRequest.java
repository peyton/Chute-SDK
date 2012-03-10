package com.chute.sdk.api.account;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRest.RequestMethod;
import com.chute.sdk.utils.GCRestConstants;

public class AccountsGetRequest<T> extends GCHttpRequestImpl<T> {

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
