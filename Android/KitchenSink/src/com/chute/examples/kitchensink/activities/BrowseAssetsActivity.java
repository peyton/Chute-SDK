package com.chute.examples.kitchensink.activities;

import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.adapters.BrowseAssetsGalleryAdapter;
import com.chute.examples.kitchensink.models.SingleChuteAsset;
import com.chute.examples.kitchensink.utils.views.AssetsGrid;
import com.chute.examples.kitchensink.utils.views.AssetsGrid.OnReadyListener;
import com.chute.examples.kitchensink.utils.views.GalleryViewFlipper;
import com.chute.examples.kitchensink.utils.views.viewflow.ViewFlow;
import com.chute.sdk.api.assets.AssetsIntentBundleWrapper;
import com.chute.sdk.api.assets.AssetsLoadFromApiTask;
import com.chute.sdk.api.assets.OnLoadAssetsComplete;

public class BrowseAssetsActivity extends Activity implements OnLoadAssetsComplete,
	OnClickListener, OnReadyListener {
    @SuppressWarnings("unused")
    private static final String TAG = BrowseAssetsActivity.class.getSimpleName();
    private ViewFlow viewFlow;
    private ProgressBar progressBarAssets;
    private List<SingleChuteAsset> assets;
    private AssetsGrid assetsGrid;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.browse_assets_activity);
	progressBarAssets = (ProgressBar) findViewById(R.id.progressBarAssets);
	viewFlow = (ViewFlow) findViewById(R.id.viewFlow);
	AssetsIntentBundleWrapper bundleWrapper = new AssetsIntentBundleWrapper(getIntent()
		.getExtras());
	ImageButton buttonGrid = (ImageButton) findViewById(R.id.buttonGrid);
	buttonGrid.setOnClickListener(this);
	TextView chuteName = (TextView) findViewById(R.id.textFullscreenChuteName);
	chuteName.setText(bundleWrapper.getChuteName());
	new AssetsLoadFromApiTask(this, progressBarAssets, bundleWrapper.getChuteId(), this)
		.execute();
    }

    /* If your min SDK version is < 8 you need to trigger the onConfigurationChanged in ViewFlow manually, like this */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	viewFlow.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSuccess(final List<SingleChuteAsset> assets) {
	this.assets = assets;
	BrowseAssetsGalleryAdapter adapter = new BrowseAssetsGalleryAdapter(this, this.assets);
	viewFlow.setAdapter(adapter, 3);
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.buttonGrid:
	    swapGridWithGallery();
	    break;
	default:
	    break;
	}
    }

    public void swapGridWithGallery() {
	GalleryViewFlipper viewFlipper = (GalleryViewFlipper) findViewById(R.id.viewFlipper);
	initGrid();
	viewFlipper.showNext();
    }

    public void initGrid() {
	if (assetsGrid == null) {
	    assetsGrid = new AssetsGrid(this, this.assets, this);
	    GalleryViewFlipper viewFlipper = (GalleryViewFlipper) findViewById(R.id.viewFlipper);
	    viewFlipper.addView(assetsGrid);
	}
    }

    @Override
    public void ready(int result, SingleChuteAsset asset) {
	swapGridWithGallery();
	viewFlow.setSelection(result);
    }

}
