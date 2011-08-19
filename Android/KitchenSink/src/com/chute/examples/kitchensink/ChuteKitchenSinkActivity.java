package com.chute.examples.kitchensink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.chute.examples.kitchensink.activities.AuthenticationActivity;
import com.chute.examples.kitchensink.activities.BrowseChuteActivity;
import com.chute.examples.kitchensink.activities.CreateBundleActivity;
import com.chute.examples.kitchensink.activities.CreateChuteActivity;
import com.chute.examples.kitchensink.activities.CreateParcelActivity;
import com.chute.examples.kitchensink.activities.PhotoSelectorActivity;
import com.chute.examples.kitchensink.activities.UploadImageActivity;
import com.chute.examples.kitchensink.activities.ViewBundleActivity;
import com.chute.examples.kitchensink.adapters.MenuExpandableAdapter;
import com.chute.examples.kitchensink.services.ListAndStoreSampleImagesService;
import com.chute.examples.kitchensink.utils.downloader.LargeImageDownloader;
import com.chute.examples.kitchensink.utils.downloader.SmallImageDownloader;
import com.chute.examples.kitchensink.utils.views.DevicePhotosGrid;

public class ChuteKitchenSinkActivity extends Activity implements OnChildClickListener,
	IChuteKitchenSinkActivity {

    private static final int GROUP_CHUTES = 0;
    private static final int GROUP_IMAGEUPLOAD = 1;
    private static final int GROUP_BUNDLES = 2;
    private static final int GROUP_AUTHENTICATION = 3;

    private MenuExpandableAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_menu_activity);
	init();
    }

    @Override
    public void init() {
	ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewMainMenu);
	adapter = new MenuExpandableAdapter(this);
	expandableListView.setAdapter(adapter);
	expandableListView.setOnChildClickListener(this);
	startService(new Intent(getApplicationContext(), ListAndStoreSampleImagesService.class));
	LargeImageDownloader.getInstance(getApplicationContext()).setPlaceholderBitmap(
		getApplicationContext(), R.drawable.placeholder_image_thumb_large);
	SmallImageDownloader.getInstance(getApplicationContext()).setPlaceholderBitmap(
		getApplicationContext(), R.drawable.placeholder_image_thumb_small);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition,
	    int childPosition, long id) {
	switch (groupPosition) {
	case GROUP_CHUTES:
	    onChuteGroupChildClicked(childPosition);
	    break;
	case GROUP_IMAGEUPLOAD:
	    onImageUploadGroupChildClicked(childPosition);
	    break;
	case GROUP_BUNDLES:
	    onBundleGroupChildClicked(childPosition);
	    break;
	case GROUP_AUTHENTICATION:
	    onAuthenticationChildClicked(childPosition);
	    break;
	}
	return false;
    }

    @Override
    public void onChuteGroupChildClicked(int childPosition) {
	Intent intent;
	switch (childPosition) {
	case 0:
	    intent = new Intent(getApplicationContext(), CreateChuteActivity.class);
	    startActivity(intent);
	    break;
	case 1:
	    intent = new Intent(getApplicationContext(), BrowseChuteActivity.class);
	    startActivity(intent);
	    break;
	}
    }

    @Override
    public void onImageUploadGroupChildClicked(int childPosition) {
	Intent intent;
	switch (childPosition) {
	case 0:
	    intent = new Intent(getApplicationContext(), UploadImageActivity.class); // Single
	    intent.putExtra(DevicePhotosGrid.EXTRA_IS_SINGLE_SELECT, true);
	    startActivity(intent);
	    break;
	case 1:
	    intent = new Intent(getApplicationContext(), UploadImageActivity.class); // Single
	    intent.putExtra(DevicePhotosGrid.EXTRA_IS_SINGLE_SELECT, false);
	    startActivity(intent);
	    break;
	case 2:
	    intent = new Intent(getApplicationContext(), PhotoSelectorActivity.class); // Single
	    intent.putExtra(DevicePhotosGrid.EXTRA_IS_SINGLE_SELECT, true);
	    startActivity(intent);
	    break;
	case 3:
	    intent = new Intent(getApplicationContext(), PhotoSelectorActivity.class); // Multiple
	    intent.putExtra(DevicePhotosGrid.EXTRA_IS_SINGLE_SELECT, false);
	    startActivity(intent);
	    break;
	case 4:
	    intent = new Intent(getApplicationContext(), CreateParcelActivity.class); // Multiple
	    startActivity(intent);
	    break;
	}
    }

    @Override
    public void onBundleGroupChildClicked(int childPosition) {
	Intent intent;
	switch (childPosition) {
	case 0:
	    intent = new Intent(getApplicationContext(), CreateBundleActivity.class);
	    startActivity(intent);
	    break;
	case 1:
	    intent = new Intent(getApplicationContext(), ViewBundleActivity.class);
	    startActivity(intent);
	    break;
	}
    }

    @Override
    public void onAuthenticationChildClicked(int childPosition) {
	Intent intent;
	switch (childPosition) {
	case 0:
	    intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
	    startActivity(intent);
	    break;
	}
    }

}