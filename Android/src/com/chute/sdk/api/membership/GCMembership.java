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
package com.chute.sdk.api.membership;

import java.util.ArrayList;

import android.content.Context;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.api.GCHttpRequest;
import com.chute.sdk.model.GCChuteModel;
import com.chute.sdk.parsers.GCMembershipJoinParser;
import com.chute.sdk.parsers.base.GCHttpResponseParser;

/**
 * The {@link GCMembership} class is a helper class which contains methods for
 * joining chutes and inviting members.
 * 
 */
public class GCMembership {

    @SuppressWarnings("unused")
    private static final String TAG = GCMembership.class.getSimpleName();

    /**
     * A private no-args default constructor.
     */
    private GCMembership() {
    }

    /**
	 * Method that defaults to the generic method {@see #join(Context, String,
	 * String, GCHttpResponseParser, GCHttpCallback)}. This method uses
	 * {@link GCMembershipJoinParser} which has String as a return type if the
	 * callback is successful.
     * 
     * @param context
     *            The application context.
     * @param chuteId
     *            {@link GCChuteModel} ID, representing the chute to be joined.
     * @param password
	 *            String variable representing the user password.
     *            specific chute.
     * @param callback
	 *            Instance of {@link GCHttpCallback} interface. If successful,
	 *            the callback returns a String.
     * @return {@link #join(Context, String, String, GCHttpResponseParser, GCHttpCallback)}
     *         method.
     */
    public static GCHttpRequest join(final Context context, final String chuteId, String password,
	    final GCHttpCallback<String> callback) {
	return join(context, chuteId, password, new GCMembershipJoinParser(), callback);
    }

    /**
	 * Method used for joining a chute. It returns a JSON object containing a
	 * String using the following parameters: context, string value representing
	 * the chute ID, string value representing the password, and the given
	 * callback and parser.
	 * 
     * 
     * @param context
     *            The application context.
     * @param chuteId
     *            {@link GCChuteModel} ID, representing the chute to be joined.
     * @param password
     *            String variable representing the user password.
     * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface. You can
	 *            add a custom parser or use the parser provided here {@see
	 *            #join(Context, String, String, GCHttpCallback)}.
     * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesMembershipsJoinRequest}, class that
     *         implements {@link GCHttpRequest}.
     */
    public static <T> GCHttpRequest join(final Context context, String chuteId, String password,
	    final GCHttpResponseParser<T> parser, final GCHttpCallback<T> callback) {
	return new ChutesMembershipsJoinRequest<T>(context, chuteId, password, parser, callback);
    }

    /**
	 * Method that defaults to the generic method {@see #invite(Context, String,
	 * ArrayList, ArrayList, ArrayList, GCHttpResponseParser, GCHttpCallback)}.
     * 
     * @param context
     *            The application context.
     * @param chuteId
     *            {@link GCChuteModel} ID, representing the chute to be invited.
     * @param emails
     *            ArrayList of members emails.
     * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface.
     * @param callback
	 *            Instance of {@link GCHttpCallback} interface.
     * @return {@link #invite(Context, String, ArrayList, ArrayList, ArrayList, GCHttpResponseParser, GCHttpCallback)}
     *         method.
     */
    public static <T> GCHttpRequest invite(final Context context, String chuteId,
	    ArrayList<String> emails, final GCHttpResponseParser<T> parser,
	    final GCHttpCallback<T> callback) {
	return invite(context, chuteId, emails, null, null, parser, callback);
    }

    /**
	 * Method used for chutes membership invitation. It returns a JSON object
	 * using the following parameters: context, string value representing the
	 * chute ID, array of member Emails, array of Facebook IDs, array of chute
	 * user IDs, and the given callback and parser.
     * 
     * @param context
     *            The application context.
     * @param chuteId
     *            {@link GCChuteModel} ID, representing the chute to be invited.
     * @param emails
	 *            ArrayList of members Emails.
     * @param facebookIds
	 *            ArrayList of members FacebookIds.
     * @param chuteUserIds
     *            ArrayList of chute user IDs.
     * @param parser
	 *            Instance of {@link GCHttpResponseParser} interface.
     * @param callback
	 *            Instance of {@link GCHttpCallback} interface. According to the
	 *            parser, the callback should have the same return type.
	 * @return Instance of {@link ChutesMembershipsInviteRequest}, class that
     *         implements {@link GCHttpRequest}.
     */
    public static <T> GCHttpRequest invite(final Context context, String chuteId,
	    ArrayList<String> emails, ArrayList<String> facebookIds,
	    ArrayList<String> chuteUserIds, final GCHttpResponseParser<T> parser,
	    final GCHttpCallback<T> callback) {
	return new ChutesMembershipsInviteRequest<T>(context, chuteId, emails, facebookIds,
		chuteUserIds, parser, callback);
    }
}
