package com.chute.sdk.api.bundles;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class RetrieveBundle extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = RetrieveBundle.class.getSimpleName();

    private final Context context;

    private final String bundleId;

    public RetrieveBundle(Context context, String bundleId) {
	this.context = context;
	this.bundleId = bundleId;
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
	try {
	    ChuteRestClient client = new ChuteRestClient(String.format(
		    ChuteConstants.URL_BUNDLE_RETRIEVE, bundleId));
	    DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	    client.setAuthentication(user.getPassword());
	    client.execute(RequestMethod.GET);
	    Log.e(TAG, client.getResponse());
	    // TODO Parse the response when api is available
	    return true;
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}
	return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
	super.onPostExecute(result);
	// TODO send the result back
    }
}
