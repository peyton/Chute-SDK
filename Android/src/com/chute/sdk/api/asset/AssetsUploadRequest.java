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

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpCallbackImpl;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.api.GCHttpRequestStore;
import com.chute.sdk.collections.GCAssetCollection;
import com.chute.sdk.collections.GCChuteCollection;
import com.chute.sdk.collections.GCLocalAssetCollection;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.model.GCUploadToken;
import com.chute.sdk.model.response.GCUploadTokenResponse;
import com.chute.sdk.parsers.GCTokenObjectParser;
import com.chute.sdk.parsers.GCUploadCompleteObjectParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.Logger;
import com.chute.sdk.utils.rest.GCS3Uploader;

public class AssetsUploadRequest implements GCHttpRequest {

	public static final String TAG = AssetsUploadRequest.class.getSimpleName();
	private final GCLocalAssetCollection localAssetCollection;
	private final GCUploadProgressListener onProgressUpdate;
	private final GCHttpCallback<GCAssetCollection> callback;

	private GCUploadTokenResponse list;

	private final Context context;
	final ArrayList<String> chuteIds = new ArrayList<String>();

	public void resumeThread() {
		synchronized (this) {
			Log.w(TAG, "Resumed thread");
			this.notifyAll();
		}
	}

	public AssetsUploadRequest(final Context context,
			final GCUploadProgressListener onProgressUpdate,
			final GCHttpCallback<GCAssetCollection> callback,
			final GCLocalAssetCollection assetCollection,
			final GCChuteCollection chuteCollection) {
		this.context = context;
		this.callback = callback;

		if (assetCollection == null || assetCollection.size() == 0) {
			throw new NullPointerException(
					"Need to provide an array with at least 1 item (Not NULL)");
		}
		for (final GCChuteModel chuteModel : chuteCollection) {
			chuteIds.add(chuteModel.getId());
		}
		this.onProgressUpdate = onProgressUpdate;
		this.localAssetCollection = assetCollection;
	}

	@Override
	public void execute() {
		final GCS3Uploader uploader = new GCS3Uploader(onProgressUpdate);
		try {
			final GCHttpRequest token = getToken(context, localAssetCollection,
					chuteIds, new TokenListener());
			synchronized (this) {
				Log.w(TAG, "Wait thread");
				token.execute();
				this.wait(4000);
			}
			for (GCUploadToken uploadToken : list.getToken()) {
				Logger.d(TAG, "Reading Token");
				if (uploadToken.getUploadInfo() != null) {
					uploader.startUpload(uploadToken);
					Logger.d(TAG, "Calling upload complete");
					Logger.d(TAG, "Need to upload");
					uploadComplete(context, uploadToken.getId(),
							new GCUploadCompleteObjectParser(),
							new UploadCompleteListener()).execute();
				}
			}
		} catch (final Exception e) {
			Logger.e(TAG, "Error in the Upload Request", e);
			callback.onHttpException(null, e);
		}
	}

	private final class TokenListener extends
			GCHttpCallbackImpl<GCUploadTokenResponse> {

		@Override
		public void onSuccess(final GCUploadTokenResponse list) {
			Logger.d("Success " + list.toString());
			AssetsUploadRequest.this.list = list;
			resumeThread();
		}

		@Override
		public void onGeneralError(final int responseCode, final String message) {
			Logger.d("Error " + responseCode + " " + message);
			super.onGeneralError(responseCode, message);
			resumeThread();
		}
	}

	private final class UploadCompleteListener extends GCHttpCallbackImpl<Void> {

		@Override
		public void onSuccess(final Void responseData) {
			callback.onSuccess(list.getAssetCollection());
		}

		@Override
		public void onGeneralError(final int responseCode, final String message) {

			super.onGeneralError(responseCode, message);
			resumeThread();
		}
	}

	public static GCHttpRequest getToken(final Context context,
			final GCLocalAssetCollection localAssetCollection,
			final ArrayList<String> chuteIds,
			final GCHttpCallback<GCUploadTokenResponse> callback) {
		return getToken(context, localAssetCollection, chuteIds,
				new GCTokenObjectParser(), callback);
	}

	public static <T> GCHttpRequest getToken(final Context context,
			final GCLocalAssetCollection localAssetCollection,
			final ArrayList<String> chuteIds,
			final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new AssetsTokenRequest<T>(context, localAssetCollection,
				chuteIds, parser, callback);
	}

	public static <T> GCHttpRequest uploadComplete(final Context context,
			final String id, final GCHttpResponseParser<T> parser,
			final GCHttpCallback<T> callback) {
		return new AssetsUploadCompleteRequest<T>(context, id, parser, callback);
	}

	@Override
	public void executeAsync() {
		GCHttpRequestStore.getInstance(context).launchServiceIntent(this);
	}

}
