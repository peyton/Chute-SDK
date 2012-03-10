package com.chute.sdk.api.chute;

import android.content.Context;
import android.text.TextUtils;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRest.RequestMethod;
import com.chute.sdk.utils.GCRestConstants;

public class ChutesSearchGetRequest<T> extends GCHttpRequestImpl<T> {

    public static final String TAG = ChutesSearchGetRequest.class.getSimpleName();
    private final String searchQuery;

    public ChutesSearchGetRequest(Context context, String searchQuery, GCHttpResponseParser<T> parser,
	    GCHttpCallback<T> callback) {
	super(context, RequestMethod.GET, parser, callback);
	if (TextUtils.isEmpty(searchQuery)) {
	    throw new NullPointerException("Need to provide domain name");
	}
	this.searchQuery = searchQuery;
    }

    @Override
    protected void prepareParams() {
    }

    @Override
    public void execute() {
	runRequest(String.format(GCRestConstants.URL_CHUTES_SEARCH, searchQuery));
    }
}
