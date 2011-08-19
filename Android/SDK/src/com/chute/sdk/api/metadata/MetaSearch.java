package com.chute.sdk.api.metadata;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class MetaSearch extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = MetaSearch.class.getSimpleName();
    private final Context context;
    private final String key;

    /**
     * Searches the meta info for a specific key
     * 
     * @param context
     * @param key
     */
    public MetaSearch(Context context, String key) {
	this.context = context;
	this.key = key;
    }

    /**
     * Gets all the meta info saved
     * 
     * @param context
     */
    public MetaSearch(Context context) {
	this.context = context;
	this.key = null;
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
	try {
	    ChuteRestClient client;
	    if (key == null) {
		client = new ChuteRestClient(ChuteConstants.URL_META_EVERYTHING);

	    } else {
		client = new ChuteRestClient(String.format(ChuteConstants.URL_META_SEARCH_KEY, key));
	    }
	    DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	    client.setAuthentication(user.getPassword());
	    client.execute(RequestMethod.GET);
	    Log.e(TAG, client.getResponse());
	    // TODO Parse the response
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
