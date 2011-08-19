package com.chute.sdk.api.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class HeartsJSONParser extends AbstractJSONParser {
    @SuppressWarnings("unused")
    private static final String TAG = HeartsJSONParser.class.getSimpleName();
    private ArrayList<Integer> data;

    public HeartsJSONParser(Context context) {
	super(context);
    }

    @Override
    public void parse(String response) throws JSONException {
	data = new ArrayList<Integer>();
	JSONObject obj = new JSONObject(response);
	JSONArray array = obj.getJSONArray("data");
	for (int i = 0; i < array.length(); i++) {
	    data.add(array.getInt(i));
	}
    }

    public ArrayList<Integer> getData() {
	return data;
    }
}
