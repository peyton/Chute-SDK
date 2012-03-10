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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The {@link GCCommentModel} class represents the concept of a comment. Each
 * comment contains status, time of creation, user who wrote the comment and the
 * actual comment.
 * 
 */
public class GCCommentModel implements Parcelable {
	@SuppressWarnings("unused")
	private static final String TAG = GCCommentModel.class.getSimpleName();

	/**
	 * The unique identifier of a comment.
	 */
	private String id;
	/**
	 * The actual comment.
	 */
	private String comment;
	/**
	 * The status of the comment.
	 */
	private int status;
	/**
	 * Time when the comment is first created.
	 */
	private String createdAt;
	/**
	 * {@link GCUserModel} which the comment belongs to.
	 */
	public GCUserModel user = new GCUserModel();

	/**
	 * Default non-args constructor.
	 */
	public GCCommentModel() {
	}

	/**
	 * Getter and setter methods
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCCommentModel [id=");
		builder.append(id);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Constructor with fields.
	 * 
	 * @param id
	 * @param comment
	 * @param status
	 * @param createdAt
	 * @param user
	 */
	public GCCommentModel(String id, String comment, int status,
			String createdAt, GCUserModel user) {
		super();
		this.id = id;
		this.comment = comment;
		this.status = status;
		this.createdAt = createdAt;
		this.user = user;
	}

	public GCCommentModel(Parcel in) {
		id = in.readString();
		comment = in.readString();
		status = in.readInt();
		createdAt = in.readString();
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
		dest.writeString(comment);
		dest.writeInt(status);
		dest.writeString(createdAt);
		dest.writeParcelable(user, flags);
	}

	public static final Parcelable.Creator<GCCommentModel> CREATOR = new Parcelable.Creator<GCCommentModel>() {

		@Override
		public GCCommentModel createFromParcel(Parcel in) {
			return new GCCommentModel(in);
		}

		@Override
		public GCCommentModel[] newArray(int size) {
			return new GCCommentModel[size];
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
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + status;
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
		GCCommentModel other = (GCCommentModel) obj;
		if (comment == null) {
			if (other.comment != null) {
				return false;
			}
		} else if (!comment.equals(other.comment)) {
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
		if (status != other.status) {
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
