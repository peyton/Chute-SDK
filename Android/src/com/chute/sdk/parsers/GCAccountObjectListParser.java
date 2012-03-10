package com.chute.sdk.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.collections.GCAccountObjectCollection;
import com.chute.sdk.parsers.base.GCAccountObjectBaseParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

public class GCAccountObjectListParser implements GCHttpResponseParser<GCAccountObjectCollection> {

    @SuppressWarnings("unused")
    private static final String TAG = GCAccountObjectListParser.class.getSimpleName();

    public GCAccountObjectListParser() {
	super();
    }

    @Override
    public GCAccountObjectCollection parse(String responseBody) throws JSONException {
	GCAccountObjectCollection list = new GCAccountObjectCollection();
	JSONArray array = new JSONObject(responseBody).getJSONArray("data");
	for (int i = 0; i < array.length(); i++) {
	    list.add(GCAccountObjectBaseParser.parse(array.getJSONObject(i)));
	}
	return list;
    }

}
