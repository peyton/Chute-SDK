package com.chute.sdk.api.chutes;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.ChuteOwnerStatus;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.OnApiCallComplete;

public class ChutesDelete extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = ChutesDelete.class.getSimpleName();
    private final Context context;
    private final String id;
    private final OnApiCallComplete onApiCallComplete;

    private final ChuteOwnerStatus ownerStatus;

    public ChutesDelete(Context context, String id, ChuteOwnerStatus ownerStatus,
	    OnApiCallComplete onApiCallComplete) {
	this.context = context;
	this.id = id;
	this.ownerStatus = ownerStatus;
	this.onApiCallComplete = onApiCallComplete;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	RestClient client;
	if (ownerStatus == ChuteOwnerStatus.OWNER) {
	    client = new RestClient(String.format(Constants.URL_CHUTES_DELETE, id));
	} else {
	    client = new RestClient(String.format(Constants.URL_CHUTES_LEAVE, id));
	}
	try {
	    DataAccountUserPass user = AccountUtils.getAccountInfo(context);
	    client.setAuthentication(user.getPassword());
	    if (ownerStatus == ChuteOwnerStatus.OWNER) {
		client.execute(RequestMethod.DELETE);
	    } else {
		client.execute(RequestMethod.POST);
	    }

	    if (ownerStatus == ChuteOwnerStatus.OWNER) {
		Log.e(TAG, client.getResponse());
		parseChutesDelete(client.getResponse());
	    } else {
		if (client.getResponseCode() == HttpStatus.SC_OK) {
		    context.getContentResolver().delete(DB.ChutesColumns.CONTENT_URI,
			    DB.ChutesColumns._ID + "=?", new String[] { id });
		    return true;
		}
		return false;
	    }
	    return true;
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	    ;
	}
	return false;
    }

    private void parseChutesDelete(String response) throws JSONException {
	JSONObject obj = new JSONObject(response);
	if (obj.has("id")) {
	    context.getContentResolver().delete(DB.ChutesColumns.CONTENT_URI,
		    DB.ChutesColumns._ID + "=?", new String[] { obj.getString("id") });
	}
    }

    @Override
    protected void onPostExecute(Boolean result) {
	if (result == true) {
	    onApiCallComplete.onSuccess();
	} else {
	    onApiCallComplete.onFail();
	}
	super.onPostExecute(result);
    }
}
