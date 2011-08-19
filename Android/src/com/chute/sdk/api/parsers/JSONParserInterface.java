package com.chute.sdk.api.parsers;

import org.json.JSONException;

public interface JSONParserInterface {
	public void parse(final String response) throws JSONException;
}
