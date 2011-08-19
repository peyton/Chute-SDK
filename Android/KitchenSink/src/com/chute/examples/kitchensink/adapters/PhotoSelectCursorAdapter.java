package com.chute.examples.kitchensink.adapters;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.PhotoDataModel;
import com.chute.examples.kitchensink.utils.downloader.SmallImageDownloader;

public class PhotoSelectCursorAdapter extends CursorAdapter implements OnScrollListener {

    Context context;
    private static LayoutInflater inflater = null;
    public HashMap<Integer, PhotoDataModel> tick;
    public SmallImageDownloader downloader;
    private boolean shouldLoadImages = true;

    public PhotoSelectCursorAdapter(Context context, Cursor c) {
	super(context.getApplicationContext(), c, false);
	this.context = context;
	inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	tick = new HashMap<Integer, PhotoDataModel>();
	downloader = SmallImageDownloader.getInstance(context);
    }

    public static class ViewHolder {
	public ImageView image;
	public ImageView tick;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

	ViewHolder holder;

	View vi = inflater.inflate(R.layout.photos_select_adapter, null);
	holder = new ViewHolder();
	holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
	holder.tick = (ImageView) vi.findViewById(R.id.imageTick);
	vi.setTag(holder);
	return vi;
    }

    @Override
    public PhotoDataModel getItem(int position) {
	Cursor cursor = getCursor();
	cursor.moveToPosition(position);
	PhotoDataModel model = new PhotoDataModel(cursor.getString(cursor
		.getColumnIndexOrThrow(DB.ImagesColumns.ASSET_ID)), cursor.getString(cursor
		.getColumnIndexOrThrow(DB.ImagesColumns.FILEPATH)), cursor.getString(cursor
		.getColumnIndexOrThrow(DB.ImagesColumns.STATUS)));
	return model;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
	ViewHolder holder;
	holder = (ViewHolder) view.getTag();
	String path = cursor.getString(cursor.getColumnIndex(DB.ImagesColumns.FILEPATH));
	holder.image.setTag(path);
	holder.tick.setTag(cursor.getPosition());
	// imageLoader.DisplayImage(path, activity, holder.image);
	if (shouldLoadImages) {
	    downloader.download(path, holder.image);
	} else {
	    downloader.download(null, holder.image);
	}
	if (tick.containsKey(cursor.getPosition())) {
	    holder.tick.setVisibility(View.VISIBLE);
	} else {
	    holder.tick.setVisibility(View.GONE);
	}
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
	    int totalItemCount) {
	// Do nothing,
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
	switch (scrollState) {
	case OnScrollListener.SCROLL_STATE_FLING:
	case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
	    shouldLoadImages = false;
	    break;
	case OnScrollListener.SCROLL_STATE_IDLE:
	    shouldLoadImages = true;
	    notifyDataSetChanged();
	    break;
	}
    }

}
