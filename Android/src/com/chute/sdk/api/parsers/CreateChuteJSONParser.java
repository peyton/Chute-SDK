package com.chute.sdk.api.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;

public class CreateChuteJSONParser extends AbstractJSONParser {

    private int chuteId;

    public CreateChuteJSONParser(Context context) {
	super(context);
    }

    public int getChuteId() {
	return chuteId;
    }

    @Override
    public void parse(String response) throws JSONException {
	JSONObject obj = new JSONObject(response);
	ContentValues values;
	values = new ContentValues();
	Log.e("obj", obj.toString());

	values.put(DB.ChutesColumns._ID, obj.getInt("id"));
	chuteId = obj.getInt("id");
	values.put(DB.ChutesColumns.CAN_MEMBERS_ADD_PHOTOS, obj.getInt("permission_add_photos"));
	values.put(DB.ChutesColumns.CAN_MEMBERS_INVITE_OTHERS, obj.getInt("permission_add_members"));
	values.put(DB.ChutesColumns.CHUTE_VISIBILITY, obj.getInt("permission_view"));
	values.put(DB.ChutesColumns.MEMBERS_COUNT, obj.getString("members_count"));
	values.put(DB.ChutesColumns.NAME, obj.getString("name"));
	values.put(DB.ChutesColumns.ASSET_COUNT, obj.getString("assets_count"));
	values.put(DB.ChutesColumns.RECENT_THUMB, obj.getString("recent_thumbnail"));
	values.put(DB.ChutesColumns.SHORTCUT, obj.getString("shortcut"));
	values.put(DB.ChutesColumns.OWNER_ID, obj.getJSONObject("user").getString("id"));
	values.put(DB.ChutesColumns.OWNER_NAME, obj.getJSONObject("user").getString("name"));
	getContext().getContentResolver().insert(DB.ChutesColumns.CONTENT_URI, values);
    }
}
