package com.chute.sdk.api.memberships;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

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
	ChuteRestClient client = new ChuteRestClient(String.format(ChuteConstants.URL_MEMBERSHIP_INVITE, id));
	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
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
