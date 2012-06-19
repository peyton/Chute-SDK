package com.chute.sdk.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.collections.GCAccountsCollection;
import com.chute.sdk.parsers.base.GCAccountBaseParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

public class GCAccountListParser implements
		GCHttpResponseParser<GCAccountsCollection> {

	@SuppressWarnings("unused")
	private static final String TAG = GCAccountListParser.class.getSimpleName();

	public GCAccountListParser() {
		super();
	}

	@Override
	public GCAccountsCollection parse(String responseBody) throws JSONException {
		GCAccountsCollection list = new GCAccountsCollection();
		JSONArray array = new JSONObject(responseBody).getJSONArray("data");
		for (int i = 0; i < array.length(); i++) {
			list.add(GCAccountBaseParser.parse(array.getJSONObject(i)));
		}
		return list;
	}

}
