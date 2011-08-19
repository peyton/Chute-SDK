package com.chute.sdk.api.upload;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.models.PhotoDataModel;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.MD5;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.OnApiCallComplete;
import com.chute.sdk.api.parsers.CreateParcelsJSONParser;
import com.chute.sdk.offline.ConstantsBuffer;

public class CreateParcel extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = CreateParcel.class.getSimpleName();
    private final Activity activity;

    private final OnApiCallComplete onApiCallComplete;
    private final String[] chuteIds;
    private final ArrayList<PhotoDataModel> photos;

    public CreateParcel(Activity activity, ArrayList<PhotoDataModel> photos,
	    OnApiCallComplete onApiCallComplete, String... chuteIds) {
	this.activity = activity;
	this.photos = photos;
	this.chuteIds = chuteIds;
	this.onApiCallComplete = onApiCallComplete;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
	super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	JSONArray chuteIdsArray = new JSONArray();
	JSONArray files = new JSONArray();
	for (String chuteIdString : chuteIds) {
	    chuteIdsArray.put(chuteIdString);
	}
	Log.e(TAG, chuteIdsArray.toString());

	JSONObject obj;
	for (PhotoDataModel photo : photos) {
	    obj = new JSONObject();
	    File f = new File(photo.getPath());
	    if (f.exists()) {
		try {
		    obj.put("filename", photo.getPath());
		    obj.put("md5", MD5.getMD5Checksum(photo.getPath()));
		    obj.put("size", String.valueOf(f.length()));
		    files.put(obj);
		} catch (JSONException e) {
		    Log.d(TAG, "", e);
		} catch (Exception e) {
		    Log.d(TAG, "", e);
		}
	    }
	}
	if (chuteIdsArray.length() > 0 && files.length() > 0) {
	    return createParcels(chuteIdsArray, files);
	} else {
	    return false;
	}
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
	if (isSuccess) {
	    onApiCallComplete.onSuccess();
	    if (isOnlineSubmitted) {
		Toast.makeText(activity, "Parcel creted sucessfully", Toast.LENGTH_LONG).show();
	    } else {
		Toast.makeText(activity, "Parcel will be submitted in the next online sync",
			Toast.LENGTH_LONG).show();
	    }
	} else {
	    onApiCallComplete.onFail();
	    Toast.makeText(activity, "Parcel creation failed", Toast.LENGTH_LONG).show();
	}
	super.onPostExecute(isSuccess);
    }

    private boolean isOnlineSubmitted = false;

    private boolean createParcels(JSONArray ids, JSONArray files) {
	RestClient client = new RestClient(Constants.URL_PARCELS);
	client.setConnectionTimeout(9000);
	client.setSocketTimeout(20000);
	DataAccountUserPass user = AccountUtils.getAccountInfo(activity);
	client.setAuthentication(user.getPassword());
	client.addParam(ConstantsBuffer.CHUTES, ids.toString());
	client.addParam(ConstantsBuffer.FILES, files.toString());
	try {
	    client.execute(RequestMethod.POST);
	    Log.v(TAG, client.getResponse());
	    new CreateParcelsJSONParser(activity).parse(client.getResponse());
	    isOnlineSubmitted = true;
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	    return false;
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	JSONObject obj = new JSONObject();
	try {
	    obj.put(ConstantsBuffer.CHUTES, ids);
	    obj.put(ConstantsBuffer.FILES, files);
	    ContentValues values = new ContentValues();
	    values.put(DB.Buffer.DATA, obj.toString());
	    values.put(DB.Buffer.TYPE, ConstantsBuffer.TYPE_PARCELS);
	    activity.getContentResolver().insert(DB.Buffer.CONTENT_URI, values);
	    isOnlineSubmitted = false;
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	    return false;
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }
}
