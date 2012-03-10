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
package com.chute.sdk.api.heart;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCHeartsMap;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.model.GCUserModel;
import com.chute.sdk.parsers.GCHeartsMapObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCHearts} class is a helper class which contains methods for
 * getting and setting hearts.
 * 
 */
public class GCHearts {

	@SuppressWarnings("unused")
	private static final String TAG = GCHearts.class.getSimpleName();

	/**
	 * Method used for getting hearts. It returns a JSON object containing array
	 * of hearts using the following parameters: context, string value and the
	 * given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param userId
	 *            {@link GCUserModel} ID.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #get(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link HeartsGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest get(final Context context,
			final String userId, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new HeartsGetRequest<T>(context, userId, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #get(Context, String,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCHeartsMapObjectParser} which has {@link GCHeartsMap} as a return
	 * type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param userId
	 *            {@link GCUserModel} ID.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCHeartsMap}.
	 * @return {@link #get(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest get(final Context context, final String userId,
			final GCHttpCallback<GCHeartsMap> callback) {
		return get(context, userId, new GCHeartsMapObjectParser(), callback);
	}

	/**
	 * Method used for setting hearts. It returns a JSON object containing array
	 * of hearts using the following parameters: context, string value
	 * representing the asset ID, boolean value indicating if the heart is
	 * checked, and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param assetId
	 *            {@link GCLocalAssetModel} ID, representing the asset to which
	 *            the heart belongs to.
	 * @param isHeart
	 *            true if the heart is checked, false otherwise.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #set(Context, String, boolean, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link HeartsPostRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest set(final Context context,
			final String assetId, boolean isHeart,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new HeartsPostRequest<T>(context, assetId, isHeart, parser,
				callback);
	}

	/**
	 * Method that defaults to the generic method {@see #set(Context, String,
	 * boolean, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCHeartsMapObjectParser} which has {@link GCHeartsMap} as a return
	 * type if the callback is successful.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param assetId
	 *            {@link GCLocalAssetModel} ID, representing the asset to which
	 *            the heart belogns to.
	 * @param isHeart
	 *            true if the heart is checked, false otherwise.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCHeartsMap}.
	 * @return {@link #set(Context, String, boolean, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest set(final Context context,
			final String assetId, boolean isHeart,
			final GCHttpCallback<GCHeartsMap> callback) {
		return set(context, assetId, isHeart, new GCHeartsMapObjectParser(),
				callback);
	}
}
