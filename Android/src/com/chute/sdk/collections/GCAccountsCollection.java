package com.chute.sdk.collections;

import java.util.ArrayList;

import com.chute.sdk.model.GCAccountModel;

public class GCAccountsCollection extends GCCollection<GCAccountModel> {

    /**
     * 
     */
    private static final long serialVersionUID = -8036409289761294508L;
    @SuppressWarnings("unused")
    private static final String TAG = GCAccountsCollection.class.getSimpleName();

    public GCAccountsCollection() {
	super(new ArrayList<GCAccountModel>());
    }

}
