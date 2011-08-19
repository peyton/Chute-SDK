package com.chute.sdk.api.assets;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class AssetsDelete {

    private static final String TAG = AssetsDelete.class.getSimpleName();

    public static boolean assetsDeleteSingle(Context context, String id) {
	ChuteRestClient client = new ChuteRestClient(String.format(
		ChuteConstants.URL_ASSETS_DELETE_SINGLE, id));

	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);

	client.setAuthentication(user.getPassword());
	Log.e(TAG, "AssetId " + id);
	try {
	    client.execute(RequestMethod.POST);
	    Log.e(TAG, client.getResponse());
	    parseAssetsDelete(context.getContentResolver(), client.getResponse(), id);
	    return true;
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	    ;
	}
	return false;
    }

    private static void parseAssetsDelete(ContentResolver contentResolver, String response,
	    String id) throws JSONException {
	JSONObject obj = new JSONObject(response);
	if (obj.has("data")) {
	    // DElete the row because the call was successful
	    contentResolver.delete(DB.ImagesColumns.CONTENT_URI, DB.ImagesColumns.ASSET_ID + "=?",
		    new String[] { id });
	}
    }
}
