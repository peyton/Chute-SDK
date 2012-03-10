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

import java.util.Collections;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.collections.GCMemberCollection;
import com.chute.sdk.parsers.GCAssetListObjectParser;
import com.chute.sdk.parsers.GCChuteSingleObjectParser;
import com.chute.sdk.parsers.GCMemberListObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCChuteModel} class represents a concept of a chute. Each chute
 * contains name, password, user which the chute belongs to, member counter,
 * contributors counter, time of creation and update, assets counter, thumbnail
 * URL, shortcut, permissions and collections of assets, members and
 * contributors.
 * 
 */
public class GCChuteModel implements Parcelable {
	@SuppressWarnings("unused")
	private static final String TAG = GCChuteModel.class.getSimpleName();

	/**
	 * Unique identifier of a chute.
	 */
	private String id;
	/**
	 * ID of the parcel the chute belongs to.
	 */
	private String recentParcelId;
	/**
	 * ID of the user the chute belongs to.
	 */
	private String recentUserId;
	/**
	 * The name of the chute.
	 */
	private String name;
	/**
	 * The password used for login.
	 */
	private String password;
	/**
	 * The user which the chute belongs to.
	 */
	public GCUserModel user = new GCUserModel();
	/**
	 * Members counter.
	 */
	private String membersCount;
	/**
	 * Contributors counter.
	 */
	private String contributorsCount;
	/**
	 * Counter of recent chutes.
	 */
	private String recentCount;
	/**
	 * Time of creation of the chute.
	 */
	private String createdAt;
	/**
	 * Time of update of the chute.
	 */
	private String updatedAt;
	/**
	 * Assets counter in a chute.
	 */
	private String assetsCount;
	/**
	 * The URL used for fetching the recent Thumbnail.
	 */
	private String recentThumbnailURL;
	/**
	 * Shortcut of the chute.
	 */
	private String shortcut;
	/**
	 * Permission for viewing the chute.
	 */
	private int permissionView;
	/**
	 * Permission for adding members to the chute.
	 */
	private int permissionAddMembers;
	/**
	 * Permission for adding photos to the chute.
	 */
	private int permissionAddPhotos;
	/**
	 * Permission for adding comments to the chute.
	 */
	private int permissionAddComments;
	/**
	 * Permission for moderating members in a chute.
	 */
	private int permissionModerateMembers;
	/**
	 * Permission for moderating photos in a chute.
	 */
	private int permissionModeratePhotos;
	/**
	 * Permission for moderating comments in a chute.
	 */
	private int permissionModerateComments;
	/**
	 * Collection of assets in a chute.
	 */
	public GCAssetCollection assetCollection = new GCAssetCollection();
	/**
	 * Collection of members in a chute.
	 */
	public GCMemberCollection memberCollection = new GCMemberCollection();
	/**
	 * Collection of contributors in a chute.
	 */
	public GCMemberCollection contributorCollection = new GCMemberCollection();

	/**
	 * Default non-args constructor.
	 */
	public GCChuteModel() {
	}

	/**
	 * Getter and setter methods.
	 */
	public GCUserModel getUser() {
		return user;
	}

	public void setUser(GCUserModel user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecentParcelId() {
		return recentParcelId;
	}

	public void setRecentParcelId(String recentParcelId) {
		this.recentParcelId = recentParcelId;
	}

	public String getRecentUserId() {
		return recentUserId;
	}

	public void setRecentUserId(String recentUserId) {
		this.recentUserId = recentUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMembersCount() {
		return membersCount;
	}

	public void setMembersCount(String membersCount) {
		this.membersCount = membersCount;
	}

	public String getContributorsCount() {
		return contributorsCount;
	}

	public void setContributorsCount(String contributorsCount) {
		this.contributorsCount = contributorsCount;
	}

	public String getRecentCount() {
		return recentCount;
	}

	public void setRecentCount(String recentCount) {
		this.recentCount = recentCount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getAssetsCount() {
		return assetsCount;
	}

	public void setAssetsCount(String assetsCount) {
		this.assetsCount = assetsCount;
	}

	public String getRecentThumbnailURL() {
		return recentThumbnailURL;
	}

	public void setRecentThumbnailURL(String recentThumbnailURL) {
		this.recentThumbnailURL = recentThumbnailURL;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public int getPermissionView() {
		return permissionView;
	}

	public void setPermissionView(int permissionView) {
		this.permissionView = permissionView;
	}

	public int getPermissionAddMembers() {
		return permissionAddMembers;
	}

	public void setPermissionAddMembers(int permissionAddMembers) {
		this.permissionAddMembers = permissionAddMembers;
	}

	public int getPermissionAddPhotos() {
		return permissionAddPhotos;
	}

	public void setPermissionAddPhotos(int permissionAddPhotos) {
		this.permissionAddPhotos = permissionAddPhotos;
	}

	public int getPermissionAddComments() {
		return permissionAddComments;
	}

	public void setPermissionAddComments(int permissionAddComments) {
		this.permissionAddComments = permissionAddComments;
	}

	public int getPermissionModerateMembers() {
		return permissionModerateMembers;
	}

	public void setPermissionModerateMembers(int permissionModerateMembers) {
		this.permissionModerateMembers = permissionModerateMembers;
	}

	public int getPermissionModeratePhotos() {
		return permissionModeratePhotos;
	}

	public void setPermissionModeratePhotos(int permissionModeratePhotos) {
		this.permissionModeratePhotos = permissionModeratePhotos;
	}

	public int getPermissionModerateComments() {
		return permissionModerateComments;
	}

	public void setPermissionModerateComments(int permissionModerateComments) {
		this.permissionModerateComments = permissionModerateComments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCChuteModel [id=");
		builder.append(id);
		builder.append(", recentParcelId=");
		builder.append(recentParcelId);
		builder.append(", recentUserId=");
		builder.append(recentUserId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", password=");
		builder.append(password);
		builder.append(", user=");
		builder.append(user);
		builder.append(", membersCount=");
		builder.append(membersCount);
		builder.append(", contributorsCount=");
		builder.append(contributorsCount);
		builder.append(", recentCount=");
		builder.append(recentCount);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", updatedAt=");
		builder.append(updatedAt);
		builder.append(", assetsCount=");
		builder.append(assetsCount);
		builder.append(", recentThumbnailURL=");
		builder.append(recentThumbnailURL);
		builder.append(", shortcut=");
		builder.append(shortcut);
		builder.append(", permissionView=");
		builder.append(permissionView);
		builder.append(", permissionAddMembers=");
		builder.append(permissionAddMembers);
		builder.append(", permissionAddPhotos=");
		builder.append(permissionAddPhotos);
		builder.append(", permissionAddComments=");
		builder.append(permissionAddComments);
		builder.append(", permissionModerateMembers=");
		builder.append(permissionModerateMembers);
		builder.append(", permissionModeratePhotos=");
		builder.append(permissionModeratePhotos);
		builder.append(", permissionModerateComments=");
		builder.append(permissionModerateComments);
		builder.append(", assetCollection=");
		builder.append(assetCollection);
		builder.append(", memberCollection=");
		builder.append(memberCollection);
		builder.append(", contributorCollection=");
		builder.append(contributorCollection);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Method used for updating a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, {@link GCChuteModel} and
	 * the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link GCChutes#updateChute(Context, GCChuteModel, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest update(Context context,
			final GCHttpCallback<GCChuteModel> callback) {
		return GCChutes.updateChute(context, this,
				new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for creating a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, {@link GCChuteModel} and
	 * the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link GCChutes#createChute(Context, GCChuteModel, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest create(Context context,
			final GCHttpCallback<GCChuteModel> callback) {
		return GCChutes.createChute(context, this,
				new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for deleting a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, string value and the given
	 * callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link GCChutes#delete(Context, String, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest delete(Context context,
			final GCHttpCallback<GCChuteModel> callback) {
		return GCChutes.delete(context, this.id,
				new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for getting a collection of assets. It returns a JSON object
	 * containing array of assets using the following parameters: context,
	 * string value and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCAssetCollection}.
	 * @return {@link GCChutes.Resources#assets(Context, String, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest assets(Context context,
			final GCHttpCallback<GCChuteModel> callback) {
		return GCChutes.Resources.assets(context, id,
				new GCAssetListObjectParser(),
				new GCHttpCallback<GCAssetCollection>() {

					@Override
					public void onSuccess(GCAssetCollection responseData) {
						assetCollection = responseData;
						Collections.reverse(assetCollection);
						callback.onSuccess(GCChuteModel.this);
					}

					@Override
					public void onHttpException(GCHttpRequestParameters params,
							Throwable exception) {
						callback.onHttpException(params, exception);
					}

					@Override
					public void onHttpError(int responseCode,
							String statusMessage) {
						callback.onHttpError(responseCode, statusMessage);
					}

					@Override
					public void onParserException(int responseCode,
							Throwable exception) {
						callback.onParserException(responseCode, exception);
					}
				});
	}

	/**
	 * Method used for getting a collection of members. It returns a JSON object
	 * containing array of members using the following parameters: context,
	 * string value and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCMemberCollection}.
	 * @return {@link GCChutes.Resources#members(Context, String, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest members(Context context,
			final GCHttpCallback<GCChuteModel> callback) {
		return GCChutes.Resources.members(context, id,
				new GCMemberListObjectParser(),
				new GCHttpCallback<GCMemberCollection>() {

					@Override
					public void onSuccess(GCMemberCollection responseData) {
						memberCollection = responseData;
						Collections.reverse(memberCollection);
						callback.onSuccess(GCChuteModel.this);
					}

					@Override
					public void onHttpException(GCHttpRequestParameters params,
							Throwable exception) {
						callback.onHttpException(params, exception);
					}

					@Override
					public void onHttpError(int responseCode,
							String statusMessage) {
						callback.onHttpError(responseCode, statusMessage);
					}

					@Override
					public void onParserException(int responseCode,
							Throwable exception) {
						callback.onParserException(responseCode, exception);
					}
				});

	}

	/**
	 * Method used for getting a collection of contributors. It returns a JSON
	 * object containing array of members using the following parameters:
	 * context, string value and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCMemberCollection}.
	 * @return {@link GCChutes.Resources#contributors(Context, String, com.chute.sdk.parsers.base.GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public GCHttpRequest contributors(Context context,
			final GCHttpCallback<GCChuteModel> callback) {
		return GCChutes.Resources.contributors(context, id,
				new GCMemberListObjectParser(),
				new GCHttpCallback<GCMemberCollection>() {

					@Override
					public void onSuccess(GCMemberCollection responseData) {
						contributorCollection = responseData;
						Collections.reverse(contributorCollection);
						callback.onSuccess(GCChuteModel.this);
					}

					@Override
					public void onHttpException(GCHttpRequestParameters params,
							Throwable exception) {
						callback.onHttpException(params, exception);
					}

					@Override
					public void onHttpError(int responseCode,
							String statusMessage) {
						callback.onHttpError(responseCode, statusMessage);
					}

					@Override
					public void onParserException(int responseCode,
							Throwable exception) {
						callback.onParserException(responseCode, exception);
					}
				});
	}

	public GCChuteModel(Parcel in) {
		this();
		id = in.readString();
		recentParcelId = in.readString();
		recentUserId = in.readString();
		name = in.readString();
		password = in.readString();
		user = in.readParcelable(GCUserModel.class.getClassLoader());
		membersCount = in.readString();
		contributorsCount = in.readString();
		recentCount = in.readString();
		createdAt = in.readString();
		updatedAt = in.readString();
		assetsCount = in.readString();
		recentThumbnailURL = in.readString();
		shortcut = in.readString();
		permissionView = in.readInt();
		permissionAddMembers = in.readInt();
		permissionAddPhotos = in.readInt();
		permissionAddComments = in.readInt();
		permissionModerateMembers = in.readInt();
		permissionModeratePhotos = in.readInt();
		permissionModerateComments = in.readInt();

		in.readTypedList(assetCollection, GCAssetModel.CREATOR);

		in.readTypedList(memberCollection, GCMemberModel.CREATOR);

		in.readTypedList(contributorCollection, GCMemberModel.CREATOR);
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
		dest.writeString(recentParcelId);
		dest.writeString(recentUserId);
		dest.writeString(name);
		dest.writeString(password);
		dest.writeParcelable(user, flags);
		dest.writeString(membersCount);
		dest.writeString(contributorsCount);
		dest.writeString(recentCount);
		dest.writeString(createdAt);
		dest.writeString(updatedAt);
		dest.writeString(assetsCount);
		dest.writeString(recentThumbnailURL);
		dest.writeString(shortcut);
		dest.writeInt(permissionView);
		dest.writeInt(permissionAddMembers);
		dest.writeInt(permissionAddPhotos);
		dest.writeInt(permissionAddComments);
		dest.writeInt(permissionModerateMembers);
		dest.writeInt(permissionModeratePhotos);
		dest.writeInt(permissionModerateComments);

		dest.writeTypedList(assetCollection);
		dest.writeTypedList(memberCollection);
		dest.writeTypedList(contributorCollection);
	}

	public static final Parcelable.Creator<GCChuteModel> CREATOR = new Parcelable.Creator<GCChuteModel>() {

		@Override
		public GCChuteModel createFromParcel(Parcel in) {
			return new GCChuteModel(in);
		}

		@Override
		public GCChuteModel[] newArray(int size) {
			return new GCChuteModel[size];
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
				+ ((assetCollection == null) ? 0 : assetCollection.hashCode());
		result = prime * result
				+ ((assetsCount == null) ? 0 : assetsCount.hashCode());
		result = prime
				* result
				+ ((contributorCollection == null) ? 0 : contributorCollection
						.hashCode());
		result = prime
				* result
				+ ((contributorsCount == null) ? 0 : contributorsCount
						.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((memberCollection == null) ? 0 : memberCollection.hashCode());
		result = prime * result
				+ ((membersCount == null) ? 0 : membersCount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + permissionAddComments;
		result = prime * result + permissionAddMembers;
		result = prime * result + permissionAddPhotos;
		result = prime * result + permissionModerateComments;
		result = prime * result + permissionModerateMembers;
		result = prime * result + permissionModeratePhotos;
		result = prime * result + permissionView;
		result = prime * result
				+ ((recentCount == null) ? 0 : recentCount.hashCode());
		result = prime * result
				+ ((recentParcelId == null) ? 0 : recentParcelId.hashCode());
		result = prime
				* result
				+ ((recentThumbnailURL == null) ? 0 : recentThumbnailURL
						.hashCode());
		result = prime * result
				+ ((recentUserId == null) ? 0 : recentUserId.hashCode());
		result = prime * result
				+ ((shortcut == null) ? 0 : shortcut.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		GCChuteModel other = (GCChuteModel) obj;
		if (assetCollection == null) {
			if (other.assetCollection != null) {
				return false;
			}
		} else if (!assetCollection.equals(other.assetCollection)) {
			return false;
		}
		if (assetsCount == null) {
			if (other.assetsCount != null) {
				return false;
			}
		} else if (!assetsCount.equals(other.assetsCount)) {
			return false;
		}
		if (contributorCollection == null) {
			if (other.contributorCollection != null) {
				return false;
			}
		} else if (!contributorCollection.equals(other.contributorCollection)) {
			return false;
		}
		if (contributorsCount == null) {
			if (other.contributorsCount != null) {
				return false;
			}
		} else if (!contributorsCount.equals(other.contributorsCount)) {
			return false;
		}
		if (createdAt == null) {
			if (other.createdAt != null) {
				return false;
			}
		} else if (!createdAt.equals(other.createdAt)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (memberCollection == null) {
			if (other.memberCollection != null) {
				return false;
			}
		} else if (!memberCollection.equals(other.memberCollection)) {
			return false;
		}
		if (membersCount == null) {
			if (other.membersCount != null) {
				return false;
			}
		} else if (!membersCount.equals(other.membersCount)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (permissionAddComments != other.permissionAddComments) {
			return false;
		}
		if (permissionAddMembers != other.permissionAddMembers) {
			return false;
		}
		if (permissionAddPhotos != other.permissionAddPhotos) {
			return false;
		}
		if (permissionModerateComments != other.permissionModerateComments) {
			return false;
		}
		if (permissionModerateMembers != other.permissionModerateMembers) {
			return false;
		}
		if (permissionModeratePhotos != other.permissionModeratePhotos) {
			return false;
		}
		if (permissionView != other.permissionView) {
			return false;
		}
		if (recentCount == null) {
			if (other.recentCount != null) {
				return false;
			}
		} else if (!recentCount.equals(other.recentCount)) {
			return false;
		}
		if (recentParcelId == null) {
			if (other.recentParcelId != null) {
				return false;
			}
		} else if (!recentParcelId.equals(other.recentParcelId)) {
			return false;
		}
		if (recentThumbnailURL == null) {
			if (other.recentThumbnailURL != null) {
				return false;
			}
		} else if (!recentThumbnailURL.equals(other.recentThumbnailURL)) {
			return false;
		}
		if (recentUserId == null) {
			if (other.recentUserId != null) {
				return false;
			}
		} else if (!recentUserId.equals(other.recentUserId)) {
			return false;
		}
		if (shortcut == null) {
			if (other.shortcut != null) {
				return false;
			}
		} else if (!shortcut.equals(other.shortcut)) {
			return false;
		}
		if (updatedAt == null) {
			if (other.updatedAt != null) {
				return false;
			}
		} else if (!updatedAt.equals(other.updatedAt)) {
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
