package com.chute.sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The {@link GCMemberModel} class represents a concept of a chute member. Each
 * member contains asset counter, pending assets, member email, member login,
 * comments notifications, invites notifications, photos notifications, the
 * available storage, the current storage.
 * 
 */
public class GCMemberModel extends GCUserModel {
	@SuppressWarnings("unused")
	private static final String TAG = GCMemberModel.class.getSimpleName();

	/**
	 * Asset counter.
	 */
	private String assetCount;
	/**
	 * Assets that are pending.
	 */
	private String assetsPending;
	/**
	 * Photos of assets.
	 */
	private String assetsPhotos;
	/**
	 * Email of the member.
	 */
	private String email;
	/**
	 * Member login.
	 */
	private String login;
	/**
	 * Notifications for comments.
	 */
	private String notificationComments;
	/**
	 * Notifications for invites.
	 */
	private String notificationInvites;
	/**
	 * Notifications for photos.
	 */
	private String notificationPhotos;
	/**
	 * The available storage.
	 */
	private String storageAvailable;
	/**
	 * The current storage.
	 */
	private String storageCurrent;

	/**
	 * Default non-args constructor.
	 */
	public GCMemberModel() {
		super();
	}

	public GCMemberModel(String id, String name, String avatarURL) {
		super(id, name, avatarURL);
	}

	/**
	 * Getter and setter methods.
	 */
	public String getAssetCount() {
		return assetCount;
	}

	public void setAssetCount(String assetCount) {
		this.assetCount = assetCount;
	}

	public String getAssetsPending() {
		return assetsPending;
	}

	public void setAssetsPending(String assetsPending) {
		this.assetsPending = assetsPending;
	}

	public String getAssetsPhotos() {
		return assetsPhotos;
	}

	public void setAssetsPhotos(String assetsPhotos) {
		this.assetsPhotos = assetsPhotos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNotificationComments() {
		return notificationComments;
	}

	public void setNotificationComments(String notificationComments) {
		this.notificationComments = notificationComments;
	}

	public String getNotificationInvites() {
		return notificationInvites;
	}

	public void setNotificationInvites(String notificationInvites) {
		this.notificationInvites = notificationInvites;
	}

	public String getNotificationPhotos() {
		return notificationPhotos;
	}

	public void setNotificationPhotos(String notificationPhotos) {
		this.notificationPhotos = notificationPhotos;
	}

	public String getStorageAvailable() {
		return storageAvailable;
	}

	public void setStorageAvailable(String storageAvailable) {
		this.storageAvailable = storageAvailable;
	}

	public String getStorageCurrent() {
		return storageCurrent;
	}

	public void setStorageCurrent(String storageCurrent) {
		this.storageCurrent = storageCurrent;
	}

	/**
	 * Method used for setting the {@link GCUserModel}.
	 * 
	 * @param userModel
	 */
	public void setUser(final GCUserModel userModel) {
		this.setId(userModel.getId());
		this.setAvatarURL(userModel.getAvatarURL());
		this.setName(userModel.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.model.GCUserModel#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCMemberModel [assetCount=");
		builder.append(assetCount);
		builder.append(", assetsPending=");
		builder.append(assetsPending);
		builder.append(", assetsPhotos=");
		builder.append(assetsPhotos);
		builder.append(", email=");
		builder.append(email);
		builder.append(", login=");
		builder.append(login);
		builder.append(", notificationComments=");
		builder.append(notificationComments);
		builder.append(", notificationInvites=");
		builder.append(notificationInvites);
		builder.append(", notificationPhotos=");
		builder.append(notificationPhotos);
		builder.append(", storageAvailable=");
		builder.append(storageAvailable);
		builder.append(", storageCurrent=");
		builder.append(storageCurrent);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param assetCount
	 * @param assetsPending
	 * @param assetsPhotos
	 * @param email
	 * @param login
	 * @param notificationComments
	 * @param notificationInvites
	 * @param notificationPhotos
	 * @param storageAvailable
	 * @param storageCurrent
	 */
	public GCMemberModel(String assetCount, String assetsPending,
			String assetsPhotos, String email, String login,
			String notificationComments, String notificationInvites,
			String notificationPhotos, String storageAvailable,
			String storageCurrent) {
		super();
		this.assetCount = assetCount;
		this.assetsPending = assetsPending;
		this.assetsPhotos = assetsPhotos;
		this.email = email;
		this.login = login;
		this.notificationComments = notificationComments;
		this.notificationInvites = notificationInvites;
		this.notificationPhotos = notificationPhotos;
		this.storageAvailable = storageAvailable;
		this.storageCurrent = storageCurrent;
	}

	public GCMemberModel(Parcel in) {
		assetCount = in.readString();
		assetsPending = in.readString();
		assetsPhotos = in.readString();
		email = in.readString();
		login = in.readString();
		notificationComments = in.readString();
		notificationInvites = in.readString();
		notificationPhotos = in.readString();
		storageAvailable = in.readString();
		storageCurrent = in.readString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.model.GCUserModel#writeToParcel(android.os.Parcel,
	 * int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(assetCount);
		dest.writeString(assetsPending);
		dest.writeString(assetsPhotos);
		dest.writeString(email);
		dest.writeString(login);
		dest.writeString(notificationComments);
		dest.writeString(notificationInvites);
		dest.writeString(notificationPhotos);
		dest.writeString(storageAvailable);
		dest.writeString(storageCurrent);

	}

	public static final Parcelable.Creator<GCMemberModel> CREATOR = new Parcelable.Creator<GCMemberModel>() {

		@Override
		public GCMemberModel createFromParcel(Parcel in) {
			return new GCMemberModel(in);
		}

		@Override
		public GCMemberModel[] newArray(int size) {
			return new GCMemberModel[size];
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.model.GCUserModel#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((assetCount == null) ? 0 : assetCount.hashCode());
		result = prime * result
				+ ((assetsPending == null) ? 0 : assetsPending.hashCode());
		result = prime * result
				+ ((assetsPhotos == null) ? 0 : assetsPhotos.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime
				* result
				+ ((notificationComments == null) ? 0 : notificationComments
						.hashCode());
		result = prime
				* result
				+ ((notificationInvites == null) ? 0 : notificationInvites
						.hashCode());
		result = prime
				* result
				+ ((notificationPhotos == null) ? 0 : notificationPhotos
						.hashCode());
		result = prime
				* result
				+ ((storageAvailable == null) ? 0 : storageAvailable.hashCode());
		result = prime * result
				+ ((storageCurrent == null) ? 0 : storageCurrent.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chute.sdk.model.GCUserModel#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GCMemberModel other = (GCMemberModel) obj;
		if (assetCount == null) {
			if (other.assetCount != null) {
				return false;
			}
		} else if (!assetCount.equals(other.assetCount)) {
			return false;
		}
		if (assetsPending == null) {
			if (other.assetsPending != null) {
				return false;
			}
		} else if (!assetsPending.equals(other.assetsPending)) {
			return false;
		}
		if (assetsPhotos == null) {
			if (other.assetsPhotos != null) {
				return false;
			}
		} else if (!assetsPhotos.equals(other.assetsPhotos)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (notificationComments == null) {
			if (other.notificationComments != null) {
				return false;
			}
		} else if (!notificationComments.equals(other.notificationComments)) {
			return false;
		}
		if (notificationInvites == null) {
			if (other.notificationInvites != null) {
				return false;
			}
		} else if (!notificationInvites.equals(other.notificationInvites)) {
			return false;
		}
		if (notificationPhotos == null) {
			if (other.notificationPhotos != null) {
				return false;
			}
		} else if (!notificationPhotos.equals(other.notificationPhotos)) {
			return false;
		}
		if (storageAvailable == null) {
			if (other.storageAvailable != null) {
				return false;
			}
		} else if (!storageAvailable.equals(other.storageAvailable)) {
			return false;
		}
		if (storageCurrent == null) {
			if (other.storageCurrent != null) {
				return false;
			}
		} else if (!storageCurrent.equals(other.storageCurrent)) {
			return false;
		}
		return true;
	}

}
