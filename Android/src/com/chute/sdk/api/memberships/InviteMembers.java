package com.chute.sdk.api.memberships;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;

public class InviteMembers {
    private static final String TAG = InviteMembers.class.getSimpleName();
    private final Context context;
    private final String id;
    private String emails;

    public InviteMembers(Context context, String id, String emails) {
	this.context = context;
	this.id = id;
	this.emails = emails;
    }

    public boolean inviteMembers() {
	RestClient client = new RestClient(String.format(Constants.URL_MEMBERSHIP_INVITE, id));
	DataAccountUserPass user = AccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	if (emails.contentEquals("")) {
	    emails = new JSONArray().toString();
	}
	Log.e(TAG, "All chosen Emails " + emails);
	client.addParam("[providers][email]", emails);
	client.addParam("[providers][facebook]", new JSONArray().toString());
	client.addParam("[providers][chute]", new JSONArray().toString());
	try {
	    client.execute(RequestMethod.POST);
	    Log.v(TAG, client.getResponse() + String.valueOf(client.getResponseCode()));
	    return client.getResponseCode() == 200;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }

}
