package com.chute.sdk.api.account;

import android.content.Context;
import android.text.TextUtils;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRest.RequestMethod;
import com.chute.sdk.utils.GCRestConstants;

public class AccountsGetObjectMediaRequest<T> extends GCHttpRequestImpl<T> {

	public static final String TAG = AccountsGetObjectMediaRequest.class
			.getSimpleName();
	private final String accountId;
	private final String objectId;

	public AccountsGetObjectMediaRequest(Context context, String accountId,
			String objectId, GCHttpResponseParser<T> parser,
			GCHttpCallback<T> callback) {
		super(context, RequestMethod.GET, parser, callback);
		this.accountId = accountId;
		if (TextUtils.isEmpty(objectId)) {
			throw new NullPointerException("Need to provide object ID");
		}
		this.objectId = objectId;
	}

	@Override
	protected void prepareParams() {
	}

	@Override
	public void execute() {
		runRequest(String.format(GCRestConstants.URL_ACCOUNT_OBJECT_MEDIA,
				accountId, objectId));
	}

}
