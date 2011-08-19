package com.chute.sdk.api.parsers;

import org.json.JSONException;

import android.content.Context;

public abstract class AbstractJSONParser implements JSONParserInterface{

	private Context context;
	
	public AbstractJSONParser(Context context) {
		this.context = context;
	}

	@Override
	public abstract void parse(final String response) throws JSONException;

	public Context getContext() {
		return context;
	}
	
}
