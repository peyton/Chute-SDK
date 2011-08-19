package com.chute.sdk.api.upload;

import android.util.Log;

import com.chute.examples.kitchensink.models.DataUploadToken;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class UploadsToken {

    private static final String TAG = UploadsToken.class.getSimpleName();

    public static DataUploadToken UploadToken(String id, String apiKey) {
	ChuteRestClient client = new ChuteRestClient(String.format(ChuteConstants.URL_UPLOADS_TOKEN, id));
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
