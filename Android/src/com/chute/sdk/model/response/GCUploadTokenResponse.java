package com.chute.sdk.model.response;

import java.util.ArrayList;
import java.util.List;

import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.model.GCUploadToken;

public class GCUploadTokenResponse {

	private GCAssetCollection assetCollection = new GCAssetCollection();

	private List<GCUploadToken> token = new ArrayList<GCUploadToken>();

	
	
	public GCUploadTokenResponse() {
		super();
	}

	public GCAssetCollection getAssetCollection() {
		return assetCollection;
	}

	public void setAssetCollection(GCAssetCollection assetCollection) {
		this.assetCollection = assetCollection;
	}

	public List<GCUploadToken> getToken() {
		return token;
	}
	public void setToken(List<GCUploadToken> token) {
		this.token = token;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCUploadTokenResponse [assetCollection=");
		builder.append(assetCollection);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}
	
	
}
