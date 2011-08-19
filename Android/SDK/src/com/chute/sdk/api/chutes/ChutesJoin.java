package com.chute.sdk.api.chutes;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;
import com.chute.sdk.api.OnApiCallComplete;

public class ChutesJoin extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = ChutesJoin.class.getSimpleName();
    private final Context context;
    private final OnApiCallComplete onApiCallComplete;
    public HashMap<Integer, String> tick;

    public ChutesJoin(Context context, HashMap<Integer, String> tick,
	    OnApiCallComplete onApiCallComplete) {
	this.context = context;
	this.tick = tick;
	this.onApiCallComplete = onApiCallComplete;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	Iterator iter = tick.values().iterator();
	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	try {
	    while (iter.hasNext()) {

		ChuteRestClient client = new ChuteRestClient(String.format(ChuteConstants.URL_CHUTES_JOIN,
			String.valueOf(iter.next())));
		client.setAuthentication(user.getPassword());

		client.execute(RequestMethod.GET);
		Log.v(TAG, client.getResponse());
		parseChuteJoin(client.getResponse());
		continue;
	    }
	    return true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }

    private Boolean parseChuteJoin(String response) throws JSONException {
	JSONObject obj = new JSONObject(response);
	return obj.has("status");
    }

    @Override
    protected void onPostExecute(Boolean result) {
	if (result) {
	    onApiCallComplete.onSuccess();
	} else {
	    onApiCallComplete.onFail();
	}
	super.onPostExecute(result);
    }

}
