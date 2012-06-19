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
package com.chute.sdk.api.membership;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCConstants;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

class ChutesMembershipsInviteRequest<T> extends GCParameterHttpRequestImpl<T> {
	@SuppressWarnings("unused")
	private static final String TAG = ChutesMembershipsInviteRequest.class
			.getSimpleName();
	private final String chuteId;
	private final ArrayList<String> emails;
	private final ArrayList<String> facebook;
	private final ArrayList<String> chute;

	public ChutesMembershipsInviteRequest(Context context, String chuteId,
			ArrayList<String> emails, ArrayList<String> facebookIds,
			ArrayList<String> chuteUserIds, GCHttpResponseParser<T> parser,
			GCHttpCallback<T> callback) {
		super(context, RequestMethod.POST, parser, callback);
		if (TextUtils.isEmpty(chuteId)) {
			throw new NullPointerException("Need to provide an ID of the chute");
		}
		this.chuteId = chuteId;
		this.emails = emails;
		this.facebook = facebookIds;
		this.chute = chuteUserIds;
	}

	@Override
	protected void prepareParams() {
		JSONArray array;

		try {
			array = new JSONArray(emails);
			client.addParam("[providers][email]", array.toString());
		} catch (Exception e) {
			Log.w(TAG, "", e);
		}

		try {
			array = new JSONArray(facebook);
			client.addParam("[providers][facebook]", array.toString());
		} catch (Exception e) {
			if (GCConstants.DEBUG) {
				Log.w(TAG, "", e);
			}
		}

		try {
			array = new JSONArray(chute);
			client.addParam("[providers][chute]", array.toString());
		} catch (Exception e) {
			if (GCConstants.DEBUG) {
				Log.w(TAG, "", e);
			}
		}
	}

	@Override
	public void execute() {
		runRequest(String.format(GCRestConstants.URL_CHUTE_MEMBERSHIPS_INVITE,
				chuteId));
	}
}
