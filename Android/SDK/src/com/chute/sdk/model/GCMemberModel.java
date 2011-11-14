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

public class GCMemberModel extends GCUserModel {
    @SuppressWarnings("unused")
    private static final String TAG = GCMemberModel.class.getSimpleName();

    private String assetCount;
    private String assetsPending;
    private String assetsPhotos;
    private String email;
    private String login;
    private String notificationComments;
    private String notificationInvites;
    private String notificationPhotos;
    private String storageAvailable;
    private String storageCurrent;

    public GCMemberModel() {
	super();
    }

    public GCMemberModel(String id, String name, String avatarURL) {
	super(id, name, avatarURL);
    }

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

    public void setUser(final GCUserModel userModel) {
	this.setId(userModel.getId());
	this.setAvatarURL(userModel.getAvatarURL());
	this.setName(userModel.getName());
    }

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
}
