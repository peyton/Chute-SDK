package com.chute.sdk.api.assets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.models.SingleChuteAsset;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;
import com.chute.sdk.api.hearts.HeartsGet;
import com.chute.sdk.api.parsers.ChuteAssetsJSONParser;

public class AssetsLoadFromApiTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = AssetsLoadFromApiTask.class.getSimpleName();

    private static final String ASSETS_ARCHIVE = "key_archive";

    private static final String ASSETS_HEARTS = "key_hearts";

    private final String chuteId;
    private final Context context;
    private List<SingleChuteAsset> assets = new ArrayList<SingleChuteAsset>();
    private final ProgressBar pb;
    private final OnLoadAssetsComplete onLoadAssetsComplete;

    public AssetsLoadFromApiTask(Context context, ProgressBar pb, String chuteId,
	    OnLoadAssetsComplete onLoadAssetsComplete) {
	this.context = context;
	this.pb = pb;
	this.chuteId = chuteId;
	this.onLoadAssetsComplete = onLoadAssetsComplete;
    }

    @Override
    protected void onPreExecute() {
	pb.setVisibility(View.VISIBLE);
	super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

	boolean isApiCallSuccessful = false;

	ChuteRestClient client = new ChuteRestClient(String.format(
		ChuteConstants.URL_CHUTE_ID_ASSETS, chuteId));
	client.setSocketTimeout(15000);
	client.setConnectionTimeout(6000);
	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	try {
	    client.execute(RequestMethod.GET);
	    Log.v(TAG, client.getResponse());
	    ChuteAssetsJSONParser parser = new ChuteAssetsJSONParser(context);
	    parser.parse(client.getResponse());
	    assets = parser.getAssets();
	    if (chuteId.contentEquals(AssetsIntentBundleWrapper.CHUTE_ARCHIVE_ID)) {
		saveArchiveAssetsList(client.getResponse());
	    } else if (chuteId.contentEquals(AssetsIntentBundleWrapper.CHUTE_HEARTS_ID)) {
		saveHeartsAssetsList(client.getResponse());
	    }
	    isApiCallSuccessful = true;
	} catch (JSONException e) {
	    Log.w(TAG, e.getMessage(), e);
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	try {
	    if (isApiCallSuccessful == false) {
		if (chuteId.contentEquals(AssetsIntentBundleWrapper.CHUTE_ARCHIVE_ID)
			|| chuteId.contentEquals(AssetsIntentBundleWrapper.CHUTE_HEARTS_ID)) {
		    ChuteAssetsJSONParser parser = new ChuteAssetsJSONParser(context);
		    parser.parse(chuteId.contentEquals(AssetsIntentBundleWrapper.CHUTE_ARCHIVE_ID) == true ? restoreArchiveAssetsList(context)
			    : restoreHeartsAssetsList(context));
		    assets = parser.getAssets();
		    isApiCallSuccessful = true;
		} else {
		    return false;
		}
	    }
	    HeartsGet heartsGet = new HeartsGet(context, HeartsGet.MY_USER_ID_CONSTANT, null);
	    ArrayList<Integer> data = new ArrayList<Integer>();
	    if (heartsGet.getHearts()) {
		data = heartsGet.getData();
	    } else {
		try {
		    JSONArray array = new JSONArray(HeartsGet.restoreHeartsList(context));
		    for (int i = 0; i < array.length(); i++) {
			data.add(array.getInt(i));
		    }
		} catch (JSONException e) {
		    Log.w(TAG, "", e);
		}
	    }
	    HashSet<Integer> set = new HashSet<Integer>();
	    for (Integer integer : data) {
		set.add(integer);
	    }

	    for (int i = 0; i < assets.size(); i++) {
		if (set.contains(Integer.parseInt(assets.get(i).getId()))) {
		    assets.get(i).setHeart(true);
		}
	    }

	    return true;
	} catch (Exception e) {
	    Log.w(TAG, e.getMessage(), e);
	}
	return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	pb.setVisibility(View.GONE);
	if (result == true) {
	    onLoadAssetsComplete.onSuccess(assets);
	}
	super.onPostExecute(result);
    }

    private boolean saveHeartsAssetsList(String response) {
	SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
	SharedPreferences.Editor editor = setting.edit();
	editor.putString(ASSETS_HEARTS, response);
	return editor.commit();
    }

    public static String restoreHeartsAssetsList(Context context) {
	SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
	return setting.getString(ASSETS_HEARTS, "{}");
    }

    private boolean saveArchiveAssetsList(String response) {
	SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
	SharedPreferences.Editor editor = setting.edit();
	editor.putString(ASSETS_ARCHIVE, response);
	return editor.commit();
    }

    public static String restoreArchiveAssetsList(Context context) {
	SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
	return setting.getString(ASSETS_ARCHIVE, "{}");
    }
}