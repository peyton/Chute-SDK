package com.chute.examples.kitchensink.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.models.SingleChuteAsset;
import com.chute.examples.kitchensink.utils.downloader.SmallImageDownloader;

public class BrowseAssetsGridAdapter extends BaseAdapter {

    private static final String TAG = BrowseAssetsGridAdapter.class.getSimpleName();
    private static LayoutInflater inflater = null;
    private final Context context;
    private final List<SingleChuteAsset> list;
    public SmallImageDownloader imageDownloader;

    public BrowseAssetsGridAdapter(Context context, final List<SingleChuteAsset> list) {
	this.list = list;
	inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.context = context;
	imageDownloader = SmallImageDownloader.getInstance(context);
    }

    @Override
    public int getCount() {
	return list.size();
    }

    @Override
    public SingleChuteAsset getItem(int position) {
	return list.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    public static class ViewHolder {
	public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

	View vi = convertView;
	ViewHolder holder;
	if (convertView == null) {
	    vi = inflater.inflate(R.layout.browse_assets_grid_adapter, null);
	    holder = new ViewHolder();
	    holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
	    vi.setTag(holder);
	} else {
	    holder = (ViewHolder) vi.getTag();
	}
	imageDownloader.download(getItem(position).getUrl(), holder.image);

	return vi;
    }
}