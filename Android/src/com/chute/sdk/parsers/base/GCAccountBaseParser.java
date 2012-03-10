package com.chute.sdk.parsers.base;

import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.model.GCAccountModel;

public class GCAccountBaseParser {

    @SuppressWarnings("unused")
    private static final String TAG = GCAccountBaseParser.class.getSimpleName();

    public static GCAccountModel parse(final JSONObject obj) throws JSONException {
        GCAccountModel model = new GCAccountModel();
        model.setId(obj.getString("id"));
        model.setName(obj.getString("name"));
        model.setNotificationsEnabled(obj.getBoolean("notifications_enabled"));
        model.setStatus(obj.getInt("status"));
        model.setType(obj.getString("type"));
        model.setUid(obj.getString("uid"));
        model.user = GCBaseUserModelParser.parse(obj.getJSONObject("user"));
        return model;
    }
}
