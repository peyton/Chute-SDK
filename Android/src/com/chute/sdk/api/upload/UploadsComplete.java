package com.chute.sdk.api.upload;

import org.json.JSONObject;

import android.util.Log;

import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;

public class UploadsComplete {

    private static final String TAG = UploadsComplete.class.getSimpleName();

    public static void uploadsComplete(String id, String apiKey) {
	RestClient client = new RestClient(String.format(Constants.URL_UPLOADS_COMPLETE, id));
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