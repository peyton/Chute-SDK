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
package com.chute.sdk.api.parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequestImpl;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRest.RequestMethod;
import com.chute.sdk.utils.GCRestConstants;

class ParcelsCreateRequest<T> extends GCHttpRequestImpl<T> {

    private final GCChuteCollection chutes;
    private final GCLocalAssetCollection assets;

    public ParcelsCreateRequest(Context context, GCLocalAssetCollection assets,
	    GCChuteCollection chutes, GCHttpResponseParser<T> parser, GCHttpCallback<T> callback) {
	super(context, RequestMethod.POST, parser, callback);
	this.assets = assets;
	this.chutes = chutes;
    }

    public static final String TAG = ParcelsCreateRequest.class.getSimpleName();

    @Override
    protected void prepareParams() {
	JSONArray array = new JSONArray();
	for (GCChuteModel chute : chutes) {
	    array.put(chute.getId());
	}
	addParam("chutes", array.toString());
	array = new JSONArray();
	JSONObject obj;
	for (GCLocalAssetModel asset : assets) {
	    obj = new JSONObject();
	    if (asset.getFile().exists()) {
		try {
		    obj.put("filename", asset.getFile().getPath());
		    obj.put("md5", asset.calculateFileMD5());
		    obj.put("size", String.valueOf(asset.getFile().length()));
		    array.put(obj);
		} catch (JSONException e) {
		    Log.d(TAG, "", e);
		} catch (Exception e) {
		    Log.d(TAG, "", e);
		}
	    }
	}
	addParam("files", array.toString());
    }

    @Override
    public void execute() {
	runRequest(GCRestConstants.URL_PARCELS_CREATE);
    }
}
