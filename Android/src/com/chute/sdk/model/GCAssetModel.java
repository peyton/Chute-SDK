// Copyright (c) 2011, Chute Corporation. All rights reserved.
// 
//  Redistribution and use in source and binary forms, with or without modification, 
//  are permitted provided that the following conditions are met:
// 
//     * Redistributions of source code must retain the above copyright notice, this 
//       list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright notice,
//       this list of conditions and the following disclaimer in the documentation
//       and/or other materials provided with the distribution.
//     * Neither the name of the  Chute Corporation nor the names
//       of its contributors may be used to endorse or promote products derived from
//       this software without specific prior written permission.
// 
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
//  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//  OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//  OF THE POSSIBILITY OF SUCH DAMAGE.
// 
package com.chute.sdk.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.asset.GCAssets;
import com.chute.sdk.model.inner.UploadInfo;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.parsers.base.GCStringResponse;

/**
 * {@link GCAssetModel} class represents the concept of an asset. Each asset
 * contains base URL, URL used for sharing, comments counter and which user it
 * belongs to.
 * 
 */
public class GCAssetModel implements Parcelable {
	@SuppressWarnings("unused")
	private static final String TAG = GCAssetModel.class.getSimpleName();

	/**
	 * The unique identifier of the asset.
	 */
	private String id;
	/**
	 * Comments counter.
	 */
	private String commentsCount;
	/**
	 * Flag that indicates if the asset is in portrait or landscape mode.
	 */
	private boolean isPortrait;
	/**
	 * The URL used for sharing.
	 */
	private String shareUrl;
	/**
	 * The URL that shows the location of the asset.
	 */
	private String url;
	/**
	 * The {@link GCUserModel} which the asset belongs to.
	 */
	public GCUserModel user = new GCUserModel();

	/**
	 * A default non-args constructor.
	 */
	public GCAssetModel() {
		super();
	}

	/**
	 * Getter and setter methods
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(String commentsCount) {
		this.commentsCount = commentsCount;
	}

	public boolean isPortrait() {
		return isPortrait;
	}

	public void setPortrait(boolean isPortrait) {
		this.isPortrait = isPortrait;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GCUserModel getUser() {
		return user;
	}

	public void setUser(GCUserModel user) {
		this.user = user;
	}

	/**
	 * Method used for deleting assets. It returns a JSON object containing an
	 * asset using the following parameters: context, string value and the given
	 * callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param parser
	 *            Instance of {@link GCStringResponse}, class that implements
	 *            {@link GCHttpResponseParser}. You can add a custom parser or
	 *            use the parser provided here {@see GCAssets#delete(Context,
	 *            String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} that is used for request
	 *            notification. According to the parser, the callback should
	 *            have the same return type.
	 * @return {@link GCAssets#delete(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest delete(Context context, GCStringResponse parser,
			GCHttpCallback<String> callback) {
		return GCAssets.delete(context, this.id, parser, callback);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCAssetModel [id=");
		builder.append(id);
		builder.append(", commentsCount=");
		builder.append(commentsCount);
		builder.append(", isPortrait=");
		builder.append(isPortrait);
		builder.append(", shareUrl=");
		builder.append(shareUrl);
		builder.append(", url=");
		builder.append(url);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Constructor with fields.
	 * 
	 * @param id
	 * @param commentsCount
	 * @param isPortrait
	 * @param shareUrl
	 * @param url
	 * @param user
	 */
	public GCAssetModel(String id, String commentsCount, boolean isPortrait,
			String shareUrl, String url, GCUserModel user) {
		super();
		this.id = id;
		this.commentsCount = commentsCount;
		this.isPortrait = isPortrait;
		this.shareUrl = shareUrl;
		this.url = url;
		this.user = user;
	}

	public GCAssetModel(Parcel in) {
		id = in.readString();
		commentsCount = in.readString();
		isPortrait = in.readInt() == 1;
		shareUrl = in.readString();
		url = in.readString();
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
		dest.writeString(commentsCount);
		dest.writeInt(isPortrait ? 1 : 0);
		dest.writeString(shareUrl);
		dest.writeString(url);
		dest.writeParcelable(user, flags);
	}

	public static final Parcelable.Creator<GCAssetModel> CREATOR = new Parcelable.Creator<GCAssetModel>() {

		@Override
		public GCAssetModel createFromParcel(Parcel in) {
			return new GCAssetModel(in);
		}

		@Override
		public GCAssetModel[] newArray(int size) {
			return new GCAssetModel[size];
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
		result = prime * result
				+ ((commentsCount == null) ? 0 : commentsCount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isPortrait ? 1231 : 1237);
		result = prime * result
				+ ((shareUrl == null) ? 0 : shareUrl.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GCAssetModel other = (GCAssetModel) obj;
		if (commentsCount == null) {
			if (other.commentsCount != null) {
				return false;
			}
		} else if (!commentsCount.equals(other.commentsCount)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (isPortrait != other.isPortrait) {
			return false;
		}
		if (shareUrl == null) {
			if (other.shareUrl != null) {
				return false;
			}
		} else if (!shareUrl.equals(other.shareUrl)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

}
