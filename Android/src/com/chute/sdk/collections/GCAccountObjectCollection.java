package com.chute.sdk.collections;

import java.util.ArrayList;

import com.chute.sdk.model.GCAccountObjectModel;

public class GCAccountObjectCollection extends GCCollection<GCAccountObjectModel> {

    /**
     * 
     */
    private static final long serialVersionUID = -488629180054434348L;
    @SuppressWarnings("unused")
    private static final String TAG = GCAccountObjectCollection.class.getSimpleName();

    public GCAccountObjectCollection() {
	super(new ArrayList<GCAccountObjectModel>());
    }

}
