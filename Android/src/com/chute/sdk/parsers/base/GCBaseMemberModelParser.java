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
package com.chute.sdk.parsers.base;

import org.json.JSONException;
import org.json.JSONObject;

import com.chute.sdk.model.GCMemberModel;

public class GCBaseMemberModelParser {
	@SuppressWarnings("unused")
	private static final String TAG = GCBaseMemberModelParser.class
			.getSimpleName();

	public static GCMemberModel parse(final JSONObject obj)
			throws JSONException {
		GCMemberModel model = new GCMemberModel();
		model.setAssetCount(obj.optString("asset_count"));
		model.setAssetsPending(obj.getJSONObject("assets").getString("pending"));
		model.setAssetsPhotos(obj.getJSONObject("assets").getString("photos"));
		model.setUser(GCBaseUserModelParser.parse(obj));
		model.setEmail(obj.getString("email"));
		model.setLogin(obj.getString("login"));
		model.setNotificationComments(obj.getString("notification_comments"));
		model.setNotificationInvites(obj.getString("notification_invites"));
		model.setNotificationPhotos(obj.getString("notification_photos"));
		model.setStorageAvailable(obj.getJSONObject("storage").getString(
				"available"));
		model.setStorageCurrent(obj.getJSONObject("storage").getString(
				"current"));
		return model;
	}
}
