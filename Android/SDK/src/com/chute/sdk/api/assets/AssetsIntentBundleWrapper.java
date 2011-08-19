package com.chute.sdk.api.assets;

import java.util.Iterator;

import android.os.Bundle;
import android.util.Log;

public class AssetsIntentBundleWrapper {
    public static final String CHUTE_NAME = "chuteName";
    public static final String URL = "url";
    public static final String PARCEL_ID = "parcelId";
    public static final String CHUTE_ID = "chuteId";

    public static final String CHUTE_ARCHIVE_ID = "archive";
    public static final String CHUTE_HEARTS_ID = "heart";

    @SuppressWarnings("unused")
    private static final String TAG = AssetsIntentBundleWrapper.class.getSimpleName();

    private final Bundle bundle;

    public AssetsIntentBundleWrapper(Bundle bundle) {
	this.bundle = bundle;
    }

    public String getChuteId() {
	return bundle.getString(CHUTE_ID);
    }

    public String getParcelId() {
	return bundle.getString(PARCEL_ID);
    }

    public String getSelectedImageUrl() {
	return bundle.getString(URL);
    }

    public String getChuteName() {
	return bundle.getString(CHUTE_NAME);
    }

    public boolean containsChuteId() {
	return bundle.containsKey(CHUTE_ID);
    }

    public boolean containsChuteName() {
	return bundle.containsKey(CHUTE_NAME);
    }

    public boolean containsParcelId() {
	return bundle.containsKey(PARCEL_ID);
    }

    public boolean containsUrl() {
	return bundle.containsKey(URL);
    }

    @Override
    public String toString() {
	String toString = "";
	Iterator<String> it = bundle.keySet().iterator();
	while (it.hasNext()) {
	    String key = it.next();
	    toString += "key = " + key + "value " + bundle.getString(key) + ", ";
	    Log.d(TAG, key);
	}
	return toString;
    }

}
