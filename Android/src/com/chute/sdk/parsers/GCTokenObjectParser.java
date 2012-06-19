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
package com.chute.sdk.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.model.GCAssetModel;
import com.chute.sdk.model.GCUploadToken;
import com.chute.sdk.model.inner.Meta;
import com.chute.sdk.model.response.GCUploadTokenResponse;
import com.chute.sdk.parsers.base.GCBaseUploadInfoParser;
import com.chute.sdk.parsers.base.GCBaseUserModelParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

public class GCTokenObjectParser implements GCHttpResponseParser<GCUploadTokenResponse> {

	@Override
	public GCUploadTokenResponse parse(final String responseBody) throws JSONException {
		final GCUploadTokenResponse response =new GCUploadTokenResponse();
		
		final Meta meta = new Meta();
		JSONObject dataRoot = new JSONObject(responseBody);
		meta.setVersion(dataRoot.getJSONObject("meta").getInt("version"));
		meta.setCode(dataRoot.getJSONObject("meta").getInt("code"));
		
		dataRoot = dataRoot.getJSONObject("data");
		
		JSONArray assetsArray = dataRoot.getJSONArray("new_assets");
		JSONObject obj;
		for (int i = 0; i < assetsArray.length(); i++) {
			obj = assetsArray.getJSONObject(i);
			
			final GCAssetModel assetModel = parseTokenAsset(obj);
			response.getAssetCollection().add(assetModel);
			
			final GCUploadToken token = new GCUploadToken();
			token.setMeta(meta);
			token.setUploadInfo(GCBaseUploadInfoParser.parse(obj.optJSONObject("upload_info")));
			token.setId(dataRoot.getString("id"));
			response.getToken().add(token);
		}
		
		assetsArray = dataRoot.getJSONArray("existing_assets");
		for (int i = 0; i < assetsArray.length(); i++) {
			obj = assetsArray.getJSONObject(i);
			final GCAssetModel assetModel = parseTokenAsset(obj);
			response.getAssetCollection().add(assetModel);
		}
		return response;
	}

	private GCAssetModel parseTokenAsset(final JSONObject obj) throws JSONException {
		final GCAssetModel assetModel = new GCAssetModel();
		assetModel.setId(obj.getString("id"));
		assetModel.setUrl(obj.getString("url"));
		assetModel.setPortrait(obj.getBoolean("is_portrait"));
		
		//	assetModel.setSourceUrl(dataRoot.getString("source_url"));
		
		assetModel.setUser(GCBaseUserModelParser.parse(obj.getJSONObject("user")));
		return assetModel;
	}
}
