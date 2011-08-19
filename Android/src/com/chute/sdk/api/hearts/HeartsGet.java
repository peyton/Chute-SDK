package com.chute.sdk.api.hearts;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.parsers.HeartsJSONParser;

public class HeartsGet extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = HeartsGet.class.getSimpleName();
    public static final String MY_USER_ID_CONSTANT = "me";
    private static final String HEARTS = "hearts";

    interface OnHeartsGet {
	public void onSuccess(ArrayList<Integer> data);
    }

    private final Context context;
    private final String userId;

    private ArrayList<Integer> data;
    private final OnHeartsGet onHeartsGet;

    public HeartsGet(Context context, String userId, OnHeartsGet onHeartsGet) {
	this.context = context;
	this.userId = userId;
	this.onHeartsGet = onHeartsGet;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	return getHearts();
    }

    @Override
    protected void onPostExecute(Boolean result) {
	if (result) {
	    if (onHeartsGet != null) {
		onHeartsGet.onSuccess(data);
	    }
	}
	super.onPostExecute(result);
    }

    public boolean getHearts() {
	RestClient client = new RestClient(String.format(Constants.URL_HEARTS_GET, userId));
	DataAccountUserPass user = AccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	try {
	    client.execute(RequestMethod.GET);
	    Log.v(TAG, client.getResponse());
	    HeartsJSONParser parser = new HeartsJSONParser(context);
	    parser.parse(client.getResponse());
	    data = parser.getData();
	    JSONArray array = new JSONObject(client.getResponse()).getJSONArray("data");
	    saveHeartsList(array);
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }

    public ArrayList<Integer> getData() {
	return data;
    }

    private boolean saveHeartsList(JSONArray array) {
	SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
	SharedPreferences.Editor editor = setting.edit();
	editor.putString(HEARTS, array.toString());
	return editor.commit();
    }

    public static String restoreHeartsList(Context context) {
	SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
	return setting.getString(HEARTS, "[]");
    }
}
