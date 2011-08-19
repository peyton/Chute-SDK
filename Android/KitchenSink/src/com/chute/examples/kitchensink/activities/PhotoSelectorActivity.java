package com.chute.examples.kitchensink.activities;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.PhotoDataModel;
import com.chute.examples.kitchensink.utils.views.DevicePhotosGrid;

public class PhotoSelectorActivity extends Activity {

    @SuppressWarnings("unused")
    private static final String TAG = PhotoSelectorActivity.class.getSimpleName();
    private DevicePhotosGrid devicePhotosGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.photo_selector_activity);
	LinearLayout root = (LinearLayout) findViewById(R.id.linearLayoutRoot);

	devicePhotosGrid = new DevicePhotosGrid(getApplicationContext(), getContentResolver()
		.query(DB.ImagesColumns.CONTENT_URI, null, null, null, null), getIntent()
		.getExtras().getBoolean(DevicePhotosGrid.EXTRA_IS_SINGLE_SELECT));
	root.addView(devicePhotosGrid);
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
}
