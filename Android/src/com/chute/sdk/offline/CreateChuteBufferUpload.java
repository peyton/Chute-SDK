package com.chute.sdk.offline;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.parsers.CreateChuteJSONParser;

public class CreateChuteBufferUpload extends AbstractBufferUpload {

    private static final String TAG = CreateChuteBufferUpload.class.getSimpleName();

    private RequestMethod method;

    public CreateChuteBufferUpload(Context context) {
	super(context);
	init();
    }

    public void init() {
	setParser(new CreateChuteJSONParser(getContext()));
	Cursor c = getContext().getContentResolver().query(DB.Buffer.CONTENT_URI, null,
		DB.Buffer.TYPE + "=?",
		new String[] { String.valueOf(ConstantsBuffer.TYPE_CHUTES) }, null);
	try {
	    if (c.moveToFirst()) {
		do {
		    try {
			JSONObject obj = new JSONObject(c.getString(c
				.getColumnIndex(DB.Buffer.DATA)));
			if (TextUtils.isEmpty(obj.getString("chute[id]"))) {
			    getClient().setUrl(Constants.URL_CHUTES_CREATE);
			    method = RequestMethod.POST;
			} else {
			    getClient().setUrl(
				    String.format(Constants.URL_CHUTES_UPDATE,
					    obj.getString("chute[id]")));
			    method = RequestMethod.PUT;
			}
			addParam("chute[name]", obj.getString("chute[name]"));
			addParam("chute[permission_view]", obj.getString("chute[permission_view]"));
			addParam("chute[permission_add_members]",
				obj.getString("chute[permission_add_members]"));
			addParam("chute[permission_add_photos]",
				obj.getString("chute[permission_add_photos]"));
			addParam("chute[permission_add_comments]",
				obj.getString("chute[permission_add_comments]"));
			addParam("chute[moderate_members]",
				obj.getString("chute[moderate_members]"));
			addParam("chute[moderate_photos]", obj.getString("chute[moderate_photos]"));
			addParam("chute[moderate_comments]",
				obj.getString("chute[moderate_comments]"));
			executeRequest(method);

		    } catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "", e);
			;
		    }
		    getContext().getContentResolver().delete(DB.Buffer.CONTENT_URI,
			    DB.Buffer._ID + "=?",
			    new String[] { c.getString(c.getColumnIndex(DB.Buffer._ID)) });
		} while (c.moveToNext());
	    }
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	    ;
	} finally {
	    if (c != null) {
		c.close();
	    }
	}
    }

}
