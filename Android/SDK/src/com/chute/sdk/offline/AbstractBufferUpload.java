package com.chute.sdk.offline;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.chute.examples.kitchensink.models.DataAccountUserPass;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteRestClient;
import com.chute.examples.kitchensink.utils.ChuteRestClient.RequestMethod;
import com.chute.sdk.api.parsers.JSONParserInterface;

public abstract class AbstractBufferUpload {

    public static final String TAG = AbstractBufferUpload.class.getSimpleName();
    private final ChuteRestClient client;
    private final Context context;
    private JSONParserInterface parser;

    public AbstractBufferUpload(Context context, String url) {
	client = new ChuteRestClient(url);
	this.context = context;
    }

    public AbstractBufferUpload(Context context) {
	client = new ChuteRestClient();
	this.context = context;
    }

    public Context getContext() {
	return context;
    }

    public ChuteRestClient getClient() {
	return client;
    }

    public void executeRequest(RequestMethod method) throws Exception {
	client.setSocketTimeout(12000);
	client.setConnectionTimeout(22000);
	DataAccountUserPass user = ChuteAccountUtils.getAccountInfo(context);
	client.setAuthentication(user.getPassword());
	client.execute(method);
	Log.e(TAG, client.getResponse());
	if (parser != null) {
	    parseResponse(client.getResponse());
	}
    }

    public void setParser(JSONParserInterface parser) {
	this.parser = parser;
    }

    public void addParam(String name, String value) {
	client.addParam(name, value);
    }

    public void parseResponse(String response) throws JSONException {
	parser.parse(response);
    }

}
