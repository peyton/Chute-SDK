package com.chute.sdk.api.metadata;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteConstants;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;

public class MetaPost extends AsyncTask<Void, Void, Boolean> {
    @SuppressWarnings("unused")
    private static final String TAG = MetaPost.class.getSimpleName();
    private final Context context;
    private final MetaType metaType;
    private final String id;
    private final String key;

    public MetaPost(Context context, MetaType metaType, String id, String key) {
	this.context = context;
	this.metaType = metaType;
	this.id = id;
	this.key = key;
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
	try {
	    ChuteRestClient client = new ChuteRestClient(String.format(
		    ChuteConstants.URL_META_REQUEST, MetaUtils.resolveMetaType(metaType), id, key));
	    DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	    client.setAuthentication(user.getPassword());
	    client.execute(RequestMethod.POST);
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
