package com.chute.sdk.api.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;

public class InboxParcelsJSONParser extends AbstractJSONParser {

    private static final String TAG = InboxParcelsJSONParser.class.getSimpleName();

    public InboxParcelsJSONParser(Context context) {
	super(context);
    }

    @Override
    public void parse(final String response) throws JSONException {
	final ContentResolver cr = getContext().getContentResolver();
	JSONObject obj = new JSONObject(response);
	JSONArray dataArray = obj.getJSONArray("data");
	Log.e(TAG,
		"AssetArray Lengh${imp:import(org.eclipse.swt.SWT)}t "
			+ String.valueOf(dataArray.length()));
	cr.delete(DB.InboxParcels.CONTENT_URI, null, null);
	cr.delete(DB.InboxParcelsAssets.CONTENT_URI, null, null);
	for (int i = 0; i < dataArray.length(); i++) {
	    // response.data.add(parseSingleTopLevelChute(dataArray.getJSONObject(i)));
	    obj = dataArray.getJSONObject(i);
	    String id = obj.getString("id");
	    ContentValues values = new ContentValues();
	    values.put(DB.InboxParcels._ID, id);
	    values.put(DB.InboxParcels.COUNT, obj.getString("count"));
	    values.put(DB.InboxParcels.THUMB, obj.getString("thumbnail"));
	    {
		JSONObject objUser = obj.getJSONObject("user");
		values.put(DB.InboxParcels.USER_ID, objUser.getString("id"));
		values.put(DB.InboxParcels.USER_NAME, objUser.getString("name"));
		values.put(DB.InboxParcels.USER_AVATAR, objUser.getString("avatar"));

	    }
	    obj = obj.getJSONObject("chute");
	    String chuteId = obj.getString("id");
	    values.put(DB.InboxParcels.CHUTE_ID, chuteId);
	    values.put(DB.InboxParcels.CHUTE_NAME, obj.getString("name"));
	    values.put(DB.InboxParcels.CHUTE_SHORTCUT, obj.getString("shortcut"));
	    values.put(DB.InboxParcels.CHUTE_RECENT_COUNT, obj.getString("recent_count"));

	    cr.insert(DB.InboxParcels.CONTENT_URI, values);

	    JSONArray assetsArray = dataArray.getJSONObject(i).getJSONArray("assets");
	    if (assetsArray.length() > 0) {
		cr.insert(DB.InboxParcels.CONTENT_URI, values);
	    } else {
		continue;
	    }
	    for (int j = 0; j < assetsArray.length(); j++) {
		obj = assetsArray.getJSONObject(j);
		values = new ContentValues();
		values.put(DB.InboxParcelsAssets._ID, obj.getString("id"));
		values.put(DB.InboxParcelsAssets.PARCEL_ID, id);
		values.put(DB.InboxParcelsAssets.CHUTE_ID, chuteId);
		values.put(DB.InboxParcelsAssets.URL, obj.getString("url"));
		values.put(DB.InboxParcelsAssets.COMMENTS, obj.getString("comments"));
		values.put(DB.InboxParcelsAssets.SHARE_URL, obj.getString("share_url"));
		cr.insert(DB.InboxParcelsAssets.CONTENT_URI, values);
	    }
	}
    }

}
