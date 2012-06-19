package com.chute.sdk.api.account;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.collections.GCAccountMediaCollection;
import com.chute.sdk.collections.GCAccountObjectCollection;
import com.chute.sdk.collections.GCAccountsCollection;
import com.chute.sdk.model.GCAccountModel;
import com.chute.sdk.model.GCAccountObjectModel;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.parsers.GCAccountListParser;
import com.chute.sdk.parsers.GCAccountMediaListParser;
import com.chute.sdk.parsers.GCAccountObjectListParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCAccounts} is a helper class which contains methods for getting
 * accounts, list of objects for the specific account, as well as media items
 * for the specific object.
 * 
 */
public class GCAccounts {

	public static final String TAG = GCAccounts.class.getSimpleName();

	/**
	 * Method used for getting accounts. It returns a JSon object containing a
	 * list of accounts using the following parameters: context, given parser
	 * and given callback.
	 * 
	 * @param context
	 *            The application context.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #all(Context, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link AccountsGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest all(final Context context,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new AccountsGetRequest<T>(context, parser, callback);
	}

	/**
	 * Method which defaults to the generic method {@see #all(Context,
	 * GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCAccountListParser} which has {@link GCAccountsCollection} as a
	 * return type if the callback is successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCChuteModel}.
	 * @return Instance of {@link AccountsGetRequest}, class that implements
	 *         {@link GCHttpRequest}.
	 */
	public static GCHttpRequest all(final Context context,
			final GCHttpCallback<GCAccountsCollection> callback) {
		return new AccountsGetRequest<GCAccountsCollection>(context,
				new GCAccountListParser(), callback);
	}

	/**
	 * Method used for getting list of object for a specific account. It returns
	 * a JSon object using the following parameters: context, string value, the
	 * given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param accountId
	 *            {@link GCAccountModel} ID, representing the specific account.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #objectMedia(Context, String, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link AccountsGetObjectsRequest}, class that
	 *         implements {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest objects(final Context context,
			final String accountId, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new AccountsGetObjectsRequest<T>(context, accountId, parser,
				callback);
	}

	/**
	 * Method which defaults to the generic method {@see #objects(Context,
	 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCAccountObjectListParser} which has
	 * {@link GCAccountObjectCollection} as a return type if the callback is
	 * successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param accountId
	 *            {@link GCAccountModel} ID, representing the specific account.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCAccountObjectCollection}.
	 * @return Instance of {@link AccountsGetObjectsRequest}, class that
	 *         implements {@link GCHttpRequest}.
	 */
	public static GCHttpRequest objects(final Context context,
			final String accountId,
			final GCHttpCallback<GCAccountObjectCollection> callback) {
		return new AccountsGetObjectsRequest<GCAccountObjectCollection>(
				context, accountId, new GCAccountObjectListParser(), callback);
	}

	/**
	 * Method used for getting media items for a specific object. It returns
	 * JSon object using the following parameters: context, string values, the
	 * given callback and parser.
	 * 
	 * @param context
	 *            The application context.
	 * @param accountId
	 *            {@link GCAccountModel} ID, representing the specific account.
	 * @param objectId
	 *            {@link GCAccountObjectModel} ID, representing the specific
	 *            object from an account.
	 * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #objectMedia(Context, String, String, GCHttpCallback)}.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link AccountsGetObjectMediaRequest}, class that
	 *         implements {@link GCHttpRequest}.
	 */
	public static <T> GCHttpRequest objectMedia(final Context context,
			final String accountId, final String objectId,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new AccountsGetObjectMediaRequest<T>(context, accountId,
				objectId, parser, callback);
	}

	/**
	 * Method which defaults to the generic method {@see #objectMedia(Context,
	 * String, String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCAccountMediaListParser} which has
	 * {@link GCAccountMediaCollection} as a return type if the callback is
	 * successful.
	 * 
	 * @param context
	 *            The application context.
	 * @param accountId
	 *            {@link GCAccountModel} ID, representing the specific account.
	 * @param objectId
	 *            {@link GCAccountObjectModel} ID, representing the specific
	 *            object from an account.
	 * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns {@link GCAccountMediaCollection}.
	 * @return Instance of {@link AccountsGetObjectMediaRequest}, class that
	 *         implements {@link GCHttpRequest}.
	 */
	public static GCHttpRequest objectMedia(final Context context,
			final String accountId, final String objectId,
			final GCHttpCallback<GCAccountMediaCollection> callback) {
		return new AccountsGetObjectMediaRequest<GCAccountMediaCollection>(
				context, accountId, objectId, new GCAccountMediaListParser(),
				callback);
	}

}
