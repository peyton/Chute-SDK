package com.chute.sdk.parsers.base;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.chute.sdk.model.GCAccountMediaModel;

public class GCAccountMediaBaseParser {

	@SuppressWarnings("unused")
	private static final String TAG = GCAccountMediaBaseParser.class
			.getSimpleName();

	public static GCAccountMediaModel parse(final JSONObject obj)
			throws JSONException {
		final GCAccountMediaModel model = new GCAccountMediaModel();
		model.setId(obj.optString("id"));
		model.setThumbUrl(obj.getString("thumb"));
		String url = obj.getString("large");
		if (TextUtils.isEmpty(url) || url.equalsIgnoreCase("null")) {
			url = model.getThumbUrl();
		}
		model.setLargeUrl(url);
		url = obj.getString("url");
		if (TextUtils.isEmpty(url) || url.equalsIgnoreCase("null")) {
			url = model.getLargeUrl();
		}
		model.setUrl(url);
		return model;
	}

}
