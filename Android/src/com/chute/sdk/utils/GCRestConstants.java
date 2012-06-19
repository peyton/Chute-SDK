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
package com.chute.sdk.utils;

/** @author DArkO */
public class GCRestConstants {

	// public static final String BASE_URL = "http://accounts.getchute.com";
	public static final String BASE_URL = "https://api.getchute.com";
	public static final String BASE_AUTH_URL = "https://getchute.com";

	// Accounts
	public static final String URL_ACCOUNTS = BASE_URL + "/v1/accounts";
	public static final String URL_ACCOUNTS_UNMUTE = BASE_URL
			+ "/v1/accounts/%s/unmute";
	public static final String URL_ACCOUNTS_MUTE = BASE_URL
			+ "/v1/accounts/%s/mute";
	public static final String URL_ACCOUNTS_AUTH = BASE_URL + "/v1/auth";

	public static final String URL_AUTHENTICATION_FACEBOOK = BASE_AUTH_URL
			+ "/oauth/facebook";
	public static final String URL_AUTHENTICATION_EVERNOTE = BASE_AUTH_URL
			+ "/oauth/evernote";
	public static final String URL_AUTHENTICATION_CHUTE = BASE_AUTH_URL
			+ "/oauth/chute";
	public static final String URL_AUTHENTICATION_TWITTER = BASE_AUTH_URL
			+ "/oauth/twitter";
	public static final String URL_AUTHENTICATION_FOURSQUARE = BASE_AUTH_URL
			+ "/oauth/foursquare";
	public static final String URL_AUTHENTICATION_FLICKR = BASE_AUTH_URL
			+ "/oauth/flickr";
	public static final String URL_AUTHENTICATION_INSTAGRAM = BASE_AUTH_URL
			+ "/oauth/instagram";
	public static final String URL_AUTHENTICATION_PICASA = BASE_AUTH_URL
			+ "/oauth/google";
	public static final String URL_AUTHENTICATION_TOKEN = BASE_AUTH_URL
			+ "/oauth/access_token";

	public static final String URL_ACCOUNT_OBJECT = BASE_URL
			+ "/v1/accounts/%s/objects";
	public static final String URL_ACCOUNT_OBJECT_MEDIA = BASE_URL
			+ "/v1/accounts/%s/objects/%s/media";

	// App Metadata
	public static final String URL_META_APP = BASE_URL + "/v1/app/meta";
	public static final String URL_META_APP_KEY = BASE_URL + "/v1/app/meta/%s";

	// Assets
	public static final String URL_ASSET_GET = BASE_URL + "/v1/assets/%s";
	public static final String URL_ASSET_VERIFY = BASE_URL
			+ "/v1/assets/verify";
	public static final String URL_ASSET_REMOVE = BASE_URL
			+ "/v1/assets/remove";
	public static final String URL_ASSET_REMOVE_MULTIPLE = BASE_URL
			+ "/v1/assets/%s/remove";
	public static final String URL_ASSET_INIT = BASE_URL + "/v1/assets/%s/init";
	public static final String URL_ASSET_DELETE = BASE_URL + "/v1/assets/%s";

	// Assets Metadata
	public static final String URL_META_ASSETS = BASE_URL
			+ "/v1/assets/%s/meta";
	public static final String URL_META_ASSETS_KEY = BASE_URL
			+ "/v1/assets/%s/meta/%s";

	// Bundles
	public static final String URL_BUNDLES_GET = BASE_URL + "/v1/bundles/%s";
	public static final String URL_BUNDLES_CREATE = BASE_URL + "/v1/bundles";
	public static final String URL_BUNDLES_ADD = BASE_URL
			+ "/v1/bundles/%s/add";
	public static final String URL_BUNDLES_REMOVE = BASE_URL
			+ "/v1/bundles/%s/remove";

	// Chutes
	public static final String URL_CHUTES_GET = BASE_URL + "/v1/chutes";
	public static final String URL_CHUTES_CREATE = BASE_URL + "/v1/chutes";
	public static final String URL_CHUTES_GET_SPECIFIC = BASE_URL
			+ "/v1/chutes/%s/assets";
	public static final String URL_CHUTES_UPDATE = BASE_URL + "/v1/chutes/%s";
	public static final String URL_CHUTES_DELETE = BASE_URL
			+ "/v1/chutes/%s/assets";
	public static final String URL_CHUTES_SEARCH = BASE_URL
			+ "/v1/chutes/search?url=%s";
	public static final String URL_CHUTES_ALL = BASE_URL + "/v1/%s/chutes/all";

	// Chute Assets
	public static final String URL_CHUTE_ASSETS_ADD = BASE_URL
			+ "/v1/chutes/%s/assets/add";
	public static final String URL_CHUTE_ASSETS_REMOVE = BASE_URL
			+ "/v1/chutes/%s/assets/remove";
	public static final String URL_CHUTE_ASSETS_DELETE = BASE_URL
			+ "/v1/chutes/%s/assets/%s/remove";

	// Chute Memberships
	public static final String URL_CHUTE_MEMBERSHIPS_REQUEST = BASE_URL
			+ "/v1/chutes/%s/request";
	public static final String URL_CHUTE_MEMBERSHIPS_INVITE = BASE_URL
			+ "/v1/chutes/%s/invite";
	public static final String URL_CHUTE_MEMBERSHIPS_JOIN = BASE_URL
			+ "/v1/chutes/%s/join";
	public static final String URL_CHUTE_MEMBERSHIPS_LEAVE = BASE_URL
			+ "/v1/chutes/%s/leave";
	public static final String URL_CHUTE_MEMBERSHIPS_DELETE = BASE_URL
			+ "/v1/memberships/%s";

	// Chute Metadata
	public static final String URL_META_CHUTES = BASE_URL
			+ "/v1/chutes/%s/meta";
	public static final String URL_META_CHUTES_KEY = BASE_URL
			+ "/v1/chutes/%s/meta/%s";

	// Chutes Resources
	public static final String URL_CHUTE_RESOURCES_ASSETS = BASE_URL
			+ "/v1/chutes/%s/assets";
	public static final String URL_CHUTE_RESOURCES_MEMBERS = BASE_URL
			+ "/v1/chutes/%s/members";
	public static final String URL_CHUTE_RESOURCES_CONTRIBUTORS = BASE_URL
			+ "/v1/chutes/%s/contributors";

	// Comments
	public static final String URL_COMMENTS_GET = BASE_URL
			+ "/v1/chutes/%s/assets/%s/comments";
	public static final String URL_COMMENTS_POST = BASE_URL
			+ "/v1/chutes/%s/assets/%s/comments";
	public static final String URL_COMMENTS_DELETE = BASE_URL
			+ "/v1/comments/%s";

	// Devices
	public static final String URL_DEVICES_QUEUE = BASE_URL
			+ "/v1/devices/%s/queue";
	public static final String URL_DEVICES_UNMUTE = BASE_URL
			+ "/v1/devices/%s/unmute";
	public static final String URL_DEVICES_MUTE = BASE_URL
			+ "/v1/devices/%s/mute";

	// Hearts
	public static final String URL_ASSETS_HEART = BASE_URL
			+ "/v1/assets/%s/heart";
	public static final String URL_ASSETS_UNHEART = BASE_URL
			+ "/v1/assets/%s/unheart";

	// Inbox
	public static final String URL_INBOX = BASE_URL + "/v1/inbox";
	public static final String URL_INBOX_INVITES = BASE_URL
			+ "/v1/inbox/invites";
	public static final String URL_INBOX_PARCELS_ALL = BASE_URL
			+ "/v1/inbox/parcels";
	public static final String URL_INBOX_PARCELS_SINGLE = BASE_URL
			+ "/v1/inbox/parcels/%s";

	// Invites
	public static final String URL_INVITES_APPROVE = BASE_URL
			+ "/v1/invites/%s/approve";
	public static final String URL_INVITES_DELETE = BASE_URL + "/v1/invites/%s";

	// Meta Search
	public static final String URL_META_SEARCH = BASE_URL + "/v1/meta/%s";
	public static final String URL_META_SEARCH_ASSETS = BASE_URL
			+ "/v1/meta/assets/%s";
	public static final String URL_META_SEARCH_CHUTES = BASE_URL
			+ "/v1/meta/chutes/%s";
	public static final String URL_META_SEARCH_PARCELS = BASE_URL
			+ "/v1/meta/parcels/%s";
	public static final String URL_META_SEARCH_USERS = BASE_URL
			+ "/v1/meta/users/%s";

	// Parcels
	public static final String URL_PARCELS_GET_ALL = BASE_URL + "/v1/parcels";
	public static final String URL_PARCELS_CREATE = BASE_URL + "/v1/parcels";
	public static final String URL_PARCELS_GET_CHUTE_PARCEL = BASE_URL
			+ "/v1/chutes/%s/parcels/%s";
	public static final String URL_PARCELS_GET_PARCEL = BASE_URL
			+ "/v1/parcels/%s";
	public static final String URL_PARCELS_GET_CHUTE_PARCEL_ALL = BASE_URL
			+ "/v1/chutes/%s/parcels";
	public static final String URL_PARCELS_COMPLETE = BASE_URL
			+ "/v1/parcels/%s/complete";

	// Parcel Metadata
	public static final String URL_META_PARCELS = BASE_URL
			+ "/v1/parcels/%s/meta";
	public static final String URL_META_PARCELS_KEY = BASE_URL
			+ "/v1/parcels/%s/meta/%s";

	// Parcel Resources
	public static final String URL_PARCEL_RESOURCES = BASE_URL
			+ "/v1/parcels/%s/assets";

	// Uploads
	public static final String URL_UPLOADS_TOKEN = BASE_URL + "/v2/uploads";
	public static final String URL_UPLOADS_COMPLETE = BASE_URL + "/v2/uploads/%s/complete";

	// User
	public static final String URL_USER = BASE_URL + "/v1/%s";

	// User Metadata
	public static final String URL_META_USER = BASE_URL + "/v1/%s/meta";
	public static final String URL_META_USER_KEY = BASE_URL + "/v1/%s/meta/%s";

	// User Resources
	public static final String URL_USER_ACCOUNTS = BASE_URL + "/v1/%s/accounts";
	public static final String URL_USER_ASSETS = BASE_URL + "/v1/%s/assets";
	public static final String URL_USER_BUNDLES = BASE_URL + "/v1/%s/bundles";
	public static final String URL_USER_CHUTES = BASE_URL + "/v1/%s/chutes";
	public static final String URL_USER_CHUTES_TREE = BASE_URL
			+ "/v1/%s/chutes/tree";
	public static final String URL_USER_CHUTES_CHILDREN = BASE_URL
			+ "/v1/%s/chutes/%s/children";
	public static final String URL_USER_DEVICES = BASE_URL + "/v1/%s/devices";
	public static final String URL_USER_HEARTS = BASE_URL + "/v1/%s/hearts";
	public static final String URL_USER_PARCELS = BASE_URL + "/v1/%s/parcels";
	public static final String URL_USER_NOTICES = BASE_URL + "/v1/%s/notices";

}
