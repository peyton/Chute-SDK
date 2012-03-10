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

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.parsers.GCCreateParcelsUploadsListParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCParcel} class is a helper class which contains methods for
 * creating parcels containing chutes and assets.
 * 
 */
public class GCParcel {

	@SuppressWarnings("unused")
	private static final String TAG = GCParcel.class.getSimpleName();

	/**
	 * Method used for creating a parcel. It returns a JSON object containing
	 * array of assets using the following parameters: context,
	 * {@link GCLocalAssetCollection}, {@link GCChuteCollection} and the given
	 * callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param assets
	 *            Instance of {@link GCLocalAssetCollection} class, representing
	 *            the assets that the created parcel contains.
	 * @param chutes
	 *            Instance of {@link GCChuteCollection} class, representing the
	 *            chutes that the created parcel contains.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #create(Context, GCLocalAssetCollection, GCChuteCollection,
	 *            GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ParcelsCreateRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest create(final Context context,
			final GCLocalAssetCollection assets,
			final GCChuteCollection chutes,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ParcelsCreateRequest<T>(context, assets, chutes, parser,
				callback);
	}

	/**
	 * Method that defaults to the generic method {@see #create(Context,
	 * GCLocalAssetCollection, GCChuteCollection, GCHttpResponseParser,
	 * GCHttpCallback)}. This method uses
	 * {@link GCCreateParcelsUploadsListParser} which has
	 * {@link GCLocalAssetCollection} as a return type if the callback is
	 * successful.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param assets
	 *            Instance of {@link GCLocalAssetCollection} class, representing
	 *            the assets that the created parcel contains.
	 * @param chutes
	 *            Instance of {@link GCChuteCollection} class, representing the
	 *            chutes that the created parcel contains.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCLocalAssetCollection}.
	 * @return {@link #create(Context, GCLocalAssetCollection, GCChuteCollection, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest create(final Context context,
			final GCLocalAssetCollection assets,
			final GCChuteCollection chutes,
			final GCHttpCallback<GCLocalAssetCollection> callback) {
		return create(context, assets, chutes,
				new GCCreateParcelsUploadsListParser(), callback);
	}
}
