package com.chute.sdk.api.upload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;

public class DevicesQueue {
    private static final String TAG = DevicesQueue.class.getSimpleName();
    private int totalCount = 0;
    private boolean hasMoreImagesInQueue = true;

    public int getTotalCount() {
	return totalCount;
    }

    public DevicesQueue() {
    }

    public boolean hasMoreImagesInQueue() {
	return hasMoreImagesInQueue;
    }

    public boolean devicesQueue(Context context) throws JSONException {
	Log.v(TAG, "Device id" + AccountUtils.restoreDeviceId(context));
	DataAccountUserPass user = AccountUtils.getAccountInfo(context);
	try {
	    context.getContentResolver().delete(DB.UploadQueueColumns.CONTENT_URI, null, null);
	    RestClient client = new RestClient(String.format(Constants.URL_DEVICES_QUEUE,
		    AccountUtils.restoreDeviceId(context)));
	    client.setAuthentication(user.getPassword());
	    client.execute(RequestMethod.GET);
	    Log.v(TAG, client.getResponse());
	    parseDeviceQueue(context.getContentResolver(), client.getResponse());
	    return true;
	} catch (JSONException e) {
	    throw new JSONException(e.getMessage());
	} catch (Exception e) {
	    new RuntimeException("Connection error", e);
	}
	return false;
    }

    private boolean parseDeviceQueue(ContentResolver cr, String response) throws JSONException {
	totalCount = new JSONObject(response).getInt("total");
	JSONArray array = new JSONObject(response).getJSONArray("data");
	ContentValues values;
	JSONObject obj;
	if (array.length() == 0) {
	    hasMoreImagesInQueue = false;
	}
	for (int i = 0; i < array.length(); i++) {
	    obj = array.getJSONObject(i);
	    values = new ContentValues();
	    values.put(DB.UploadQueueColumns.QUEUE_ASSET_ID, obj.getString("asset_id"));
	    values.put(DB.UploadQueueColumns.QUEUE_FILE_PATH, obj.getString("file_path"));
	    values.put(DB.UploadQueueColumns.QUEUE_LOCKED, obj.getString("locked"));
	    values.put(DB.UploadQueueColumns.QUEUE_PRIORITY, obj.getString("priority"));
	    values.put(DB.UploadQueueColumns.QUEUE_STATUS, obj.getString("status"));
	    cr.insert(DB.UploadQueueColumns.CONTENT_URI, values);
	}
	return true;
    }

}
