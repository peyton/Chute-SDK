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

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.AccountAuthenticatorActivity;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.model.GCAccountStore;
import com.chute.sdk.model.GCAccountStore.GCAuthConstants;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCConstants;
import com.chute.sdk.utils.GCUtils;

public class GCAuthenticationActivity extends AccountAuthenticatorActivity {

    private static final String TAG = GCAuthenticationActivity.class.getSimpleName();
    public static final int CODE_HTTP_EXCEPTION = 4;
    public static final int CODE_HTTP_ERROR = 5;
    public static final int CODE_PARSER_EXCEPTION = 6;

    private WebView webViewAuthentication;

    private GCAuthenticationFactory authenticationFactory;

    private GCAuthConstants authConstants;

    private ProgressBar pb;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	webViewAuthentication = new WebView(this);
	webViewAuthentication.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		LayoutParams.FILL_PARENT));
	webViewAuthentication.setWebViewClient(new AuthWebViewClient());
	webViewAuthentication.getSettings().setJavaScriptEnabled(true);
	webViewAuthentication.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

	webViewAuthentication.clearCache(true);
	final WebSettings mWebSettings = webViewAuthentication.getSettings();
	mWebSettings.setSavePassword(false);
	mWebSettings.setSaveFormData(false);
	final FrameLayout frameLayout = new FrameLayout(this);
	frameLayout.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
		LayoutParams.FILL_PARENT));
	pb = new ProgressBar(this);
	final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100, 100);
	layoutParams.gravity = Gravity.CENTER;
	pb.setLayoutParams(layoutParams);
	frameLayout.addView(webViewAuthentication);
	frameLayout.addView(pb);
	setContentView(frameLayout);
	authConstants = GCAccountStore.getInstance(getApplicationContext()).getAuthConstants();
	authenticationFactory = new GCAuthenticationFactory(authConstants);
	webViewAuthentication.loadUrl(authenticationFactory.getAuthenticationURL());
    }

    private final class GCAuthenticationCodeCallback implements GCHttpCallback<String> {

	@Override
	public void onSuccess(final String responseData) {
	    setResult(Activity.RESULT_OK);
	    finish();
	}

	@Override
	public void onHttpException(final GCHttpRequestParameters params, final Throwable exception) {
	    if (GCConstants.DEBUG) {
		Log.e(TAG, "Cannot get token, possible connection issues", exception);
	    }
	    setResult(CODE_HTTP_EXCEPTION);
	    finish();
	}

	@Override
	public void onHttpError(final int responseCode, final String statusMessage) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG, "Response Not Valid, " + statusMessage + " Code: " + responseCode);
	    }
	    setResult(CODE_HTTP_ERROR);
	    finish();
	}

	@Override
	public void onParserException(final int responseCode, final Throwable exception) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG, "Cannot get token, possible connection issues", exception);
	    }
	    setResult(CODE_PARSER_EXCEPTION);
	    finish();
	}
    }

    /** @author darko.grozdanovski */
    private final class GCAuthenticationResponseParser implements GCHttpResponseParser<String> {
	@Override
	public String parse(final String responseBody) throws JSONException {
	    final JSONObject obj = new JSONObject(responseBody);
	    GCAccountStore.getInstance(getApplicationContext()).saveApiKey(
		    obj.getString("access_token"), getApplicationContext());
	    return responseBody;
	}
    }

    private class AuthWebViewClient extends WebViewClient {
	/** @author darko.grozdanovski */

	@Override
	public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
	    Log.e(TAG, "Override " + url);
	    return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG, "Page started " + url);
	    }

	    try {
		if (authenticationFactory.isRedirectUri(url)) {
		    final Bundle params = GCUtils.decodeUrl(url);
		    final String code = params.getString("code");
		    if (TextUtils.isEmpty(code)) {
			setResult(CODE_HTTP_ERROR);
			finish();
		    }
		    view.stopLoading();
		    new GCAuthenticationToken<String>(getApplicationContext(), authConstants, code,
			    new GCAuthenticationResponseParser(),
			    new GCAuthenticationCodeCallback()).executeAsync();
		}
	    } catch (final Exception e) {
		if (GCConstants.DEBUG) {
		    Log.d(TAG, "AUTHENTICATION FAILED", e);
		}
		setResult(CODE_HTTP_EXCEPTION);
		finish();
	    } finally {
		pb.setVisibility(View.VISIBLE);
	    }
	    super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(final WebView view, final String url) {
	    if (GCConstants.DEBUG) {
		Log.e(TAG, "Page finished " + url);
	    }
	    pb.setVisibility(View.GONE);
	    super.onPageFinished(view, url);
	}

	@Override
	public void onReceivedError(final WebView view, final int errorCode,
		final String description, final String failingUrl) {
	    if (GCConstants.DEBUG) {
		Log.e(TAG, "Error " + failingUrl);
	    }
	    super.onReceivedError(view, errorCode, description, failingUrl);
	}
    }
}
