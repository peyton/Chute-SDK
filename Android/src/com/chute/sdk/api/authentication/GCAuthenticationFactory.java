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
package com.chute.sdk.api.authentication;

import com.chute.sdk.model.GCAccountStore.GCAuthConstants;
import com.chute.sdk.utils.GCRestConstants;

public class GCAuthenticationFactory {
	@SuppressWarnings("unused")
	private static final String TAG = GCAuthenticationFactory.class
			.getSimpleName();
	private final GCAuthConstants authConstants;

	public enum AccountType {
		FACEBOOK("facebook"), EVERNOTE("evernote"), CHUTE("chute"), TWITTER(
				"twitter"), FOURSQUARE("foursquare"), PICASA("google"), FLICKR(
				"flickr"), INSTAGRAM("instagram");

		private final String name;

		private AccountType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		};

		public String getName() {
			return name;
		}
	}

	public GCAuthenticationFactory(GCAuthConstants authConstants) {
		this.authConstants = authConstants;
	}

	public String getAuthenticationURL() {
		StringBuilder stringBuilder;
		switch (authConstants.accountType) {
		case FACEBOOK:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_FACEBOOK);
			break;
		case EVERNOTE:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_EVERNOTE);
			break;
		case CHUTE:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_CHUTE);
			break;
		case TWITTER:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_TWITTER);
			break;
		case FOURSQUARE:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_FOURSQUARE);
			break;
		case PICASA:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_PICASA);
			break;
		case FLICKR:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_FLICKR);
			break;
		case INSTAGRAM:
			stringBuilder = new StringBuilder(
					GCRestConstants.URL_AUTHENTICATION_INSTAGRAM);
			break;
		default:
			throw new RuntimeException("Not a valid account type");
		}
		stringBuilder.append("?");
		stringBuilder.append("scope=" + authConstants.scope);
		stringBuilder.append("&");
		stringBuilder.append("type=web_server");
		stringBuilder.append("&");
		stringBuilder.append("response_type=code");
		stringBuilder.append("&");
		stringBuilder.append("client_id=" + authConstants.clientId);
		stringBuilder.append("&");
		stringBuilder.append("redirect_uri=" + authConstants.redirectUri);
		return stringBuilder.toString();
	}

	public String getRedirectUri() {
		return authConstants.redirectUri;
	}

	public boolean isRedirectUri(String url) {
		return url.startsWith(authConstants.redirectUri);
	}
}
