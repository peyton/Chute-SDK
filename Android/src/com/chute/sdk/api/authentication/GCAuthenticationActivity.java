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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chute.sdk.api.GCHttpCallback;
import com.chute.sdk.model.GCAccount;
import com.chute.sdk.model.GCAccount.GCAuthConstants;
import com.chute.sdk.model.GCHttpRequestParameters;
import com.chute.sdk.parsers.base.GCHttpResponseParser;
import com.chute.sdk.utils.GCUtils;

public class GCAuthenticationActivity extends AccountAuthenticatorActivity {

    private static final String TAG = GCAuthenticationActivity.class.getSimpleName();

    private WebView webViewAuthentication;

    private GCAuthenticationFactory authenticationFactory;

    private GCAuthConstants authConstants;

    private ProgressBar pb;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	webViewAuthentication = new WebView(this);
	webViewAuthentication.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		LayoutParams.FILL_PARENT));
	webViewAuthentication.setWebViewClient(new AuthWebViewClient());
	webViewAuthentication.getSettings().setJavaScriptEnabled(true);
	webViewAuthentication.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	/*
	webViewAuthentication.clearCache(true);
	WebSettings mWebSettings = webViewAuthentication.getSettings();
	mWebSettings.setSavePassword(false);
	mWebSettings.setSaveFormData(false);*/
	FrameLayout frameLayout = new FrameLayout(this);
	frameLayout.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
		LayoutParams.FILL_PARENT));
	pb = new ProgressBar(this);
	FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100, 100);
	layoutParams.gravity = Gravity.CENTER;
	pb.setLayoutParams(layoutParams);
	frameLayout.addView(webViewAuthentication);
	frameLayout.addView(pb);
	setContentView(frameLayout);
	authConstants = GCAccount.getInstance(getApplicationContext()).getAuthConstants();
	authenticationFactory = new GCAuthenticationFactory(authConstants);
	webViewAuthentication.loadUrl(authenticationFactory.getAuthenticationURL());
    }

    private class AuthWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    Log.e(TAG, "Override " + url);
	    /*   if (AuthenticationFactory.isRedirectUri(url)) {
	    Bundle params = Util.decodeUrl(url);
	    Toast.makeText(getApplicationContext(), params.getString("code"), Toast.LENGTH_LONG)
	    	.show();
	       }*/
	    return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
	    Log.e(TAG, "Page started " + url);

	    try {
		if (authenticationFactory.isRedirectUri(url)) {
		    Bundle params = GCUtils.decodeUrl(url);
		    String code = params.getString("code");
		    if (TextUtils.isEmpty(code)) {
			throw new RuntimeException();
		    }
		    view.stopLoading();
		    new GCAuthenticationToken<String>(getApplicationContext(), authConstants, code,
			    new GCHttpResponseParser<String>() {
				@Override
				public String parse(String responseBody) throws JSONException {
				    JSONObject obj;
				    obj = new JSONObject(responseBody);
				    GCAccount.getInstance(getApplicationContext()).saveApiKey(
					    obj.getString("access_token"), getApplicationContext());
				    return responseBody;
				}
			    }, new GCHttpCallback<String>() {

				@Override
				public void onSuccess(String responseData) {
				    setResult(Activity.RESULT_OK);
				    finish();
				}

				@Override
				public void onHttpException(GCHttpRequestParameters params,
					Throwable exception) {
				    Log.e(TAG, "Cannot get token, possible connection issues",
					    exception);
				    setResult(Activity.RESULT_CANCELED);
				    finish();
				}

				@Override
				public void onHttpError(int responseCode, String statusMessage) {
				    Log.e(TAG, "Response Not Valid, " + statusMessage + " Code: "
					    + responseCode);
				    setResult(Activity.RESULT_CANCELED);
				    finish();
				}

				@Override
				public void onParserException(int responseCode, Throwable exception) {
				    Log.e(TAG, "Cannot get token, possible connection issues",
					    exception);
				    setResult(Activity.RESULT_CANCELED);
				    finish();
				}
			    }).executeAsync();
		}
	    } catch (Exception e) {
		Log.w(TAG, "AUTHENTICATION FAILED", e);
		setResult(Activity.RESULT_CANCELED);
		finish();
	    }
	    pb.setVisibility(View.VISIBLE);
	    super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
	    Log.e(TAG, "Page finished " + url);
	    pb.setVisibility(View.GONE);
	    super.onPageFinished(view, url);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description,
		String failingUrl) {
	    Log.e(TAG, "Error " + failingUrl);

	    super.onReceivedError(view, errorCode, description, failingUrl);
	}
    }
}
