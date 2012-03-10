package com.chute.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chute.sdk.model.GCUserModel;

/**
 * The {@link GCAccountModel} class represents the concept of an account. Each
 * account contains type, name, user ID, status and which user it belongs to.
 * 
 */
public class GCAccountModel implements Parcelable {

	@SuppressWarnings("unused")
	private static final String TAG = GCAccountModel.class.getSimpleName();

	/**
	 * The unique identifier of the account.
	 */
	private String id;
	/**
	 * Type of the account. It can be: Facebook, Twitter, Flickr, Instagram,
	 * Picasa etc.
	 */
	private String type;
	/**
	 * Account name.
	 */
	private String name;
	/**
	 * Account user unique identifier.
	 */
	private String uid;
	/**
	 * Status of the account.
	 */
	private int status;
	/**
	 * Flag indicating if account notifications are enabled or no.
	 */
	private boolean notificationsEnabled;
	/**
	 * The {@link GCUserModel} which the account belongs to.
	 */
	public GCUserModel user = new GCUserModel();

	/**
	 * Default non-args constructor.
	 */
	public GCAccountModel() {
		super();
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param id
	 * @param type
	 * @param name
	 * @param uid
	 * @param status
	 * @param notificationsEnabled
	 * @param user
	 */
	public GCAccountModel(String id, String type, String name, String uid,
			int status, boolean notificationsEnabled, GCUserModel user) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.uid = uid;
		this.status = status;
		this.notificationsEnabled = notificationsEnabled;
		this.user = user;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isNotificationsEnabled() {
		return notificationsEnabled;
	}

	public void setNotificationsEnabled(boolean notificationsEnabled) {
		this.notificationsEnabled = notificationsEnabled;
	}

	public GCUserModel getUser() {
		return user;
	}

	public void setUser(GCUserModel user) {
		this.user = user;
	}

	public GCAccountModel(Parcel in) {
		id = in.readString();
		type = in.readString();
		name = in.readString();
		uid = in.readString();
		status = in.readInt();
		notificationsEnabled = in.readInt() == 1;
		user = in.readParcelable(GCUserModel.class.getClassLoader());
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
		dest.writeString(type);
		dest.writeString(name);
		dest.writeString(uid);
		dest.writeInt(status);
		dest.writeInt(notificationsEnabled ? 1 : 0);
		dest.writeParcelable(user, flags);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GCAccountModel [id=" + id + ", type=" + type + ", name=" + name
				+ ", uid=" + uid + ", status=" + status
				+ ", notificationsEnabled=" + notificationsEnabled + ", user="
				+ user + "]";
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
		result = prime * result + (notificationsEnabled ? 1231 : 1237);
		result = prime * result + status;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		GCAccountModel other = (GCAccountModel) obj;
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
		if (notificationsEnabled != other.notificationsEnabled)
			return false;
		if (status != other.status)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	public static final Parcelable.Creator<GCAccountModel> CREATOR = new Parcelable.Creator<GCAccountModel>() {

		@Override
		public GCAccountModel createFromParcel(Parcel in) {
			return new GCAccountModel(in);
		}

		@Override
		public GCAccountModel[] newArray(int size) {
			return new GCAccountModel[size];
		}
	};

}
