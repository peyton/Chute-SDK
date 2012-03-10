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

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCCommentCollection;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCCommentModel;
import com.chute.sdk.model.GCLocalAssetModel;
import com.chute.sdk.parsers.GCChuteSingleObjectParser;
import com.chute.sdk.parsers.GCCommentListObjectParser;
import com.chute.sdk.parsers.GCCommentSingleObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCComments} class is a helper class which contains methods for
 * deleting, getting and adding comments.
 * 
 */
public class GCComments {
	@SuppressWarnings("unused")
	private static final String TAG = GCComments.class.getSimpleName();

	/**
	 * A private no-args default constructor.
	 */
	private GCComments() {
	}

	/**
	 * Method used for deleting a comment. It returns a JSON object containing a
	 * comment using the following parameters: context, string value and the
	 * given callback and parser.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCCommentModel} ID, representing the comment to be
	 *            deleted.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #delete(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link CommentsDeleteRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest delete(final Context context,
			final String id, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new CommentsDeleteRequest<T>(context, id, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #delete(Context, String,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCCommentSingleObjectParser} which has {@link GCCommentModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCCommentModel} ID, representing the comment to be
	 *            deleted.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCCommentModel}.
	 * @return {@link #delete(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest delete(final Context context, final String id,
			final GCHttpCallback<GCCommentModel> callback) {
		return delete(context, id, new GCCommentSingleObjectParser(), callback);
	}

	/**
	 * Method used for getting a collection of comments. It returns a JSON
	 * object containing array of comments using the following parameters:
	 * context, string value representing the chute ID, string value
	 * representing the asset ID and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteId
	 *            {@link GCChuteModel} ID, representing the chute that contains
	 *            the returned comments.
	 * @param assetId
	 *            {@link GCLocalAssetModel} ID, representing the assets that
	 *            contains the returned comments.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #get(Context, String, String, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link CommentsGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest get(final Context context,
			final String chuteId, final String assetId,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new CommentsGetRequest<T>(context, chuteId, assetId, parser,
				callback);
	}

	/**
	 * Method that defaults to the generic method {@see #get(Context, String,
	 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCCommentListObjectParser} which has {@link GCCommentCollection}
	 * as a return type if the callback is successful.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteId
	 *            {@link GCChuteModel} ID, representing the chute that contains
	 *            the returned comments.
	 * @param assetId
	 *            {@link GCLocalAssetModel} ID, representing the asset that
	 *            contains the returned comments.
	 * @param comment
	 *            String variable representing the actual comment.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCCommentCollection}.
	 * @return {@link #get(Context, String, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest get(final Context context,
			final String chuteId, final String assetId, final String comment,
			final GCHttpCallback<GCCommentCollection> callback) {
		return get(context, chuteId, assetId, new GCCommentListObjectParser(),
				callback);
	}

	/**
	 * Method used for adding a comment. It returns a JSON object containing a
	 * comment using the following parameters: context, string value
	 * representing the chute ID, string value representing the asset ID, string
	 * value representing the actual comment and the given callback and parser.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteId
	 *            {@link GCChuteModel} ID, representing the chute that the added
	 *            comment belongs to.
	 * @param assetId
	 *            {@link GCLocalAssetModel} ID, representing the asset that the
	 *            added comment belongs to.
	 * @param comment
	 *            String variable representing the actual comment.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #add(Context, String, String, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link CommentsPostRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest add(final Context context,
			final String chuteId, final String assetId, final String comment,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new CommentsPostRequest<T>(context, chuteId, assetId, comment,
				parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #add(Context, String,
	 * String, String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteSingleObjectParser} which has {@link GCCommentModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteId
	 *            {@link GCChuteModel} ID, representing the chute that the added
	 *            comment belongs to.
	 * @param assetId
	 *            {@link GCLocalAssetModel} ID, representing the asset that the
	 *            added comment belongs to.
	 * @param comment
	 *            String variable representing the actual comment.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCCommentModel}.
	 * @return {@link #add(Context, String, String, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest add(final Context context,
			final String chuteId, final String assetId, final String comment,
			final GCHttpCallback<GCCommentModel> callback) {
		return add(context, chuteId, assetId, comment,
				new GCCommentSingleObjectParser(), callback);
	}
}
