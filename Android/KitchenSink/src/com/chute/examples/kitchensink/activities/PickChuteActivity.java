package com.chute.examples.kitchensink.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.adapters.BrowseChutesAdapter;
import com.chute.examples.kitchensink.adapters.BrowseChutesAdapter.ChuteWrapper;
import com.chute.examples.kitchensink.db.DB;
import com.chute.sdk.api.OnApiCallComplete;
import com.chute.sdk.api.assets.AssetsIntentBundleWrapper;
import com.chute.sdk.api.user.UserChutesMe;

public class PickChuteActivity extends Activity implements OnApiCallComplete, OnItemClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = PickChuteActivity.class.getSimpleName();
    private BrowseChutesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.browse_chute_activity);
	populateAdapter();
	new UserChutesMe(this, this).execute();
    }

    @Override
    public void onSuccess() {
	populateAdapter();
    }

    private void populateAdapter() {
	Cursor query = getContentResolver().query(DB.ChutesColumns.CONTENT_URI, null, null, null,
		null);
	if (adapter == null) {
	    adapter = new BrowseChutesAdapter(this, query);
	    ListView listView = (ListView) findViewById(R.id.listViewChutes);
	    listView.setOnItemClickListener(this);
	    listView.setAdapter(adapter);
	} else {
	    adapter.changeCursor(query);
	}
    }

    @Override
    public void onFail() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	Intent intent = new Intent();
	ChuteWrapper chuteWrapper = adapter.getItem(position);
	intent.putExtra(AssetsIntentBundleWrapper.CHUTE_ID, chuteWrapper.getChuteId());
	intent.putExtra(AssetsIntentBundleWrapper.CHUTE_NAME, chuteWrapper.getChuteName());
	setResult(Activity.RESULT_OK, intent);
	finish();
    }
}
