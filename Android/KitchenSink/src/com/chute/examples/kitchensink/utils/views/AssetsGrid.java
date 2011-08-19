package com.chute.examples.kitchensink.utils.views;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.chute.examples.kitchensink.adapters.BrowseAssetsGridAdapter;
import com.chute.examples.kitchensink.models.SingleChuteAsset;

public class AssetsGrid extends GridView {

    public interface OnReadyListener {
	public void ready(int result, SingleChuteAsset asset);
    }

    private final List<SingleChuteAsset> list;

    private final OnReadyListener readyListener;
    private BrowseAssetsGridAdapter gridAdapter;

    private final Context context;

    public AssetsGrid(Context context, final List<SingleChuteAsset> list,
	    OnReadyListener readyListener) {
	super(context);
	this.list = list;
	this.readyListener = readyListener;
	this.context = context;
	init();
    }

    public void init() {
	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	this.setPadding(1, 10, 1, 5);
	this.setLayoutParams(params);

	this.setBackgroundColor(Color.TRANSPARENT);
	this.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		readyListener.ready(position, list.get(position));
	    }
	});
	gridAdapter = new BrowseAssetsGridAdapter(context, list);
	this.setAdapter(gridAdapter);
	this.setFadingEdgeLength(0);
	this.setColumnWidth(GridView.STRETCH_COLUMN_WIDTH);
	this.setNumColumns(4);
	this.setVerticalSpacing(1);
	this.setHorizontalSpacing(1);
    }
}
