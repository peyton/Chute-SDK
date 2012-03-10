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

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.collections.GCMemberCollection;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCUserModel;
import com.chute.sdk.parsers.GCAssetListObjectParser;
import com.chute.sdk.parsers.GCChuteListObjectParser;
import com.chute.sdk.parsers.GCChuteSingleObjectParser;
import com.chute.sdk.parsers.GCMemberListObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCChutes} class is a helper class which contains methods for
 * deleting, updating, searching and getting chutes. It is also used for getting
 * a collection of assets, members and contributors.
 * 
 */
public class GCChutes {
	public static final String TAG = GCChutes.class.getSimpleName();

	/**
	 * A private no-args default constructor.
	 */
	private GCChutes() {
	}

	/**
	 * Method used for deleting a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, string value and the given
	 * callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCChuteModel} ID, representing the chute to be deleted.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #delete(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesDeleteRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest delete(final Context context,
			final String id, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ChutesDeleteRequest<T>(context, id, parser, callback);
	}

	/**
	 * Method which defaults to the generic method {@see #delete(Context,
	 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteSingleObjectParser} which has {@link GCChuteModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCChuteModel} ID, representing the chute to be deleted.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link #delete(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest delete(final Context context, final String id,
			final GCHttpCallback<GCChuteModel> callback) {
		return delete(context, id, new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for creating a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, {@link GCChuteModel} and
	 * the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteModel
	 *            Instance of {@link GCChuteModel} class.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #createChute(Context, GCChuteModel, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesCreateRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest createChute(final Context context,
			final GCChuteModel chuteModel,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ChutesCreateRequest<T>(context, chuteModel, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #createChute(Context,
	 * GCChuteModel, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteSingleObjectParser} which has {@link GCChuteModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteModel
	 *            Instance of {@link GCChuteModel} class.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link #createChute(Context, GCChuteModel, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest createChute(final Context context,
			final GCChuteModel chuteModel,
			final GCHttpCallback<GCChuteModel> callback) {
		return createChute(context, chuteModel,
				new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for updating a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, {@link GCChuteModel} and
	 * the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteModel
	 *            Instance of {@link GCChuteModel} class.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #updateChute(Context, GCChuteModel, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesUpdateRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest updateChute(final Context context,
			final GCChuteModel chuteModel,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ChutesUpdateRequest<T>(context, chuteModel, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #updateChute(Context,
	 * GCChuteModel, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteSingleObjectParser} which has {@link GCChuteModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param chuteModel
	 *            Instance of {@link GCChuteModel} class.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link #createChute(Context, GCChuteModel, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest updateChute(final Context context,
			final GCChuteModel chuteModel,
			final GCHttpCallback<GCChuteModel> callback) {
		return updateChute(context, chuteModel,
				new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for getting a chute. It returns a JSON object containing a
	 * chute using the following parameters: context, string value and the given
	 * callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCChuteModel} ID.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #get(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest get(final Context context, final String id,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ChutesGetRequest<T>(context, id, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #get(Context, String,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteSingleObjectParser} which has {@link GCChuteModel} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCChuteModel} ID.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return {@link #get(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest get(final Context context, final String id,
			final GCHttpCallback<GCChuteModel> callback) {
		return get(context, id, new GCChuteSingleObjectParser(), callback);
	}

	/**
	 * Method used for searching chutes using domain name. It returns a JSON
	 * object containing array of chutes using the following parameters:
	 * context, string value and the given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param domain
	 *            String value representing the domain name.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #search(Context, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesSearchGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest search(final Context context,
			final String domain, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ChutesSearchGetRequest<T>(context, domain, parser, callback);
	}

	/**
	 * Method that defaults to the generic method {@see #search(Context, String,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteListObjectParser} which has {@link GCChuteCollection} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param domain
	 *            String value representing the domain name.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteCollection}.
	 * @return {@link #search(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest search(final Context context,
			final String domain,
			final GCHttpCallback<GCChuteCollection> callback) {
		return search(context, domain, new GCChuteListObjectParser(), callback);
	}

	/**
	 * Method used for getting a chute collection. It returns a JSON object
	 * containing array of chutes using the following parameters: context,
	 * string value and the given callback and parser.
	 * 
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCUserModel} ID.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #all(Context, String, GCHttpResponseParser, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesAllGetRequest}, class that implements
	 *         {@link GCHttpRequest}. This request is needed for server
	 *         communication and needs to be executed in order to get response
	 *         from the server.
	 */
	public static <T> GCHttpRequest all(final Context context, final String id,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new ChutesAllGetRequest<T>(context, id, parser, callback);
	}

	/**
	 * 
	 * Method that defaults to the generic method {@see #all(Context, String,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCChuteListObjectParser} which has {@link GCChuteCollection} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param id
	 *            {@link GCUserModel} ID.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteCollection}.
	 * @return {@link #all(Context, String, GCHttpResponseParser, GCHttpCallback)}
	 *         method.
	 */
	public static GCHttpRequest all(final Context context, final String id,
			final GCHttpCallback<GCChuteCollection> callback) {
		return all(context, id, new GCChuteListObjectParser(), callback);
	}

	/**
	 * The {@link GCChutes.Resources} class is an inner class which contains
	 * methods for getting a collection of assets, members and contributors.
	 * 
	 */
	public static class Resources {

		/**
		 * Method used for getting a collection of assets. It returns a JSON
		 * object containing array of assets using the following parameters:
		 * context, string value and the given callback and parser.
		 * 
		 * @param context
		 *            The application context.
		 * @param id
		 *            {@link GCChuteModel} ID, representing the chute to which
		 *            the returned asset collection belongs to.
		 * @param parser
		 *            Instance of {@link GCHttpResponseParser} interface. You
		 *            can add a custom parser or use the parser provided here
		 *            {@see #assets(Context, String, GCHttpCallback)}.
		 * @param callback
		 *            Instance of {@link GCHttpCallback} interface. According to
		 *            the parser, the callback should have the same return type.
		 * @return Instance of {@link ChutesAssetsGetRequest}, class that
		 *         implements {@link GCHttpRequest}.
		 */
		public static <T> GCHttpRequest assets(final Context context,
				final String id, final GCHttpResponseParser<T> parser,
				final GCHttpCallback<T> callback) {
			return new ChutesAssetsGetRequest<T>(context, id, parser, callback);
		}

		/**
		 * Method that defaults to the generic method {@see #assets(Context,
		 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
		 * {@link GCAssetListObjectParser} which has {@link GCAssetCollection}
		 * as a return type if the callback is successful.
		 * 
		 * @param context
		 *            The application context.
		 * @param id
		 *            {@link GCChuteModel} ID, representing the chute to which
		 *            the returned asset collection belongs to.
		 * @param callback
		 *            Instance of {@link GCHttpCallback} interface. If
		 *            successful, the callback returns {@link GCAssetCollection}
		 *            .
		 * @return {@link #assets(Context, String, GCHttpResponseParser, GCHttpCallback)}
		 *         method.
		 */
		public static GCHttpRequest assets(final Context context,
				final String id,
				final GCHttpCallback<GCAssetCollection> callback) {
			return assets(context, id, new GCAssetListObjectParser(), callback);
		}

		/**
		 * Method used for getting a collection of members. It returns a JSON
		 * object containing array of members using the following parameters:
		 * context, string value and the given callback and parser.
		 * 
		 * @param context
		 *            The application context.
		 * @param id
		 *            {@link GCChuteModel} ID, representing the chute to which
		 *            the returned member collection belongs to.
		 * @param parser
		 *            Instance of {@link GCHttpResponseParser} interface. You
		 *            can add a custom parser or use the parser provided here
		 *            {@see #members(Context, String, GCHttpCallback)}.
		 * @param callback
		 *            Instance of {@link GCHttpCallback} interface. According to
		 *            the parser, the callback should have the same return type.
		 * @return Instance of {@link ChutesMembersGetRequest}, class that
		 *         implements {@link GCHttpRequest}.
		 */
		public static <T> GCHttpRequest members(final Context context,
				final String id, final GCHttpResponseParser<T> parser,
				final GCHttpCallback<T> callback) {
			return new ChutesMembersGetRequest<T>(context, id, parser, callback);
		}

		/**
		 * Method that defaults to the generic method {@see #members(Context,
		 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
		 * {@link GCMemberListObjectParser} which has {@link GCMemberCollection}
		 * as a return type if the callback is successful.
		 * 
		 * @param context
		 *            The application context.
		 * @param id
		 *            {@link GCChuteModel} ID, representing the chute to which
		 *            the returned member collection belongs to.
		 * @param callback
		 *            Instance of {@link GCHttpCallback} interface. If
		 *            successful, the callback returns
		 *            {@link GCMemberCollection}.
		 * @return {@link #members(Context, String, GCHttpResponseParser, GCHttpCallback)}
		 *         method.
		 */
		public static GCHttpRequest members(final Context context,
				final String id,
				final GCHttpCallback<GCMemberCollection> callback) {
			return members(context, id, new GCMemberListObjectParser(),
					callback);
		}

		/**
		 * Method used for getting a collection of contributors. It returns a
		 * JSON object containing array of members using the following
		 * parameters: context, string value and the given callback and parser.
		 * 
		 * @param context
		 *            The application context.
		 * @param id
		 *            {@link GCChuteModel} ID, representing the chute to which
		 *            the returned member collection belongs to.
		 * @param parser
		 *            Instance of {@link GCHttpResponseParser} interface. You
		 *            can add a custom parser or use the parser provided here
		 *            {@see #contributors(Context, String, GCHttpCallback)}.
		 * @param callback
		 *            Instance of {@link GCHttpCallback} interface. According to
		 *            the parser, the callback should have the same return type.
		 * @return Instance of {@link ChutesContributorsGetRequest}, class that
		 *         implements {@link GCHttpRequest}.
		 */
		public static <T> GCHttpRequest contributors(final Context context,
				final String id, final GCHttpResponseParser<T> parser,
				final GCHttpCallback<T> callback) {
			return new ChutesContributorsGetRequest<T>(context, id, parser,
					callback);
		}

		/**
		 * Method that defaults to the generic method {@see
		 * #contributors(Context, String, GCHttpResponseParser, GCHttpCallback)}
		 * . This method uses {@link GCMemberListObjectParser} which has
		 * {@link GCMemberCollection} as a return type if the callback is
		 * successful.
		 * 
		 * @param context
		 *            The application context.
		 * @param id
		 *            {@link GCChuteModel} ID, representing the chute to which
		 *            the returned member collection belongs to.
		 * @param callback
		 *            Instance of {@link GCHttpCallback} interface. If
		 *            successful, the callback returns {@link GCChuteCollection}
		 *            .
		 * @return {@link #contributors(Context, String, GCHttpResponseParser, GCHttpCallback)}
		 *         method.
		 */
		public static GCHttpRequest contributors(final Context context,
				final String id,
				final GCHttpCallback<GCMemberCollection> callback) {
			return contributors(context, id, new GCMemberListObjectParser(),
					callback);
		}
	}
}
