package com.chute.sdk.api.hearts;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.offline.ConstantsBuffer;

public class HeartsSet extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = HeartsSet.class.getSimpleName();

    private final Context context;

    private final String id;
    private final boolean isHeart;

    public HeartsSet(Context context, String id, boolean isHeart) {
	this.context = context;
	this.id = id;
	this.isHeart = isHeart;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	String url;
	if (isHeart) {
	    url = Constants.URL_FULLSCREEN_HEART_ASSET;
	    url = String.format(url, id);
	} else {
	    url = Constants.URL_FULLSCREEN_UNHEART_ASSET;
	    url = String.format(url, id);
	}
	RestClient client = new RestClient(url);
	client.setSocketTimeout(5000);
	client.setConnectionTimeout(8000);
	DataAccountUserPass user = AccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	try {

	    client.execute(RequestMethod.GET);
	    if (client.getResponseCode() != HttpStatus.SC_OK) {
		throw new IOException();
	    }
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	Log.i(TAG, "writing to buffer");
	try {
	    JSONObject obj = new JSONObject();
	    obj.put(ConstantsBuffer.HEART_ID, id);
	    obj.put(ConstantsBuffer.IS_HEART, isHeart);
	    ContentValues values = new ContentValues();
	    values.put(DB.Buffer.DATA, obj.toString());
	    values.put(DB.Buffer.TYPE, ConstantsBuffer.TYPE_HEART);
	    context.getContentResolver().insert(DB.Buffer.CONTENT_URI, values);
	    Log.i(TAG, "written to buffer" + obj.toString());
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, "", e);
	}
	return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	// TODO Auto-generated method stub
	super.onPostExecute(result);
    }
}
