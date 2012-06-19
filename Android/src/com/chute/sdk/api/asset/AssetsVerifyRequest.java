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
package com.chute.sdk.api.asset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCConstants;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

class AssetsVerifyRequest<T> extends GCParameterHttpRequestImpl<T> {

	public static final String TAG = AssetsVerifyRequest.class.getSimpleName();
	private final GCLocalAssetModel[] assetModels;

	public AssetsVerifyRequest(Context context, GCHttpResponseParser<T> parser,
			GCHttpCallback<T> callback, GCLocalAssetModel... assetModels) {
		super(context, RequestMethod.POST, parser, callback);
		if (assetModels == null || assetModels.length == 0) {
			throw new NullPointerException(
					"Need to provide an array with at least 1 item (Not NULL)");
		}
		this.assetModels = assetModels;
	}

	@Override
	protected void prepareParams() {
		JSONArray array = new JSONArray();
		JSONObject obj;
		for (int i = 0; i < assetModels.length; i++) {
			obj = new JSONObject();
			try {
				if (assetModels[i].getFile().exists()) {
					obj.put("filename", assetModels[i].getFile().getPath());
					obj.put("size",
							String.valueOf(assetModels[i].getFile().length()));
					if (TextUtils.isEmpty(assetModels[i].getFileMD5())) {
						assetModels[i].calculateFileMD5();
					}
					obj.put("md5", assetModels[i].getFileMD5());
					array.put(obj);
				} else {
					Log.e(TAG, " Doesn't EXIST: "
							+ assetModels[i].getFile().getPath());
				}
			} catch (JSONException e) {
				if (GCConstants.DEBUG) {
					Log.w(TAG, "", e);
				}
			} catch (Exception e) {
				if (GCConstants.DEBUG) {
					Log.w(TAG, "", e);
				}
			}
		}
		addParam("files", array.toString());
	}

	@Override
	public void execute() {
		runRequest(GCRestConstants.URL_ASSET_VERIFY);
	}

}
