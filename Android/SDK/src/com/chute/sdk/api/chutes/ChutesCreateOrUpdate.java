package com.chute.sdk.api.chutes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.models.DataCreateChuteRequest.CreateChute;
import com.chute.examples.kitchensink.models.FormMembersContactEmailInfo;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;
import com.chute.sdk.api.memberships.InviteMembers;
import com.chute.sdk.api.parsers.CreateChuteJSONParser;
import com.chute.sdk.offline.ConstantsBuffer;

public class ChutesCreateOrUpdate extends AsyncTask<Void, Void, Boolean> {

    public static final int OFFLINE_CHUTE = -1;
    private static final String TAG = ChutesCreateOrUpdate.class.getSimpleName();
    private final Context context;
    private final CreateChute chute;
    private final OnChuteCreated onChuteCreateCallComplete;
    private int chuteId = OFFLINE_CHUTE;
    private final ArrayList<FormMembersContactEmailInfo> contactsInstance;

    public interface OnChuteCreated {
	/**
	 * @param id
	 *            returns the new chute's id.
	 */
	public void onSuccess(int id);

	public void onFail();
    }

    public ChutesCreateOrUpdate(Context context, CreateChute chute,
	    ArrayList<FormMembersContactEmailInfo> contactsInstance,
	    OnChuteCreated onChuteCreateCallComplete) {
	this.context = context;
	this.chute = chute;
	this.contactsInstance = contactsInstance;
	this.onChuteCreateCallComplete = onChuteCreateCallComplete;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	boolean isChuteCreated = false;
	ChuteRestClient client;
	RequestMethod method;
	if (TextUtils.isEmpty(chute.getId())) {
	    Log.e(TAG, "Chute Create");
	    client = new ChuteRestClient(ChuteConstants.URL_CHUTES_CREATE);
	    method = RequestMethod.POST;
	} else {
	    Log.e(TAG, "Chute Update - Chute id: " + chute.getId());
	    client = new ChuteRestClient(String.format(ChuteConstants.URL_CHUTES_UPDATE,
		    chute.getId()));
	    method = RequestMethod.PUT;
	}

	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	Log.e(TAG, user.getUsername());
	Log.e(TAG, user.getPassword());
	client.setAuthentication(user.getPassword());
	client.addParam("chute[name]", chute.getName());
	client.addParam("chute[permission_view]", String.valueOf(chute.getPermission_view()));
	client.addParam("chute[permission_add_members]",
		String.valueOf(chute.getPermission_add_members()));
	client.addParam("chute[permission_add_photos]",
		String.valueOf(chute.getPermission_add_photos()));
	client.addParam("chute[permission_add_comments]",
		String.valueOf(chute.getPermission_add_comments()));
	client.addParam("chute[moderate_members]", String.valueOf(chute.getModerate_members()));
	client.addParam("chute[moderate_photos]", String.valueOf(chute.getModerate_photos()));
	client.addParam("chute[moderate_comments]", String.valueOf(chute.getModerate_comments()));
	Log.e(TAG, chute.toString());
	try {
	    client.execute(method);
	    Log.v(TAG, client.getResponse());
	    CreateChuteJSONParser parser = new CreateChuteJSONParser(context);
	    parser.parse(client.getResponse());
	    chuteId = parser.getChuteId();
	    isChuteCreated = true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	if (isChuteCreated == true) {
	    JSONArray array = new JSONArray();
	    for (FormMembersContactEmailInfo s : contactsInstance) {
		for (String email : s.getEmails()) {
		    array.put(email);
		}
	    }
	    InviteMembers inviteMembers = new InviteMembers(context, String.valueOf(chuteId),
		    array.toString());
	    inviteMembers.inviteMembers();
	}
	if (isChuteCreated == false) {
	    try {
		final JSONObject obj = new JSONObject();
		obj.put("chute[id]", chute.getId());
		obj.put("chute[name]", chute.getName());
		obj.put("chute[permission_view]", String.valueOf(chute.getPermission_view()));
		obj.put("chute[permission_add_members]",
			String.valueOf(chute.getPermission_add_members()));
		obj.put("chute[permission_add_photos]",
			String.valueOf(chute.getPermission_add_photos()));
		obj.put("chute[permission_add_comments]",
			String.valueOf(chute.getPermission_add_comments()));
		obj.put("chute[moderate_members]", String.valueOf(chute.getModerate_members()));
		obj.put("chute[moderate_photos]", String.valueOf(chute.getModerate_photos()));
		obj.put("chute[moderate_comments]", String.valueOf(chute.getModerate_comments()));
		ContentValues values = new ContentValues();
		values.put(DB.Buffer.DATA, obj.toString());
		values.put(DB.Buffer.TYPE, ConstantsBuffer.TYPE_CHUTES);
		context.getContentResolver().insert(DB.Buffer.CONTENT_URI, values);
		isChuteCreated = true;
	    } catch (JSONException e) {
		Log.d(TAG, "", e);
	    }
	}

	return isChuteCreated;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	if (result == false) {
	    onChuteCreateCallComplete.onFail();
	} else {
	    onChuteCreateCallComplete.onSuccess(chuteId);
	}
	super.onPostExecute(result);
    }
}
