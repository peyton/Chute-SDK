package com.chute.sdk.api.chutes;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.models.DataCreateChuteRequest;
import com.chute.examples.kitchensink.utils.AccountUtils;
import com.chute.examples.kitchensink.utils.Constants;
import com.chute.examples.kitchensink.utils.RestClient;
import com.chute.examples.kitchensink.utils.RestClient.RequestMethod;
import com.chute.sdk.api.memberships.InviteMembers;

public class ChutesMake extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = ChutesMake.class.getSimpleName();
    private final Context context;
    private HashMap<Integer, String> members;
    private final ArrayList<DataCreateChuteRequest> chutes;
    private final OnChuteCreated onApiCallComplete;

    public interface OnChuteCreated {
	public void onSuccess(int id);

	public void onFail();
    }

    int chuteId;

    public ChutesMake(Context context, ArrayList<DataCreateChuteRequest> selected,
	    OnChuteCreated onApiCallComplete) {
	this.context = context;
	this.chutes = selected;
	this.onApiCallComplete = onApiCallComplete;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	try {
	    for (int i = 0; i < chutes.size(); i++) {
		RestClient client = new RestClient(Constants.URL_CHUTES_CREATE);
		DataAccountUserPass user = AccountUtils.getAccountInfo(context);
		client.setAuthentication(user.getPassword());
		DataCreateChuteRequest.CreateChute chute = chutes.get(i).chute;
		client.addParam("chute[name]", chute.getName());
		client.addParam("chute[permission_view]",
			String.valueOf(chute.getPermission_view()));
		client.addParam("chute[permission_add_members]",
			String.valueOf(chute.getPermission_add_members()));
		client.addParam("chute[permission_add_photos]",
			String.valueOf(chute.getPermission_add_photos()));
		client.addParam("chute[permission_add_comments]",
			String.valueOf(chute.getPermission_add_comments()));
		client.addParam("chute[moderate_members]",
			String.valueOf(chute.getModerate_members()));
		client.addParam("chute[moderate_photos]",
			String.valueOf(chute.getModerate_photos()));
		client.addParam("chute[moderate_comments]",
			String.valueOf(chute.getModerate_comments()));

		client.execute(RequestMethod.POST);
		Log.v(TAG, client.getResponse());
		parseChuteCreate(context.getContentResolver(), client.getResponse());
		Log.e(TAG, chutes.get(i).members.getEmail().toString());
		InviteMembers inviteMembers = new InviteMembers(context, String.valueOf(chuteId),
			chutes.get(i).members.getEmail().toString());
		inviteMembers.inviteMembers();
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

    @Override
    protected void onPostExecute(Boolean result) {
	if (result == false) {
	    onApiCallComplete.onFail();
	} else {
	    onApiCallComplete.onSuccess(0);
	}
	super.onPostExecute(result);
    }

    private void parseChuteCreate(ContentResolver contentResolver, String response)
	    throws JSONException {
	JSONObject obj = new JSONObject(response);
	ContentValues values;
	values = new ContentValues();
	Log.e("obj", obj.toString());
	values.put(DB.ChutesColumns._ID, obj.getInt("id"));
	chuteId = obj.getInt("id");
	// values.put(DB.ChutesColumns.ARE_PHOTOS_DOWNLOADED,
	// obj.getBoolean("are_photos_downloaded"));
	// values.put(DB.ChutesColumns.ARE_PHOTOS_MODERATED,
	// obj.getBoolean("are_photos_moderated"));
	// values.put(DB.ChutesColumns.CAN_MEMBERS_ADD_PHOTOS,obj.getBoolean("can_members_add_photos")
	// );
	// values.put(DB.ChutesColumns.CAN_MEMBERS_INVITE_OTHERS,obj.getBoolean("can_members_invite_others"));
	// values.put(DB.ChutesColumns.CHUTE_VISIBILITY,
	// obj.getInt("chute_visibility"));
	values.put(DB.ChutesColumns.MEMBERS_COUNT, obj.getString("members_count"));
	values.put(DB.ChutesColumns.NAME, obj.getString("name"));
	values.put(DB.ChutesColumns.ASSET_COUNT, obj.getString("assets_count"));
	values.put(DB.ChutesColumns.RECENT_THUMB, obj.getString("recent_thumbnail"));
	values.put(DB.ChutesColumns.SHORTCUT, obj.getString("shortcut"));
	contentResolver.insert(DB.ChutesColumns.CONTENT_URI, values);
    }
}
