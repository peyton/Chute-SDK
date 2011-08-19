package com.chute.examples.kitchensink.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.utils.ChuteAppUtils;
import com.chute.examples.kitchensink.utils.downloader.SmallImageDownloader;

/**
 * @author Darko
 * 
 */
public class BrowseChutesAdapter extends CursorAdapter {

    private static LayoutInflater inflater = null;
    private final Activity activity;
    private final SmallImageDownloader imageDownloader;

    public BrowseChutesAdapter(Activity activity, Cursor c) {
	super(activity, c);
	inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.activity = activity;
	imageDownloader = SmallImageDownloader.getInstance(activity);
    }

    public static class ViewHolder {
	public ImageView image;
	public TextView title;
	public TextView itemCount;
	public TextView photoCount;
    }

    public class ChuteWrapper {
	private final String chuteId;
	private final String parcelId;
	private final String chuteName;

	public ChuteWrapper(String chuteId, String parcelId, String chuteName) {
	    this.chuteId = chuteId;
	    this.parcelId = parcelId;
	    this.chuteName = chuteName;
	}

	public String getChuteId() {
	    return chuteId;
	}

	public String getParcelId() {
	    return parcelId;
	}

	public String getChuteName() {
	    return chuteName;
	}
    }

    @Override
    public ChuteWrapper getItem(int position) {
	Cursor cursor = getCursor();
	cursor.moveToPosition(position);
	return new ChuteWrapper(cursor.getString(cursor.getColumnIndex(DB.ChutesColumns._ID)),
		null, cursor.getString(cursor.getColumnIndex(DB.ChutesColumns.NAME)));
    }

    @Override
    public void bindView(View vi, Context context, Cursor cursor) {
	ViewHolder holder;
	holder = (ViewHolder) vi.getTag();

	holder.title.setText(cursor.getString(cursor.getColumnIndex(DB.ChutesColumns.NAME)));
	// Description code
	holder.photoCount.setText(cursor.getString(cursor
		.getColumnIndex(DB.ChutesColumns.ASSET_COUNT)));

	// Image Thumb code
	try {
	    String thumb = cursor.getString(cursor.getColumnIndex(DB.ChutesColumns.RECENT_THUMB));
	    imageDownloader.download(thumb, holder.image);
	} catch (NullPointerException e) {
	}

	holder.itemCount.setText(cursor.getString(cursor
		.getColumnIndex(DB.ChutesColumns.MEMBERS_COUNT)));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	View vi = inflater.inflate(R.layout.browse_chutes_list_adapter, null);
	ViewHolder holder;
	holder = new ViewHolder();
	holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
	holder.title = (TextView) vi.findViewById(R.id.TextViewItemTitle);
	holder.title.setTypeface(ChuteAppUtils.getTypefaceHelvaticaMedium(activity));
	holder.itemCount = (TextView) vi.findViewById(R.id.TextViewItemCount);
	holder.photoCount = (TextView) vi.findViewById(R.id.TextViewPhotoCount);
	vi.setTag(holder);
	return vi;
    }

}