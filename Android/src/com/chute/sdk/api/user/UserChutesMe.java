package com.chute.sdk.api.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.OnApiCallComplete;
import com.chute.sdk.api.parsers.CreateChuteJSONParser;

public class UserChutesMe extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = UserChutesMe.class.getSimpleName();
    private final Context context;
    private final OnApiCallComplete onApiCallComplete;

    public UserChutesMe(Context context, OnApiCallComplete onApiCallComplete) {
	this.context = context;
	this.onApiCallComplete = onApiCallComplete;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	RestClient client = new RestClient(Constants.URL_USER_CHUTES_ME);
	client.setSocketTimeout(9000);
	client.setConnectionTimeout(9000);
	DataAccountUserPass user = AccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	try {
	    client.execute(RequestMethod.GET);
	    Log.v(TAG, client.getResponse());
	    parseUserChutes(context.getContentResolver(), client.getResponse());
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }

    private void parseUserChutes(ContentResolver contentResolver, String response)
	    throws JSONException {
	JSONObject obj = new JSONObject(response);
	JSONArray array = obj.getJSONArray("data");
	contentResolver.delete(DB.ChutesColumns.CONTENT_URI, null, null);
	CreateChuteJSONParser parser = new CreateChuteJSONParser(context);
	for (int i = 0; i < array.length(); i++) {
	    parser.parse(array.getJSONObject(i).toString());
	}
    }

    @Override
    protected void onPostExecute(Boolean result) {
	if (result == true) {
	    if (onApiCallComplete != null) {
		onApiCallComplete.onSuccess();
	    }
	} else {
	    if (onApiCallComplete != null) {
		onApiCallComplete.onFail();
	    }
	}
	super.onPostExecute(result);
    }
}
