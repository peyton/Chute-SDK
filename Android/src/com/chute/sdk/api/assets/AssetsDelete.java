package com.chute.sdk.api.assets;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;

public class AssetsDelete {

    private static final String TAG = AssetsDelete.class.getSimpleName();

    public static boolean assetsDeleteSingle(Context context, String id) {
	RestClient client = new RestClient(String.format(Constants.URL_ASSETS_DELETE_SINGLE, id));

	DataAccountUserPass user = AccountUtils.getAccountInfo(context);

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
