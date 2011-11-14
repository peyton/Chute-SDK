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

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.chute.GCChutes;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.collections.GCMemberCollection;
import com.chute.sdk.parsers.GCAssetListObjectParser;
import com.chute.sdk.parsers.GCChuteSingleObjectParser;
import com.chute.sdk.parsers.GCMemberListObjectParser;

public class GCChuteModel {
    @SuppressWarnings("unused")
    private static final String TAG = GCChuteModel.class.getSimpleName();

    private String id;
    private String recentParcelId;
    private String recentUserId;
    private String name;
    private String password;
    public GCUserModel user = new GCUserModel();
    private String membersCount;
    private String contributorsCount;
    private String recentCount;
    private String createdAt;
    private String updatedAt;
    private String assetsCount;
    private String recentThumbnailURL;
    private String shortcut;
    private int permissionView;
    private int permissionAddMembers;
    private int permissionAddPhotos;
    private int permissionAddComments;
    private int permissionModerateMembers;
    private int permissionModeratePhotos;
    private int permissionModerateComments;
    public GCAssetCollection assetCollection;
    public GCMemberCollection memberCollection;
    public GCMemberCollection contributorCollection;

    public GCChuteModel() {
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

    public GCHttpRequest update(Context context, final GCHttpCallback<GCChuteModel> callback) {
	return GCChutes.updateChute(context, this, new GCChuteSingleObjectParser(), callback);
    }

    public GCHttpRequest create(Context context, final GCHttpCallback<GCChuteModel> callback) {
	return GCChutes.createChute(context, this, new GCChuteSingleObjectParser(), callback);
    }

    public GCHttpRequest delete(Context context, final GCHttpCallback<GCChuteModel> callback) {
	return GCChutes.delete(context, this.id, new GCChuteSingleObjectParser(), callback);
    }

    public GCHttpRequest assets(Context context, final GCHttpCallback<GCChuteModel> callback) {
	return GCChutes.Resources.assets(context, id, new GCAssetListObjectParser(),
		new GCHttpCallback<GCAssetCollection>() {

		    @Override
		    public void onSuccess(GCAssetCollection responseData) {
			assetCollection = responseData;
			callback.onSuccess(GCChuteModel.this);
		    }

		    @Override
		    public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
			callback.onHttpException(params, exception);
		    }

		    @Override
		    public void onHttpError(int responseCode, String statusMessage) {
			callback.onHttpError(responseCode, statusMessage);
		    }

		    @Override
		    public void onParserException(int responseCode, Throwable exception) {
			callback.onParserException(responseCode, exception);
		    }
		});
    }

    public GCHttpRequest members(Context context, final GCHttpCallback<GCChuteModel> callback) {
	return GCChutes.Resources.members(context, id, new GCMemberListObjectParser(),
		new GCHttpCallback<GCMemberCollection>() {

		    @Override
		    public void onSuccess(GCMemberCollection responseData) {
			memberCollection = responseData;
			callback.onSuccess(GCChuteModel.this);
		    }

		    @Override
		    public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
			callback.onHttpException(params, exception);
		    }

		    @Override
		    public void onHttpError(int responseCode, String statusMessage) {
			callback.onHttpError(responseCode, statusMessage);
		    }

		    @Override
		    public void onParserException(int responseCode, Throwable exception) {
			callback.onParserException(responseCode, exception);
		    }
		});

    }

    public GCHttpRequest contributors(Context context, final GCHttpCallback<GCChuteModel> callback) {
	return GCChutes.Resources.contributors(context, id, new GCMemberListObjectParser(),
		new GCHttpCallback<GCMemberCollection>() {

		    @Override
		    public void onSuccess(GCMemberCollection responseData) {
			contributorCollection = responseData;
			callback.onSuccess(GCChuteModel.this);
		    }

		    @Override
		    public void onHttpException(GCHttpRequestParameters params, Throwable exception) {
			callback.onHttpException(params, exception);
		    }

		    @Override
		    public void onHttpError(int responseCode, String statusMessage) {
			callback.onHttpError(responseCode, statusMessage);
		    }

		    @Override
		    public void onParserException(int responseCode, Throwable exception) {
			callback.onParserException(responseCode, exception);
		    }
		});
    }
}
