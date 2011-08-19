package com.chute.examples.kitchensink.activities;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.PhotoDataModel;
import com.chute.examples.kitchensink.utils.views.DevicePhotosGrid;
import com.chute.sdk.api.OnApiCallComplete;
import com.chute.sdk.api.assets.AssetsIntentBundleWrapper;
import com.chute.sdk.api.upload.CreateParcel;

public class CreateParcelActivity extends Activity implements OnClickListener, OnApiCallComplete {
    private static final int REQUEST_CODE_PICK_CHUTE = 1;
    @SuppressWarnings("unused")
    private static final String TAG = CreateParcelActivity.class.getSimpleName();
    private DevicePhotosGrid devicePhotosGrid;
    private AssetsIntentBundleWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.create_parcel_activity);
	LinearLayout root = (LinearLayout) findViewById(R.id.linearLayoutRoot);

	devicePhotosGrid = new DevicePhotosGrid(getApplicationContext(), getContentResolver()
		.query(DB.ImagesColumns.CONTENT_URI, null, null, null, null), false);
	root.addView(devicePhotosGrid);
	Button buttonCreate = (Button) findViewById(R.id.buttonCreateParcel);
	buttonCreate.setOnClickListener(this);
	Button buttonPickChute = (Button) findViewById(R.id.buttonPickChute);
	buttonPickChute.setOnClickListener(this);
    }

    public ArrayList<PhotoDataModel> getSelectedPhotoList() {
	ArrayList<PhotoDataModel> list = new ArrayList<PhotoDataModel>();
	Iterator<PhotoDataModel> iterator = devicePhotosGrid.getSelectedPhotos().values()
		.iterator();
	while (iterator.hasNext()) {
	    list.add(iterator.next());
	}
	return list;
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.buttonCreateParcel:
	    createParcel();
	    break;
	case R.id.buttonPickChute:
	    if (wrapper == null) {
		Intent intent = new Intent(getApplicationContext(), PickChuteActivity.class);
		startActivityForResult(intent, REQUEST_CODE_PICK_CHUTE);
		Toast.makeText(getApplicationContext(), "Pick a chute for the parcel",
			Toast.LENGTH_LONG).show();
	    }
	    break;
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode == Activity.RESULT_OK) {
	    switch (requestCode) {
	    case REQUEST_CODE_PICK_CHUTE:
		wrapper = new AssetsIntentBundleWrapper(data.getExtras());
		Log.w(TAG, wrapper.getChuteId() + " CHUTE NAME " + wrapper.getChuteName());
		break;
	    default:
		break;
	    }
	}
    }

    public void createParcel() {
	if (wrapper == null) {
	    Toast.makeText(getApplicationContext(), "Pick a chute for the parcel",
		    Toast.LENGTH_LONG).show();
	    return;
	}
	if (getSelectedPhotoList().size() == 0) {
	    Toast.makeText(getApplicationContext(), "Pick a Photos", Toast.LENGTH_LONG).show();
	    return;
	}
	new CreateParcel(CreateParcelActivity.this, getSelectedPhotoList(),
		CreateParcelActivity.this, wrapper.getChuteId()).execute();
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onFail() {
    }
}
