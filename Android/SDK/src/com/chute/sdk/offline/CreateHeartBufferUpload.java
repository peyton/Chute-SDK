package com.chute.sdk.offline;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class CreateHeartBufferUpload extends AbstractBufferUpload {
    private RequestMethod method;

    @SuppressWarnings("unused")
    private static final String TAG = CreateHeartBufferUpload.class.getSimpleName();

    public CreateHeartBufferUpload(Context context) {
	super(context);
	Cursor c = getContext().getContentResolver().query(DB.Buffer.CONTENT_URI, null,
		DB.Buffer.TYPE + "=?", new String[] { String.valueOf(ConstantsBuffer.TYPE_HEART) },
		null);
	try {
	    if (c.moveToFirst()) {
		do {
		    try {
			JSONObject obj = new JSONObject(c.getString(c
				.getColumnIndex(DB.Buffer.DATA)));
			method = RequestMethod.GET;
			String url;
			if (obj.getBoolean(ConstantsBuffer.IS_HEART) == true) {
			    url = ChuteConstants.URL_FULLSCREEN_HEART_ASSET;
			    url = String.format(url, obj.getString(ConstantsBuffer.HEART_ID));
			} else {
			    url = ChuteConstants.URL_FULLSCREEN_UNHEART_ASSET;
			    url = String.format(url, obj.getString(ConstantsBuffer.HEART_ID));
			}
			Log.e(TAG, "URl for the Hearts call " + url);
			getClient().setUrl(url);
			executeRequest(method);
			if (getClient().getResponseCode() != HttpStatus.SC_OK) {
			    getContext().getContentResolver().delete(DB.Buffer.CONTENT_URI,
				    DB.Buffer._ID + "=?",
				    new String[] { c.getString(c.getColumnIndex(DB.Buffer._ID)) });
			    throw new IOException("status code != OK : "
				    + String.valueOf(getClient().getResponseCode()));
			}

		    } catch (JSONException e) {
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
