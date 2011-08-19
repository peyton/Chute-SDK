package com.chute.examples.kitchensink.activities;

import android.app.Activity;
import android.os.Bundle;

import com.chute.examples.kitchensink.R;

public class ViewBundleActivity extends Activity {
    @SuppressWarnings("unused")
    private static final String TAG = ViewBundleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.view_bundle_activity);
    }
}
