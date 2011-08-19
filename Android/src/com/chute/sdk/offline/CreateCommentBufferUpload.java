package com.chute.sdk.offline;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.parsers.CommentsJSONParser;

public class CreateCommentBufferUpload extends AbstractBufferUpload {

    public CreateCommentBufferUpload(Context context) {
	super(context);
	Cursor c = context.getContentResolver().query(DB.Buffer.CONTENT_URI, null,
		DB.Buffer.TYPE + "=?",
		new String[] { String.valueOf(ConstantsBuffer.TYPE_COMMENTS) }, null);
	setParser(new CommentsJSONParser(context));
	if (c.moveToFirst()) {
	    do {
		try {
		    JSONObject obj = new JSONObject(c.getString(c.getColumnIndex(DB.Buffer.DATA)));
		    String format = String.format(Constants.URL_COMMENTS_POST,
			    obj.getString(ConstantsBuffer.CHUTE_ID),
			    obj.getString(ConstantsBuffer.ASSET_ID));
		    Log.e(TAG, format);
		    getClient().setUrl(format);
		    addParam(ConstantsBuffer.COMMENT, obj.getString(ConstantsBuffer.COMMENT));
		    executeRequest(RequestMethod.POST);
		    getContext().getContentResolver().delete(DB.Buffer.CONTENT_URI,
			    DB.Buffer._ID + "=?",
			    new String[] { c.getString(c.getColumnIndex(DB.Buffer._ID)) });
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    Log.d(TAG, "", e);
		    ;
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    Log.d(TAG, "", e);
		    ;
		}

	    } while (c.moveToNext());
	}

    }

}
