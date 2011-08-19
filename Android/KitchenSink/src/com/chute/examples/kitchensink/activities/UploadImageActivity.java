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

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.PhotoDataModel;
import com.chute.examples.kitchensink.services.UploadImagesService;
import com.chute.examples.kitchensink.utils.views.DevicePhotosGrid;

public class UploadImageActivity extends Activity implements OnClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = UploadImageActivity.class.getSimpleName();
    private DevicePhotosGrid devicePhotosGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.upload_single_image_activity);
	LinearLayout root = (LinearLayout) findViewById(R.id.linearLayoutRoot);
	Button upload = (Button) findViewById(R.id.buttonUpload);
	upload.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.buttonUpload:
	    ArrayList<PhotoDataModel> selectedPhotoList = getSelectedPhotoList();
	    Intent intent = new Intent(getApplicationContext(), UploadImagesService.class);
	    intent.putParcelableArrayListExtra(UploadImagesService.EXTRA_IMAGES_LIST,
		    selectedPhotoList);
	    startService(intent);
	    Log.e(TAG, "Upload Service starting");
	    break;
	}
    }
}
