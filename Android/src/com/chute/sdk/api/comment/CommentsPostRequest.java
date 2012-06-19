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
package com.chute.sdk.api.comment;

import android.content.Context;
import android.text.TextUtils;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCParameterHttpRequestImpl;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCRestConstants;
import com.chute.sdk.utils.rest.GCBaseRestClient.RequestMethod;

class CommentsPostRequest<T> extends GCParameterHttpRequestImpl<T> {
	private final String chuteId;
	private final String assetId;
	private final String comment;

	public CommentsPostRequest(Context context, String chuteId, String assetId,
			String comment, GCHttpResponseParser<T> parser,
			GCHttpCallback<T> callback) {
		super(context, RequestMethod.POST, parser, callback);
		if (TextUtils.isEmpty(chuteId) || TextUtils.isEmpty(assetId)) {
			throw new NullPointerException("Need to provide an ID");
		}
		if (TextUtils.isEmpty(comment)) {
			throw new NullPointerException("Need to provide an Comment String");
		}
		this.chuteId = chuteId;
		this.assetId = assetId;
		this.comment = comment;
	}

	@SuppressWarnings("unused")
	private static final String TAG = CommentsPostRequest.class.getSimpleName();

	@Override
	protected void prepareParams() {
		addParam("comment", comment);
	}

	@Override
	public void execute() {
		runRequest(String.format(GCRestConstants.URL_COMMENTS_POST, chuteId,
				assetId));
	}
}
