package com.chute.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The {@link GCAccountMediaModel} class represents the concept of a media item
 * for a specific object that belongs to an account. Each media item contains
 * URL, thumbnail URL and large image URL.
 * 
 */
public class GCAccountMediaModel implements Parcelable {

	@SuppressWarnings("unused")
	private static final String TAG = GCAccountMediaModel.class.getSimpleName();

	/**
	 * The unique identifier of the media item.
	 */
	private String id;
	/**
	 * The URL that shows the location of the thumbnail.
	 */
	private String thumbUrl;
	/**
	 * The URL that shows the location of the large image.
	 */
	private String largeUrl;
	/**
	 * The URl that shows the location of the media item.
	 */
	private String url;

	/**
	 * Default non-args constructor.
	 */
	public GCAccountMediaModel() {
		super();
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

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GCAccountMediaModel(Parcel in) {
		id = in.readString();
		thumbUrl = in.readString();
		largeUrl = in.readString();
		url = in.readString();
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
		dest.writeString(thumbUrl);
		dest.writeString(largeUrl);
		dest.writeString(url);
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
		result = prime * result
				+ ((largeUrl == null) ? 0 : largeUrl.hashCode());
		result = prime * result
				+ ((thumbUrl == null) ? 0 : thumbUrl.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GCAccountMediaModel other = (GCAccountMediaModel) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (largeUrl == null) {
			if (other.largeUrl != null) {
				return false;
			}
		} else if (!largeUrl.equals(other.largeUrl)) {
			return false;
		}
		if (thumbUrl == null) {
			if (other.thumbUrl != null) {
				return false;
			}
		} else if (!thumbUrl.equals(other.thumbUrl)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCAccountMediaModel [id=");
		builder.append(id);
		builder.append(", thumbUrl=");
		builder.append(thumbUrl);
		builder.append(", largeUrl=");
		builder.append(largeUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}

	public static final Parcelable.Creator<GCAccountMediaModel> CREATOR = new Parcelable.Creator<GCAccountMediaModel>() {

		@Override
		public GCAccountMediaModel createFromParcel(Parcel in) {
			return new GCAccountMediaModel(in);
		}

		@Override
		public GCAccountMediaModel[] newArray(int size) {
			return new GCAccountMediaModel[size];
		}

	};
}
