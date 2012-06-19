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
 * The {@link GCUserModel} class represents a concept of a user. Each user
 * contains ID, name and avatar URL.
 * 
 */
public class GCUserModel implements Parcelable {
	@SuppressWarnings("unused")
	private static final String TAG = GCUserModel.class.getSimpleName();

	/**
	 * Unique identifier of the user.
	 */
	private String id;
	/**
	 * User name.
	 */
	private String name;
	
	private String username;
	
	/**
	 * The URL for fetching the user avatar.
	 */
	private String avatarURL;

	/**
	 * Default non-args constructor.
	 */
	public GCUserModel() {
	}

	/**
	 * Constructor with fields.
	 * 
	 * @param id
	 * @param name
	 * @param avatarURL
	 */
	public GCUserModel(String id, String name, String avatarURL) {
		this.id = id;
		this.name = name;
		this.avatarURL = avatarURL;
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

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCUserModel [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", username=");
		builder.append(username);
		builder.append(", avatarURL=");
		builder.append(avatarURL);
		builder.append("]");
		return builder.toString();
	}

	public GCUserModel(Parcel in) {
		id = in.readString();
		name = in.readString();
		avatarURL = in.readString();
		username = in.readString();
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
		dest.writeString(avatarURL);
		dest.writeString(username);
	}

	public static final Parcelable.Creator<GCUserModel> CREATOR = new Parcelable.Creator<GCUserModel>() {

		@Override
		public GCUserModel createFromParcel(Parcel in) {
			return new GCUserModel(in);
		}

		@Override
		public GCUserModel[] newArray(int size) {
			return new GCUserModel[size];
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
				+ ((avatarURL == null) ? 0 : avatarURL.hashCode());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GCUserModel other = (GCUserModel) obj;
		if (avatarURL == null) {
			if (other.avatarURL != null) {
				return false;
			}
		} else if (!avatarURL.equals(other.avatarURL)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
