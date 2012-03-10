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
package com.chute.sdk.api.user;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.model.GCUserModel;
import com.chute.sdk.parsers.GCChuteListObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCUser} class is a helper class which contains methods for
 * obtaining chute collection using user ID.
 * 
 */
public class GCUser {
	@SuppressWarnings("unused")
	private static final String TAG = GCUser.class.getSimpleName();

	/**
	 * A private no-args default constructor.
	 */
	private GCUser() {
		super();
	}

	/**
	 * Method used for getting a specific user chute collection. It returns a
	 * JSON object containing array of chutes using the following parameters:
	 * context, string value and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param userId
	 *            {@link GCUserModel} ID, representing the user that holds the
	 *            returned chute collection.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #userChutes(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link UserChutesRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest userChutes(final Context context,
			final String userId, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new UserChutesRequest<T>(context, userId, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #userChutes(Context,
	 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteListObjectParser} which has {@link GCChuteCollection} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param userId
	 *            {@link GCUserModel} ID, representing the user that holds the
	 *            returned chute collection.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteCollection}.
	 * @return {@link #userChutes(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest userChutes(final Context context,
			final String userId,
			final GCHttpCallback<GCChuteCollection> callback) {
		return userChutes(context, userId, new GCChuteListObjectParser(),
				callback);
	}
}
