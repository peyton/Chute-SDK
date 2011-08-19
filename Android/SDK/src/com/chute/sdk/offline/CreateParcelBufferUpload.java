package com.chute.sdk.offline;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;
import com.chute.sdk.api.parsers.CreateParcelsJSONParser;

public class CreateParcelBufferUpload extends AbstractBufferUpload {

    private static final String TAG = CreateChuteBufferUpload.class.getSimpleName();

    public CreateParcelBufferUpload(Context context) {
	super(context, ChuteConstants.URL_PARCELS);
	setParser(new CreateParcelsJSONParser(context));
	Cursor c = context.getContentResolver().query(DB.Buffer.CONTENT_URI, null,
		DB.Buffer.TYPE + "=?",
		new String[] { String.valueOf(ConstantsBuffer.TYPE_PARCELS) }, null);
	try {
	    if (c.moveToFirst()) {
		do {
		    try {
			JSONObject obj = new JSONObject(c.getString(c
				.getColumnIndex(DB.Buffer.DATA)));
			addParam(ConstantsBuffer.CHUTES, obj.getJSONArray(ConstantsBuffer.CHUTES)
				.toString());
			addParam(ConstantsBuffer.FILES, obj.getJSONArray(ConstantsBuffer.FILES)
				.toString());
			executeRequest(RequestMethod.POST);

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
