package com.chute.sdk.model.response;

import com.chute.sdk.collections.GCLocalAssetCollection;

public class GCParcelCreateResponse {

	public static final String TAG = GCParcelCreateResponse.class
			.getSimpleName();

	private String id;

	private GCLocalAssetCollection localAssetCollection;

	private String parcelShareUrl;

	public GCParcelCreateResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GCLocalAssetCollection getLocalAssetCollection() {
		return localAssetCollection;
	}

	public void setLocalAssetCollection(
			GCLocalAssetCollection localAssetCollection) {
		this.localAssetCollection = localAssetCollection;
	}

	public String getParcelShareUrl() {
		return parcelShareUrl;
	}

	public void setParcelShareUrl(String parcelShareUrl) {
		this.parcelShareUrl = parcelShareUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GCParcelCreateResponse [id=");
		builder.append(id);
		builder.append(", localAssetCollection=");
		builder.append(localAssetCollection);
		builder.append(", parcelShareUrl=");
		builder.append(parcelShareUrl);
		builder.append("]");
		return builder.toString();
	}

}
