package com.chute.sdk.api.assets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.SingleChuteAsset;
import com.chute.sdk.api.hearts.HeartsGet;

public class AssetsLoadFromDatabaseTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = AssetsLoadFromDatabaseTask.class.getSimpleName();

    private final String parcelId;
    private final String chuteId;
    private final Context context;
    private final List<SingleChuteAsset> assets = new ArrayList<SingleChuteAsset>();
    private final ProgressBar pb;
    private final OnLoadAssetsComplete onLoadDatabaseComplete;

    public AssetsLoadFromDatabaseTask(Context context, ProgressBar pb, String parcelId, String chuteId,
	    OnLoadAssetsComplete onLoadDatabaseComplete) {
	this.context = context;
	this.pb = pb;
	this.parcelId = parcelId;
	this.chuteId = chuteId;
	this.onLoadDatabaseComplete = onLoadDatabaseComplete;
    }

    @Override
    protected void onPreExecute() {
	pb.setVisibility(View.VISIBLE);
	super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	Cursor c = null;
	try {
	    int counter = 0;
	    while ((c == null || c.getCount() == 0) && counter < 5) {
		c = context.getContentResolver().query(DB.InboxParcels.CONTENT_URI, null,
			DB.InboxParcels._ID + "=? AND " + DB.InboxParcels.CHUTE_ID + "=?",
			new String[] { parcelId, chuteId }, null);
		Thread.sleep(800);
		counter++;
	    }
	    if (c.moveToFirst()) {
		SingleChuteAsset asset;
		assets.clear();
		String userId = c.getString(c.getColumnIndex(DB.InboxParcels.USER_ID));
		String userName = c.getString(c.getColumnIndex(DB.InboxParcels.USER_NAME));
		String userAvatar = c.getString(c.getColumnIndex(DB.InboxParcels.USER_AVATAR));
		c = context.getContentResolver().query(
			DB.InboxParcelsAssets.CONTENT_URI,
			null,
			DB.InboxParcelsAssets.PARCEL_ID + "=? AND "
				+ DB.InboxParcelsAssets.CHUTE_ID + "=?",
			new String[] { parcelId, chuteId }, null);
		if (c.moveToFirst()) {
		    do {
			asset = new SingleChuteAsset();
			asset.setId(c.getString(c.getColumnIndex(DB.InboxParcelsAssets._ID)));
			String url = c.getString(c.getColumnIndex(DB.InboxParcelsAssets.URL));
			asset.setUrl(url);
			asset.setShare_url(c.getString(c
				.getColumnIndex(DB.InboxParcelsAssets.SHARE_URL)));
			asset.setComments(c.getString(c
				.getColumnIndex(DB.InboxParcelsAssets.COMMENTS)));
			asset.user.setUserAvatar(userAvatar);
			asset.user.setUserName(userName);
			asset.user.setUserId(userId);
			assets.add(asset);
		    } while (c.moveToNext());
		}
	    }
	    HeartsGet heartsGet = new HeartsGet(context, HeartsGet.MY_USER_ID_CONSTANT, null);
	    ArrayList<Integer> data = new ArrayList<Integer>();
	    ;
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
	} catch (SQLiteException e) {
	    Log.e(TAG, "", e);
	} catch (SQLException e) {
	    Log.e(TAG, "", e);
	} catch (Exception e) {
	    Log.e(TAG, "", e);
	} finally {
	    if (c != null) {
		c.close();
	    }
	}
	return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	pb.setVisibility(View.GONE);
	if (result == true) {
	    onLoadDatabaseComplete.onSuccess(assets);
	}
	super.onPostExecute(result);
    }
}