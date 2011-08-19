package com.chute.examples.kitchensink.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.utils.ChuteAppUtils;
import com.chute.examples.kitchensink.utils.ChuteAuthenticationFactory;
import com.chute.examples.kitchensink.utils.ChuteAuthenticationFactory.AccountType;
import com.chute.sdk.api.authorization.Authorization;

public class AuthenticationActivity extends Activity implements OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = AuthenticationActivity.class.getSimpleName();

    private WebView webViewAuthentication;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.authentication_activity);
	Button facebook = (Button) findViewById(R.id.buttonFacebook);
	facebook.setOnClickListener(this);
	Button evernote = (Button) findViewById(R.id.buttonEvernote);
	evernote.setOnClickListener(this);
	Button chute = (Button) findViewById(R.id.buttonChute);
	chute.setOnClickListener(this);
	webViewAuthentication = (WebView) findViewById(R.id.webViewAuthentication);
	webViewAuthentication.setWebViewClient(new AuthWebViewClient());
	webViewAuthentication.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.buttonFacebook:
	    String authenticationURL = ChuteAuthenticationFactory
		    .getAuthenticationURL(AccountType.FACEBOOK);
	    Log.e(TAG, authenticationURL);
	    webViewAuthentication.loadUrl(authenticationURL);
	    break;
	case R.id.buttonEvernote:
	    webViewAuthentication.loadUrl(ChuteAuthenticationFactory
		    .getAuthenticationURL(AccountType.EVERNOTE));
	    break;
	case R.id.buttonChute:
	    webViewAuthentication.loadUrl(ChuteAuthenticationFactory
		    .getAuthenticationURL(AccountType.CHUTE));
	    break;
	}
    }

    private class AuthWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    Log.e(TAG, "Override " + url);
	    return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
	    Log.e(TAG, "Page started " + url);
	    try {
		if (ChuteAuthenticationFactory.isRedirectUri(url)) {
		    Bundle params = ChuteAppUtils.decodeUrl(url);
		    new Authorization(getApplicationContext(), params.getString("code")).execute();
		    view.stopLoading();
		}
	    } catch (Exception e) {
		Log.w(TAG, "", e);
	    }
	    super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
	    Log.e(TAG, "Page finished " + url);
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
