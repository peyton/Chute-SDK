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
package com.chute.sdk.api.chute;

import android.content.Context;
import android.text.TextUtils;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

class ChutesCreateRequest<T> extends GCParameterHttpRequestImpl<T> {
	@SuppressWarnings("unused")
	private static final String TAG = ChutesCreateRequest.class.getSimpleName();
	private final GCChuteModel model;

	public ChutesCreateRequest(Context context, GCChuteModel model,
			GCHttpResponseParser<T> parser, GCHttpCallback<T> callback) {
		super(context, RequestMethod.POST, parser, callback);
		if (model == null || TextUtils.isEmpty(model.getName())) {
			throw new NullPointerException(
					"Need to provide a Name of the chute");
		}
		this.model = model;
	}

	@Override
	protected void prepareParams() {
		addParam("chute[name]", model.getName());
		addParam("chute[password]", model.getPassword());
		addParam("chute[permission_view]",
				String.valueOf(model.getPermissionView()));
		addParam("chute[permission_add_members]",
				String.valueOf(model.getPermissionAddMembers()));
		addParam("chute[permission_add_photos]",
				String.valueOf(model.getPermissionAddPhotos()));
		addParam("chute[permission_add_comments]",
				String.valueOf(model.getPermissionModerateComments()));
		addParam("chute[moderate_members]",
				String.valueOf(model.getPermissionModerateMembers()));
		addParam("chute[moderate_photos]",
				String.valueOf(model.getPermissionModeratePhotos()));
		addParam("chute[moderate_comments]",
				String.valueOf(model.getPermissionModerateComments()));
	}

	@Override
	public void execute() {
		runRequest(GCRestConstants.URL_CHUTES_CREATE);
	}
}
