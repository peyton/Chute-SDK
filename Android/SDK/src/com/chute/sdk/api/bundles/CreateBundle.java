package com.chute.sdk.api.bundles;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class CreateBundle extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = CreateBundle.class.getSimpleName();
    private final Context context;
    private final String assetIds;

    /**
     * @param context
     * @param assetIds
     *            comma delimited asset ids ex. 1,3,4,5
     */
    public CreateBundle(Context context, String assetIds) {
	this.context = context;
	this.assetIds = assetIds;
    }

    public CreateBundle(Context context, List<String> assetIds) {
	this.context = context;
	this.assetIds = TextUtils.join(",", assetIds);
    }

    public CreateBundle(Context context, String[] assetIds) {
	this.context = context;
	this.assetIds = TextUtils.join(",", assetIds);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
	try {
	    ChuteRestClient client = new ChuteRestClient(ChuteConstants.URL_BUNDLE_CREATE);
	    DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	    client.setAuthentication(user.getPassword());
	    Log.e(TAG, "Asset IDS " + assetIds);
	    client.addParam("asset_ids", assetIds);
	    client.execute(RequestMethod.POST);
	    Log.e(TAG, client.getResponse());
	    // parser.parse(client.getResponse());
	    return true;
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}
	return false;
    }
}
