package com.chute.sdk.api.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.chute.examples.kitchensink.models.DataParsedUserMeJson;
import com.chute.examples.kitchensink.utils.AccountUtils;

public class UserMeJSONParser extends AbstractJSONParser {

    private DataParsedUserMeJson dataParsedUserMeJson;

    public UserMeJSONParser(Context context) {
	super(context);
    }

    @Override
    public void parse(final String response) throws JSONException {
	JSONObject obj = new JSONObject(response);
	dataParsedUserMeJson = new DataParsedUserMeJson();
	dataParsedUserMeJson.setId(obj.getString("id"));
	AccountUtils.saveUserId(dataParsedUserMeJson.getId(), getContext());
	dataParsedUserMeJson.setName(obj.getString("name"));
	dataParsedUserMeJson.setAvatar(obj.getString("avatar"));
	dataParsedUserMeJson.getAssets().setPhotos(obj.getJSONObject("assets").getString("photos"));
	dataParsedUserMeJson.getAssets().setPending(
		obj.getJSONObject("assets").getString("pending"));
	dataParsedUserMeJson.getStorage().setAvailable(
		obj.getJSONObject("storage").getString("available"));
	dataParsedUserMeJson.getStorage().setCurrent(
		obj.getJSONObject("storage").getString("current"));
    }

    public DataParsedUserMeJson getDataParsedUserMeJson() {
	return dataParsedUserMeJson;
    }
}
