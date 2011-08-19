package com.chute.examples.kitchensink.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * A simple Expandable Adapter containing all the menu options grouped into
 * categories.
 */
public class MenuExpandableAdapter extends BaseExpandableListAdapter {
    @SuppressWarnings("unused")
    private static final String TAG = MenuExpandableAdapter.class.getSimpleName();

    // Sample data set. children[i] contains the children (String[]) for
    // groups[i].
    // @formatter:off
    private final String[] groups = {
	    "Chutes",
	    "Photos",
	    "Bundles",
	    "Authentication"
	    };
    private final String[][] children = {
	    { "Create Chute", "Browse Chute" },
	    { "Upload Photo", "Upload Photos", "Photo Selector (Single)", "Photo Selector (Multiple)", "Create Parcel" },
	    { "Create Bundle", "View Bundle" },
	    { "Authentication"}
	    };
    //@formatter:on
    private final Context context;

    @Override
    public Object getChild(int groupPosition, int childPosition) {
	return children[groupPosition][childPosition];
    }

    public MenuExpandableAdapter(Context context) {
	this.context = context;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
	return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
	return children[groupPosition].length;
    }

    public TextView getGenericView() {
	// Layout parameters for the ExpandableListView
	AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
		ViewGroup.LayoutParams.FILL_PARENT, 64);

	TextView textView = new TextView(context);
	textView.setLayoutParams(lp);
	// Center the text vertically
	textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
	// Set the text starting position
	textView.setPadding(36, 0, 0, 0);
	return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
	    View convertView, ViewGroup parent) {
	TextView textView = getGenericView();
	textView.setText(getChild(groupPosition, childPosition).toString());
	return textView;
    }

    @Override
    public Object getGroup(int groupPosition) {
	return groups[groupPosition];
    }

    @Override
    public int getGroupCount() {
	return groups.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
	return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	    ViewGroup parent) {
	TextView textView = getGenericView();
	textView.setText(getGroup(groupPosition).toString());
	return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
	return true;
    }

    @Override
    public boolean hasStableIds() {
	return true;
    }

}