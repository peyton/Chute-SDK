package com.chute.sdk.api.chute;

import android.content.Context;
import android.text.TextUtils;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

public class ChutesAllGetRequest<T> extends GCParameterHttpRequestImpl<T> {

	@SuppressWarnings("unused")
	private static final String TAG = ChutesAllGetRequest.class.getSimpleName();
	private final String userId;

	public ChutesAllGetRequest(Context context, String userId,
			GCHttpResponseParser<T> parser, GCHttpCallback<T> callback) {
		super(context, RequestMethod.GET, parser, callback);
		if (TextUtils.isEmpty(userId)) {
			throw new NullPointerException("Need to provide user ID");
		}
		this.userId = userId;
	}

	@Override
	protected void prepareParams() {
	}

	@Override
	public void execute() {
		runRequest(String.format(GCRestConstants.URL_CHUTES_ALL, userId));
	}

}
