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
import com.chute.sdk.api.asset.GCAssets;
import com.chute.sdk.parsers.base.GCStringResponse;

public class GCAssetModel {
    @SuppressWarnings("unused")
    private static final String TAG = GCAssetModel.class.getSimpleName();

    private String id;
    private String commentsCount;
    private boolean isPortrait;
    private String shareUrl;
    private String url;
    public GCUserModel user = new GCUserModel();

    public GCAssetModel() {
	super();
    }

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

    public GCHttpRequest delete(Context context, GCStringResponse parser,
	    GCHttpCallback<String> callback) {
	return GCAssets.delete(context, this.id, parser, callback);
    }

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
}
