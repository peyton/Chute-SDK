package com.chute.sdk.api.assets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class AssetsVerify {

    private static final String TAG = AssetsVerify.class.getSimpleName();

    public static boolean verify(Context context, JSONArray obj) {
	ChuteRestClient client = new ChuteRestClient(ChuteConstants.URL_ASSETS_VERIFY);

	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	try {
	    client.addParam("files", obj.toString());
	    client.setSocketTimeout(20000);
	    client.execute(RequestMethod.POST);
	    Log.e(TAG, client.getResponse());
	    parseAssetsVerify(context.getContentResolver(), client.getResponse());
	    return true;
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	}
	return false;
    }

    private static void parseAssetsVerify(ContentResolver cr, String response)
	    throws JSONException, IllegalArgumentException {
	JSONArray array = new JSONArray(response);
	ContentValues values;
	JSONObject obj;
	for (int i = 0; i < array.length(); i++) {
	    try {
		obj = array.getJSONObject(i);
		values = new ContentValues();
		values.put(DB.ImagesColumns.ASSET_ID, obj.getString("asset_id"));
		values.put(DB.ImagesColumns.PRIORITY, obj.getInt("priority"));
		values.put(DB.ImagesColumns.STATUS, obj.getString("status"));
		cr.update(DB.ImagesColumns.CONTENT_URI, values, DB.ImagesColumns.FILEPATH + "=?",
			new String[] { obj.getString("file_path") });
	    } catch (SQLiteException e) {
		Log.d(TAG, "", e);
	    }
	}
    }
}
