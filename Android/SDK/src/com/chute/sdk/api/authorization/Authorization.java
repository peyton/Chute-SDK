package com.chute.sdk.api.authorization;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteAuthenticationFactory;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class Authorization extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = Authorization.class.getSimpleName();
    private final Context context;
    private final String code;

    public Authorization(Context context, String code) {
	this.context = context;
	this.code = code;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	try {
	    ChuteRestClient client = new ChuteRestClient(ChuteConstants.URL_AUTHENTICATION_CODE);
	    client.addParam("code", code);
	    client.addParam("scope", "profile");
	    client.addParam("client_id", "4e44f307f3e3bd09ac000001");
	    client.addParam("client_secret",
		    "d6a9f6b219291ded44e763c22599e0d9f8daeef668898d088ab870d60911f642");
	    client.addParam("grant_type", "authorization_code");
	    client.addParam("redirect_uri", ChuteAuthenticationFactory.getCallbackUri());
	    client.execute(RequestMethod.POST);
	    Log.e(TAG, client.getResponse());
	    JSONObject object = new JSONObject(client.getResponse());
	    ChuteAccountUtils.saveApiKey(object.getString("access_token"), context);
	    return true;
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}
	return false;
    }
}
