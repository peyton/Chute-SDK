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

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.model.GCAssetModel;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.parsers.GCAssetSingleObjectParser;
import com.chute.sdk.parsers.GCChuteSingleObjectParser;
import com.chute.sdk.parsers.GCLocalAssetListObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.parsers.base.GCStringResponse;

/**
 * The {@link GCAssets} class is a helper class which contains methods for
 * deleting, verifying and uploading assets.
 * 
 */
public class GCAssets {

	@SuppressWarnings("unused")
	private static final String TAG = GCAssets.class.getSimpleName();

	/**
	 * A private no-args default constructor.
	 */
	private GCAssets() {
		super();
	}

	/**
	 * Method used for deleting assets. It returns a JSON object containing an
	 * asset using the following parameters: context, string value and the given
	 * callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCAssetModel} ID, representing the asset to be deleted.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #delete(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link AssetsDeleteRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest delete(final Context context,
			final String id, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new AssetsDeleteRequest<T>(context, id, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #delete(Context, String,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCAssetSingleObjectParser} which has {@link GCAssetModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCAssetModel} ID, representing the asset to be deleted.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCAssetModel}.
	 * @return {@link #delete(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest delete(final Context context, final String id,
			final GCHttpCallback<GCAssetModel> callback) {
		return delete(context, id, new GCAssetSingleObjectParser(), callback);
	}

	/**
	 * Method used for verifying assets. It returns a JSON object containing
	 * array of assets using the following parameters: context,
	 * {@link GCLocalAssetModel} and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #verify(Context, GCHttpCallback, GCLocalAssetModel...)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @param assetModels
	 *            Array of {@link GCLocalAssetModel}.
	 * @return Instance of {@link AssetsVerifyRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest verify(final Context context,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback,
			final GCLocalAssetModel... assetModels) {
		return new AssetsVerifyRequest<T>(context, parser, callback,
				assetModels);
	}

	/**
	 * Method that defaults to the generic method {@see #verify(Context,
	 * GCHttpResponseParser, GCHttpCallback, GCLocalAssetModel...)}. This method
	 * uses {@link GCLocalAssetListObjectParser} which has
	 * {@link GCLocalAssetCollection} as a return type if the callback is
	 * successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCLocalAssetCollection}.
	 * @param assetModels
	 *            Array of {@link GCLocalAssetModel}
	 * @return {@link #verify(Context, GCHttpResponseParser, GCHttpCallback, GCLocalAssetModel...)}
	 *         method.
	 */
	public static GCHttpRequest verify(final Context context,
			final GCHttpCallback<GCLocalAssetCollection> callback,
			final GCLocalAssetModel... assetModels) {
		return verify(context, new GCLocalAssetListObjectParser(), callback,
				assetModels);
	}

	/**
	 * Method used for uploading assets. It returns a JSON object containing a
	 * string using the following parameters: context,
	 * {@link GCLocalAssetCollection}, {@link GCUploadProgressListener} and the
	 * given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param onProgressUpdate
	 *            Instance of {@link GCUploadProgressListener} interface.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #upload(Context, GCUploadProgressListener, GCHttpCallback,
	 *            GCLocalAssetCollection)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @param assets
	 *            Instance of {@link GCLocalAssetCollection} class.
	 * @return Instance of {@link AssetsUploadRequest}, a class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest upload(final Context context,
			GCUploadProgressListener onProgressUpdate,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback,
			final GCLocalAssetCollection assets) {

		return new AssetsUploadRequest<T>(context, onProgressUpdate, parser,
				callback, assets);
	}

	/**
	 * Method that defaults to the generic method {@see #upload(Context,
	 * GCUploadProgressListener, GCHttpResponseParser, GCHttpCallback,
	 * GCLocalAssetCollection)}. This method uses {@link GCStringResponse} which
	 * has String as a return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param onProgressUpdate
	 *            Instance of {@link GCUploadProgressListener} interface.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns String.
	 * @param assets
	 *            Instance of {@link GCLocalAssetCollection} class.
	 * @return {@link #upload(Context, GCUploadProgressListener, GCHttpResponseParser, GCHttpCallback, GCLocalAssetCollection)}
	 *         method.
	 */
	public static GCHttpRequest upload(final Context context,
			GCUploadProgressListener onProgressUpdate,
			final GCHttpCallback<String> callback,
			final GCLocalAssetCollection assets) {
		return upload(context, onProgressUpdate, new GCStringResponse(),
				callback, assets);
	}
}
