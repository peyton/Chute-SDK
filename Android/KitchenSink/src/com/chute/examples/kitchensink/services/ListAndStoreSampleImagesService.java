package com.chute.examples.kitchensink.services;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.MD5;
import com.chute.sdk.api.assets.AssetsVerify;

public class ListAndStoreSampleImagesService extends IntentService {
    private Context mContext;

    public ListAndStoreSampleImagesService() {
	super(TAG);
    }

    @SuppressWarnings("unused")
    private static final String TAG = ListAndStoreSampleImagesService.class.getSimpleName();

    @Override
    protected void onHandleIntent(Intent intent) {

	mContext = getApplicationContext();

	if (TextUtils.isEmpty(ChuteAccountUtils.restoreDeviceId(mContext))) {
	    String android_id = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
	    if (android_id == null || android_id.contentEquals("")) {
		WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		android_id = "DEVICE_MAC: " + wm.getConnectionInfo().getMacAddress();
	    }
	    ChuteAccountUtils.saveDeviceId(android_id, mContext);
	}

	String[] projection = new String[] { MediaStore.Images.Media.DATE_MODIFIED,
		MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID,
		MediaStore.Images.Media.DATA };
	if (mContext
		.getContentResolver()
		.query(DB.ImagesColumns.CONTENT_URI, new String[] { DB.ImagesColumns._ID }, null,
			null, null).getCount() > 0) {
	    return; // No need to continue there is sample data available in the
		    // database
	}
	{
	    Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	    String query = MediaStore.Images.Media.DATA + " LIKE \"%DCIM%\"";
	    Cursor c = mContext.getContentResolver().query(images, projection, query, null, null);
	    if (c != null && c.moveToFirst()) {
		String path;
		int pathColumn = c.getColumnIndex(MediaStore.Images.Media.DATA);
		long date;
		int addedColumn = c.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
		int idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
		do {
		    path = c.getString(pathColumn);
		    date = c.getLong(addedColumn);
		    String id = c.getString(idColumn);
		    ContentValues value = new ContentValues();
		    value.put(DB.ImagesColumns._ID, id);
		    value.put(DB.ImagesColumns.FILEPATH, path);
		    value.put(DB.ImagesColumns.FILENAME,
			    path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.')));
		    value.put(DB.ImagesColumns.DATE_LISTED, date);
		    value.put(DB.ImagesColumns.STATUS, DB.ImagesColumns.STATUS_UNVERIFIED);
		    mContext.getContentResolver().insert(DB.ImagesColumns.CONTENT_URI, value);
		    Log.e(TAG, "path " + path);
		} while (c.moveToNext() && c.getPosition() < 25); // restriction
								  // just for
								  // the sample
								  // app.
	    }
	}
	// Verify new assets

	try {
	    Cursor c = mContext.getContentResolver().query(DB.ImagesColumns.CONTENT_URI, null,
		    DB.ImagesColumns.STATUS + "=?",
		    new String[] { String.valueOf(DB.ImagesColumns.STATUS_UNVERIFIED) }, null);
	    if (c != null && c.moveToFirst()) {
		JSONArray array = new JSONArray();
		JSONObject obj;
		int countAtATime = 0;
		do {
		    countAtATime++;
		    obj = new JSONObject();
		    String path = c.getString(c.getColumnIndexOrThrow(DB.ImagesColumns.FILEPATH));
		    obj.put("filename", path);
		    File f = new File(path);
		    if (f.exists()) {
			obj.put("size", String.valueOf(f.length()));
			obj.put("md5", MD5.getMD5Checksum(path));
			array.put(obj);
		    }
		    if (countAtATime > 15) {
			AssetsVerify.verify(mContext, array);
			array = new JSONArray();
			countAtATime = 0;
		    }
		} while (c.moveToNext());
		AssetsVerify.verify(mContext, array);
	    }
	} catch (RemoteException e) {
	    Log.d(TAG, "", e);
	} catch (JSONException e) {
	    Log.d(TAG, "", e);
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	}
    }
}
