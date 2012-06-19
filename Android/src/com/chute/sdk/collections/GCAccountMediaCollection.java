package com.chute.sdk.collections;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.chute.sdk.model.GCAccountMediaModel;

public class GCAccountMediaCollection extends GCCollection<GCAccountMediaModel>
		implements Parcelable {

	/**
     * 
     */
	private static final long serialVersionUID = -7519081559627771803L;

	@SuppressWarnings("unused")
	private static final String TAG = GCAccountMediaCollection.class
			.getSimpleName();

	private String id;

	public GCAccountMediaCollection() {
		super(new ArrayList<GCAccountMediaModel>());
	}

	public GCAccountMediaCollection(Parcel in) {
		this();
		id = in.readString();
		this.clear();
		in.readTypedList(this, GCAccountMediaModel.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeTypedList(this);
	}

	public static final Parcelable.Creator<GCAccountMediaCollection> CREATOR = new Parcelable.Creator<GCAccountMediaCollection>() {

		@Override
		public GCAccountMediaCollection createFromParcel(Parcel in) {
			return new GCAccountMediaCollection(in);
		}

		@Override
		public GCAccountMediaCollection[] newArray(int size) {
			return new GCAccountMediaCollection[size];
		}
	};

}
