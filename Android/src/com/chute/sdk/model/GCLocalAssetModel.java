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

import java.io.File;

import android.content.Context;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.asset.GCAssets;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.parsers.GCLocalAssetListObjectParser;
import com.chute.sdk.utils.MD5;

public class GCLocalAssetModel {
    @SuppressWarnings("unused")
    private static final String TAG = GCLocalAssetModel.class.getSimpleName();

    public enum AssetStatus {
	UNVERIFIED("unverified"), NEW("new"), INITIALIZED("initialized"), COMPLETE("complete"), SKIP(
		"skip");
	private AssetStatus(String name) {
	    this.name = name;
	}

	private final String name;

	@Override
	public String toString() {
	    return name;
	}

    }

    private String assetId;
    private File file;
    private int priority;
    private AssetStatus assetStatus;
    private String fileMD5;

    public GCLocalAssetModel() {
	super();
	this.assetStatus = AssetStatus.UNVERIFIED;
	this.priority = 1;
    }

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

    public String calculateFileMD5() {
	try {
	    this.fileMD5 = MD5.getMD5Checksum(this.file.getPath());
	    return this.fileMD5;
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}
	return "";
    }

    public GCHttpRequest verify(Context context, GCHttpCallback<GCLocalAssetCollection> callback) {
	return GCAssets.verify(context, new GCLocalAssetListObjectParser(), callback, this);
    }

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

    public static AssetStatus resolveAssetStatus(String assetStatus) {
	if (assetStatus.contentEquals(AssetStatus.NEW.toString())) {
	    return AssetStatus.NEW;
	} else if (assetStatus.contentEquals(AssetStatus.INITIALIZED.toString())) {
	    return AssetStatus.INITIALIZED;
	} else if (assetStatus.contentEquals(AssetStatus.COMPLETE.toString())) {
	    return AssetStatus.COMPLETE;
	} else if (assetStatus.contentEquals(AssetStatus.SKIP.toString())) {
	    return AssetStatus.SKIP;
	}
	return AssetStatus.UNVERIFIED;
    }

}
