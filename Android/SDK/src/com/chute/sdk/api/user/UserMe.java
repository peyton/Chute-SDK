package com.chute.sdk.api.user;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.models.DataParsedUserMeJson;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;
import com.chute.sdk.api.parsers.UserMeJSONParser;

public class UserMe extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = UserMe.class.getSimpleName();
    private final Context context;

    public interface OnUserMeComplete {
	public void onSuccess(DataParsedUserMeJson data);

	public void onFail();
    }

    private final OnUserMeComplete onUserMeComplete;
    private UserMeJSONParser parser;

    public UserMe(Context context, OnUserMeComplete onUserMeComplete) {
	this.context = context;
	this.onUserMeComplete = onUserMeComplete;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	ChuteRestClient client = new ChuteRestClient(ChuteConstants.URL_USER_ME);
	client.setSocketTimeout(9000);
	client.setConnectionTimeout(5000);
	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	try {
	    client.execute(RequestMethod.GET);
	    Log.v(TAG, client.getResponse());
	    parser = new UserMeJSONParser(context);
	    parser.parse(client.getResponse());
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	if (result == true) {
	    if (onUserMeComplete != null) {
		onUserMeComplete.onSuccess(parser.getDataParsedUserMeJson());
	    }
	} else {
	    if (onUserMeComplete != null) {
		onUserMeComplete.onFail();
	    }
	}
	super.onPostExecute(result);
    }
}
