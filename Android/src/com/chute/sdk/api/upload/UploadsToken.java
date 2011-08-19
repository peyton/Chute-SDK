package com.chute.sdk.api.upload;

import android.util.Log;

import com.chute.examples.kitchensink.models.DataUploadToken;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;

public class UploadsToken {

    private static final String TAG = UploadsToken.class.getSimpleName();

    public static DataUploadToken UploadToken(String id, String apiKey) {
	RestClient client = new RestClient(String.format(Constants.URL_UPLOADS_TOKEN, id));
	client.setAuthentication(apiKey);
	try {
	    client.execute(RequestMethod.GET);
	    Log.e("UploadsToken", client.getResponse());
	    DataUploadToken data = new DataUploadToken(client.getResponse());
	    return data;
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	    throw new RuntimeException("Token or parsing error", e);
	}
    }
}
