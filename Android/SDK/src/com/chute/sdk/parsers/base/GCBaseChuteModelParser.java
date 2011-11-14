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

import com.chute.sdk.model.GCChuteModel;

public class GCBaseChuteModelParser {
    @SuppressWarnings("unused")
    private static final String TAG = GCBaseChuteModelParser.class.getSimpleName();

    public static GCChuteModel parse(final JSONObject obj) throws JSONException {
	final GCChuteModel model = new GCChuteModel();
	model.setId(obj.getString("id"));
	model.setAssetsCount(obj.getString("assets_count"));
	model.setContributorsCount(obj.getString("contributors_count"));
	model.setCreatedAt(obj.getString("created_at"));
	model.setMembersCount(obj.getString("members_count"));
	model.setPermissionModerateComments(obj.getBoolean("moderate_comments") == true ? 1 : 0);
	model.setPermissionModerateMembers(obj.getBoolean("moderate_members") == true ? 1 : 0);
	model.setPermissionModeratePhotos(obj.getInt("moderate_photos"));
	model.setName(obj.getString("name"));
	model.setPermissionAddComments(obj.getInt("permission_add_comments"));
	model.setPermissionAddMembers(obj.getInt("permission_add_members"));
	model.setPermissionAddPhotos(obj.getInt("permission_add_photos"));
	model.setPermissionView(obj.getInt("permission_view"));
	model.setRecentCount(obj.getString("recent_count"));
	model.setRecentParcelId(obj.getString("recent_parcel_id"));
	model.setRecentThumbnailURL(obj.getString("recent_thumbnail"));
	model.setRecentUserId(obj.getString("recent_user_id"));
	model.setShortcut(obj.getString("shortcut"));
	model.setUpdatedAt(obj.getString("updated_at"));
	model.user = GCBaseUserModelParser.parse(obj.getJSONObject("user"));
	return model;
    }
}
