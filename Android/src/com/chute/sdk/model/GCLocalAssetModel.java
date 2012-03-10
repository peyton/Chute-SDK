package com.chute.sdk.model;

import java.io.File;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.asset.GCAssets;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.parsers.GCLocalAssetListObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.MD5;

/**
 * The {@link GCLocalAssetModel} class represents a concept of a local asset.
 * Each local asset contains status, file, priority.
 * 
 */
public class GCLocalAssetModel implements Parcelable {
	@SuppressWarnings("unused")
	private static final String TAG = GCLocalAssetModel.class.getSimpleName();

	/**
	 * Enumeration for the status of the local asset. It can be one of the
	 * following types: UNVERIFIED, NEW, INITIALIZED, COMPLETE or SKIP.
	 */
	public enum AssetStatus {
		UNVERIFIED("unverified"), NEW("new"), INITIALIZED("initialized"), COMPLETE(
				"complete"), SKIP("skip");

		/**
		 * Method used for getting the status of the local asset.
		 * 
		 * @param name
		 */
		private AssetStatus(String name) {
			this.name = name;
		}

		/**
		 * Name of the status.
		 */
		private final String name;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return name;
		}

	}

	/**
	 * The unique identifier of the local asset.
	 */
	private String assetId;
	/**
	 * The File type of the local asset.
	 */
	private File file;
	/**
	 * The priority of the local asset.
	 */
	private int priority;
	/**
	 * Status of the local asset.
	 */
	private AssetStatus assetStatus;
	/**
	 * The String that needs to be converted into MD5 hash.
	 */
	private String fileMD5;

	/**
	 * Constructor
	 */
	public GCLocalAssetModel() {
		super();
		this.assetStatus = AssetStatus.UNVERIFIED;
		this.priority = 1;
	}

	/**
	 * Getter and setter methods.
	 */
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File localImageFile) {
		this.file = localImageFile;
	}

	public void setFile(String path) {
		this.file = new File(path);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public AssetStatus getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(AssetStatus assetStatus) {
		this.assetStatus = assetStatus;
	}

	public String getFileMD5() {
		return fileMD5;
	}

	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}

	/**
	 * Method used for getting MD5 checksum
	 * 
	 * @return
	 */
	public String calculateFileMD5() {
		try {
			this.fileMD5 = MD5.getMD5Checksum(this.file.getPath());
			return this.fileMD5;
		} catch (Exception e) {
			Log.w(TAG, "", e);
		}
		return "";
	}

	/**
	 * Method used for verifying assets. It returns a JSON object containing
	 * array of assets using the following parameters: context,
	 * {@link GCLocalAssetModel} and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCLocalAssetCollection}.
	 * @return {@link GCAssets#verify(Context, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback, GCLocalAssetModel...)}
	 *         method.
	 */
	public GCHttpRequest verify(Context context,
			GCHttpCallback<GCLocalAssetCollection> callback) {
		return GCAssets.verify(context, new GCLocalAssetListObjectParser(),
				callback, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCLocalAssetModel [assetId=");
		builder.append(assetId);
		builder.append(", localImageFile=");
		builder.append(file.getPath());
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", assetStatus=");
		builder.append(assetStatus);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Method used for resolving the asset status.
	 * 
	 * @param assetStatus
	 *            String variable which represents the asset status.
	 * @return {@link AssetStatus#name}.
	 */
	public static AssetStatus resolveAssetStatus(String assetStatus) {
		if (assetStatus.contentEquals(AssetStatus.NEW.toString())) {
			return AssetStatus.NEW;
		} else if (assetStatus
				.contentEquals(AssetStatus.INITIALIZED.toString())) {
			return AssetStatus.INITIALIZED;
		} else if (assetStatus.contentEquals(AssetStatus.COMPLETE.toString())) {
			return AssetStatus.COMPLETE;
		} else if (assetStatus.contentEquals(AssetStatus.SKIP.toString())) {
			return AssetStatus.SKIP;
		}
		return AssetStatus.UNVERIFIED;
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param assetId
	 * @param file
	 * @param priority
	 * @param assetStatus
	 * @param fileMD5
	 */
	public GCLocalAssetModel(String assetId, File file, int priority,
			AssetStatus assetStatus, String fileMD5) {
		super();
		this.assetId = assetId;
		this.file = file;
		this.priority = priority;
		this.assetStatus = assetStatus;
		this.fileMD5 = fileMD5;
	}

	public GCLocalAssetModel(Parcel in) {
		assetId = in.readString();
		file = (File) in.readSerializable();
		priority = in.readInt();
		assetStatus = AssetStatus.values()[in.readInt()];
		fileMD5 = in.readString();
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
		dest.writeString(assetId);
		dest.writeSerializable(file);
		dest.writeInt(priority);
		dest.writeInt(assetStatus.ordinal());
		dest.writeString(fileMD5);

	}

	public static final Parcelable.Creator<GCLocalAssetModel> CREATOR = new Parcelable.Creator<GCLocalAssetModel>() {

		@Override
		public GCLocalAssetModel createFromParcel(Parcel in) {
			return new GCLocalAssetModel(in);
		}

		@Override
		public GCLocalAssetModel[] newArray(int size) {
			return new GCLocalAssetModel[size];
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assetId == null) ? 0 : assetId.hashCode());
		result = prime * result
				+ ((assetStatus == null) ? 0 : assetStatus.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((fileMD5 == null) ? 0 : fileMD5.hashCode());
		result = prime * result + priority;
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
		GCLocalAssetModel other = (GCLocalAssetModel) obj;
		if (assetId == null) {
			if (other.assetId != null) {
				return false;
			}
		} else if (!assetId.equals(other.assetId)) {
			return false;
		}
		if (assetStatus != other.assetStatus) {
			return false;
		}
		if (file == null) {
			if (other.file != null) {
				return false;
			}
		} else if (!file.equals(other.file)) {
			return false;
		}
		if (fileMD5 == null) {
			if (other.fileMD5 != null) {
				return false;
			}
		} else if (!fileMD5.equals(other.fileMD5)) {
			return false;
		}
		if (priority != other.priority) {
			return false;
		}
		return true;
	}

}