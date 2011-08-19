package com.chute.examples.kitchensink.utils.views;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.chute.examples.kitchensink.adapters.PhotoSelectCursorAdapter;
import com.chute.examples.kitchensink.models.PhotoDataModel;

public class DevicePhotosGrid extends GridView {
    public static final String EXTRA_IS_SINGLE_SELECT = "isSingle";
    private PhotoSelectCursorAdapter gridAdapter;

    private final Context context;

    private final Cursor c;

    private final boolean isSingle;

    public DevicePhotosGrid(Context context, final Cursor c, boolean isSingle) {
	super(context);
	this.context = context;
	this.c = c;
	this.isSingle = isSingle;
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
		if (isSingle) {
		    gridAdapter.tick.clear();
		}
		if (gridAdapter.tick.containsKey(position)) {
		    gridAdapter.tick.remove(position);
		} else {
		    gridAdapter.tick.put(position, gridAdapter.getItem(position));
		}
		gridAdapter.notifyDataSetChanged();
	    }
	});
	gridAdapter = new PhotoSelectCursorAdapter(context, c);
	this.setAdapter(gridAdapter);
	this.setFadingEdgeLength(0);
	this.setColumnWidth(GridView.STRETCH_COLUMN_WIDTH);
	this.setNumColumns(4);
	this.setVerticalSpacing(1);
	this.setHorizontalSpacing(1);
    }

    public HashMap<Integer, PhotoDataModel> getSelectedPhotos() {
	return gridAdapter.tick;
    }
}
