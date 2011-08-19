package com.chute.examples.kitchensink.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.chute.examples.kitchensink.R;
import com.chute.sdk.api.bundles.CreateBundle;

public class CreateBundleActivity extends Activity {
    @SuppressWarnings("unused")
    private static final String TAG = CreateBundleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.create_bundle_activity);
	ArrayList<String> list = new ArrayList<String>();
	list.add("16");
	new CreateBundle(getApplicationContext(), list).execute();
    }
}
