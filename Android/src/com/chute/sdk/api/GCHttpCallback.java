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
package com.chute.sdk.api;

import com.chute.sdk.model.GCHttpRequestParameters;

/**
 * The {@link GCHttpCallback} interface is used for HTTP request notification.
 * It contains methods that describe the error if the callback fails and return
 * response data if the callback succeeds.
 * 
 * @param <T>
 *            Parameter that indicates which object the callback returns. It can
 *            be of any type.
 */
public interface GCHttpCallback<T> {

	/**
	 * This method shows that the callback successfully finished. It contains
	 * response data which represents the return type of the callback.
	 * 
	 * @param responseData
	 *            It can be of any type.
	 */
	public void onSuccess(T responseData);

	/**
	 * This method shows that the callback has failed due to Internet connection
	 * issue.
	 * 
	 * @param params
	 *            Instance of {@link GCHttpRequestParameters} class.
	 * @param exception
	 *            The exception that the callback throws to indicate what went
	 *            wrong.
	 */
	public void onHttpException(GCHttpRequestParameters params,
			Throwable exception);

	/**
	 * This method shows that the callback has failed due to server issue.
	 * 
	 * @param responseCode
	 *            The response code indicating which server problem has
	 *            occurred.
	 * @param statusMessage
	 *            The message describing the server issue.
	 */
	public void onHttpError(int responseCode, String statusMessage);

	/**
	 * This method shows that the callback has failed due to invalid response
	 * format.
	 * 
	 * @param responseCode
	 *            The response code indicating which invalid response format has
	 *            occurred.
	 * @param exception
	 *            The exception that the callback throws to indicate what went
	 *            wrong.
	 */
	public void onParserException(int responseCode, Throwable exception);
}
