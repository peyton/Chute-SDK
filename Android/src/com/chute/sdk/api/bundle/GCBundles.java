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
package com.chute.sdk.api.bundle;

import java.util.ArrayList;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.model.GCAssetModel;
import com.chute.sdk.model.GCBundleModel;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCBundles} class is a helper class which contains methods for
 * getting and creating bundles.
 * 
 */
public class GCBundles {

	@SuppressWarnings("unused")
	private static final String TAG = GCBundles.class.getSimpleName();

	// Cannot instantiate
	/**
	 * A private no-args default constructor.
	 */
	private GCBundles() {
	}

	/**
	 * Method used for getting bundles. It returns a JSON object containing
	 * array of bundles using the following parameters: context, string value
	 * and the given callback and parser.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCBundleModel} ID.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link BundleGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest get(final Context context, final String id,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new BundleGetRequest<T>(context, id, parser, callback);
	}

	/**
	 * Method used for creating bundles. It returns a JSON object containing a
	 * bundle using the following parameters: context, array of assetIds and the
	 * given callback and parser.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @param assetIds
	 *            ArrayList of {@link GCAssetModel} IDs.
	 * @return Instance of {@link BundleCreateRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest create(final Context context,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback, final ArrayList<String> assetIds) {
		return new BundleCreateRequest<T>(context, assetIds, parser, callback);
	}
}
