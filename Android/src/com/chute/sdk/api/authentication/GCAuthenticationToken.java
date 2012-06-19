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
package com.chute.sdk.api.authentication;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.model.GCAccountStore.GCAuthConstants;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

public class GCAuthenticationToken<T> extends GCParameterHttpRequestImpl<T> {
	@SuppressWarnings("unused")
	private static final String TAG = GCAuthenticationToken.class
			.getSimpleName();
	private final GCAuthConstants authConstants;
	private final String code;

	public GCAuthenticationToken(Context context,
			GCAuthConstants authConstants, String code,
			GCHttpResponseParser<T> parser, GCHttpCallback<T> callback) {
		super(context, RequestMethod.POST, parser, callback);
		this.authConstants = authConstants;
		this.code = code;
	}

	@Override
	public void prepareParams() {
		addParam("code", code);
		addParam("scope", authConstants.scope);
		addParam("client_id", authConstants.clientId);
		addParam("client_secret", authConstants.clientSecret);
		addParam("grant_type", "authorization_code");
		addParam("redirect_uri", authConstants.redirectUri);
	}

	@Override
	public void execute() {
		runRequest(GCRestConstants.URL_AUTHENTICATION_TOKEN);
	}
}
