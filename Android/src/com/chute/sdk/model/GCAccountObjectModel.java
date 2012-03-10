package com.chute.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {@link GCAccountObjectModel} class represents the concept of an object for a
 * specific account. Each object contains ID and name.
 * 
 */
public class GCAccountObjectModel implements Parcelable {

	@SuppressWarnings("unused")
	private static final String TAG = GCAccountObjectModel.class
			.getSimpleName();

	/**
	 * The unique identifier of the object.
	 */
	private String id;
	/**
	 * Name of the object.
	 */
	private String name;

	/**
	 * Default non-args constructor.
	 */
	public GCAccountObjectModel() {
		super();
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param id
	 * @param name
	 */
	public GCAccountObjectModel(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * Getter and setter methods.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GCAccountObjectModel(Parcel in) {
		id = in.readString();
		name = in.readString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
	}

	public static final Parcelable.Creator<GCAccountObjectModel> CREATOR = new Parcelable.Creator<GCAccountObjectModel>() {

		@Override
		public GCAccountObjectModel createFromParcel(Parcel in) {
			return new GCAccountObjectModel(in);
		}

		@Override
		public GCAccountObjectModel[] newArray(int size) {
			return new GCAccountObjectModel[size];
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GCAccountObjectModel [id=" + id + ", name=" + name + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GCAccountObjectModel other = (GCAccountObjectModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
