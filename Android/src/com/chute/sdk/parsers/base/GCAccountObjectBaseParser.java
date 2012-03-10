package com.chute.sdk.parsers.base;

import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.model.GCAccountObjectModel;

public class GCAccountObjectBaseParser {

    @SuppressWarnings("unused")
    private static final String TAG = GCAccountObjectBaseParser.class.getSimpleName();

    public static GCAccountObjectModel parse(final JSONObject obj) throws JSONException {
	final GCAccountObjectModel model = new GCAccountObjectModel();
	model.setId(obj.getString("id"));
	model.setName(obj.getString("name"));
	return model;
    }

}
