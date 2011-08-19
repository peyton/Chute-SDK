package com.chute.sdk.api.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.chute.examples.kitchensink.models.SingleChuteAsset;

public class ChuteAssetsJSONParser extends AbstractJSONParser {

    @SuppressWarnings("unused")
    private static final String TAG = ChuteAssetsJSONParser.class.getSimpleName();

    private final List<SingleChuteAsset> assets = new ArrayList<SingleChuteAsset>();

    public ChuteAssetsJSONParser(Context context) {
	super(context);
    }

    @Override
    public void parse(String response) throws JSONException {
	JSONObject obj = new JSONObject(response);
	JSONArray array = obj.getJSONArray("data");
	SingleChuteAsset asset = new SingleChuteAsset();
	for (int index = 0; index < array.length(); index++) {
	    obj = array.getJSONObject(index);
	    asset = new SingleChuteAsset();
	    asset.setId(obj.getString("id"));
	    asset.setUrl(obj.getString("url"));
	    asset.setShare_url(obj.getString("share_url"));
	    asset.setComments(obj.getString("comments"));
	    asset.user.setUserAvatar(obj.getJSONObject("user").getString("avatar"));
	    asset.user.setUserId(obj.getJSONObject("user").getString("id"));
	    asset.user.setUserName(obj.getJSONObject("user").getString("name"));
	    assets.add(asset);
	}
    }

    public List<SingleChuteAsset> getAssets() {
	return assets;
    }
}
