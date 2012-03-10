package com.chute.sdk.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.collections.GCAccountMediaCollection;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.parsers.base.GCAccountMediaBaseParser;

public class GCAccountMediaListParser implements GCHttpResponseParser<GCAccountMediaCollection> {

    @SuppressWarnings("unused")
    private static final String TAG = GCAccountMediaListParser.class.getSimpleName();

    public GCAccountMediaListParser() {
	super();
    }

    @Override
    public GCAccountMediaCollection parse(String responseBody) throws JSONException {
	GCAccountMediaCollection list = new GCAccountMediaCollection();
	JSONArray array = new JSONObject(responseBody).getJSONArray("data");
	for (int i = 0; i < array.length(); i++) {
	    list.add(GCAccountMediaBaseParser.parse(array.getJSONObject(i)));
	}
	return list;
    }

}
