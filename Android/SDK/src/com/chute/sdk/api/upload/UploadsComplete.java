package com.chute.sdk.api.upload;

import org.json.JSONObject;

import android.util.Log;

import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class UploadsComplete {

    private static final String TAG = UploadsComplete.class.getSimpleName();

    public static void uploadsComplete(String id, String apiKey) {
	ChuteRestClient client = new ChuteRestClient(String.format(ChuteConstants.URL_UPLOADS_COMPLETE, id));
	client.setAuthentication(apiKey);
	try {
	    client.execute(RequestMethod.GET);
	    Log.e(TAG, client.getResponse());
	    JSONObject obj = new JSONObject(client.getResponse());
	    if (!obj.has("asset")) {
		throw new RuntimeException("Not a proper Response");
	    }
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	    ;
	    throw new RuntimeException(e.getMessage());
	}
    }
}