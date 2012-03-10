// Copyright (c) 2011, Chute Corporation. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification,
// are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above copyright notice, this
// list of conditions and the following disclaimer.
// * Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// * Neither the name of the Chute Corporation nor the names
// of its contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
// IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
// INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
// BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
// LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
// OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
// OF THE POSSIBILITY OF SUCH DAMAGE.
//
package com.chute.sdk.model;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;

import com.chute.sdk.api.authentication.GCAuthenticationActivity;
import com.chute.sdk.api.authentication.GCAuthenticationFactory.AccountType;
import com.chute.sdk.utils.GCConstants;

public class GCAccountStore {

    public static final int AUTHENTICATION_REQUEST_CODE = 423532;

    private static final String TAG = GCAccountStore.class.getSimpleName();

    private String username = "";
    private String password = "";

    private static GCAccountStore gcAccount;

    private final ArrayList<NameValuePair> headers;
    private final Context context;

    /**
     * <b> this object will be using a Reference to the application context via
     * getApplicationContext() NOT the Activity context</b> Recomended to be initialized at the
     * application startup or by initializing in your own class extending application
     * <p>
     * <b> Dont forget to set the username and password on first init </b>
     * 
     * @param context it is only used into the first time init of the singleton, its reference is
     *        stored so the singleton is alive during the application lifecycle.
     * @return
     */
    public static synchronized GCAccountStore getInstance(Context context) {
        if (gcAccount == null) {
            gcAccount = new GCAccountStore(context.getApplicationContext());
        }
        return gcAccount;
    }

    private GCAccountStore(final Context context) {
        this.context = context;
        headers = new ArrayList<NameValuePair>();
        GCAuthConstants.ACCOUNT_TYPE = context.getPackageName();
        getAccountInfo(context);
        try {
            String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            if (android_id == null || android_id.contentEquals("")) {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                android_id = "DEVICE_MAC:" + wm.getConnectionInfo().getMacAddress();
            }
            String android_name = android.os.Build.MODEL;
            addHeader("x-device-name", android_name + "");
            addHeader("x-device-identifier", android_id);
            saveDeviceId(android_id, context);
            addHeader("x-device-os", "Android");
            addHeader("x-device-version", android.os.Build.VERSION.RELEASE);
        } catch (Exception e) {
            Log.w(TAG, "", e);
        }

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void addHeader(String name, String value) {
        headers.add(new BasicNameValuePair(name, value));
    }

    public ArrayList<NameValuePair> getHeaders() {
        return headers;
    }

    private static final String API_KEY = "api_key";
    private static final String DEVICE_ID = "device_id";
    private static final String USER_ID = "user_id";

    private GCAuthConstants authConstants;

    private Bundle buildAccount(final Context context, String username, String password) {
        Bundle result = null;
        AccountManager am = AccountManager.get(context);
        if (GCConstants.DEBUG) {
            Log.e(TAG, GCAuthConstants.ACCOUNT_TYPE);
        }
        Account[] chuteAccounts = am.getAccountsByType(GCAuthConstants.ACCOUNT_TYPE);
        if (chuteAccounts.length > 0) {
            if (GCConstants.DEBUG) {
                Log.e("ACCOUNTS", String.valueOf(chuteAccounts.length));
            }
            for (Account ac : chuteAccounts) {
                if (!username.contentEquals(ac.name)) {
                    am.removeAccount(ac, null, null);
                    Account account = new Account(username, GCAuthConstants.ACCOUNT_TYPE);
                    if (am.addAccountExplicitly(account, password, null)) {
                        result = new Bundle();
                        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                        return result;
                    } else {
                        return null;
                    }
                } else if (!am.getPassword(ac).contentEquals(password)) {
                    am.setPassword(ac, password);
                }
            }
        } else {
            Account account = new Account(username, GCAuthConstants.ACCOUNT_TYPE);
            if (am.addAccountExplicitly(account, password, null)) {
                result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                return result;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Usage:
     * 
     * <pre>
     * Bundle b = addAccount(uInfo.getEmail(), uInfo.getApiKey());
     * if (b != null) {
     *     setAccountAuthenticatorResult(b);
     * }
     * </pre>
     * 
     * Use it with an Extended AccountAuthenticatorActivity to provide a way to add an account via
     * the accounts and sync screen. using account requires manifest permissions:
     * 
     * <pre>
     *  uses-permission android:name="android.permission.GET_ACCOUNTS"
     *  uses-permission android:name="android.permission.MANAGE_ACCOUNTS"
     *  uses-permission android:name="android.permission.AUTHENTICATE_ACCOUNTS"
     * </pre>
     * 
     * @param context
     * @param username The account username
     * @param password the app token used to access the api's
     * @return an Authentication Bundle
     */
    public Bundle addAccount(Activity activity, String username, String password) {
        final Bundle bundle = buildAccount(activity, username, password);
        if (bundle != null) {
            this.setUsername(username);
            this.setPassword(password);
        }
        return bundle;
    }

    private void getAccountInfo(Context context) {

        AccountManager am = AccountManager.get(context);
        Account[] chuteAccounts = am.getAccountsByType(GCAuthConstants.ACCOUNT_TYPE);
        if (chuteAccounts.length > 0) {
            this.setUsername(chuteAccounts[0].name);
            this.setPassword(am.getPassword(chuteAccounts[0]));
            return;
        }
        // IF there is no account added get the token from shared preferences
        String apiKey = restoreApiKey(context);
        if (!TextUtils.isEmpty(apiKey)) {
            this.setUsername("ChuteDefaultUsername");
            this.setPassword(apiKey);
            return;
        }
        // Set a manual token for testing
        // this.setUsername("ChuteDefaultUsername");
        // this.setPassword("46e580a90085912ed11c565084f1f2465f28630bd58fa80cc98432f3078fc5ac");
    }

    /**
     * Use as an alternative for saving the token to accounts (Note that using the account manager
     * is a preferred and safer method)
     * 
     * @param apiKey the token aqured from chute auth
     * @param context
     * @return if the save was successful
     */
    public boolean saveApiKey(String apiKey, Context context) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(API_KEY, apiKey);
        boolean commit = editor.commit();
        getAccountInfo(context);
        return commit;
    }

    public boolean isTokenValid() {
        return !TextUtils.isEmpty(password);
    }

    public boolean clearAuth() {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(API_KEY);
        boolean commit = editor.commit();
        AccountManager am = AccountManager.get(context);
        Account[] chuteAccounts = am.getAccountsByType(GCAuthConstants.ACCOUNT_TYPE);
        for (Account ac : chuteAccounts) {
            am.removeAccount(ac, null, null);
        }
        return commit;
    }

    public String restoreApiKey(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getString(API_KEY, "");
    }

    public boolean saveDeviceId(String deviceId, Context context) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(DEVICE_ID, deviceId);
        return editor.commit();
    }

    public String restoreDeviceId(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getString(DEVICE_ID, "");
    }

    public boolean saveUserId(String userId, Context context) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(USER_ID, userId);
        return editor.commit();
    }

    public String restoreUserId(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getString(USER_ID, "");
    }

    /**
     * <p>
     * Use {@link #AUTHENTICATION_REQUEST_CODE} inside the onActivityResult to check the request
     * code
     * <p>
     * Use {@link Activity#RESULT_OK} for the result code if the auth was successful
     * 
     * <pre>
     * <b> for errors use the following constants for the result code </b>
     * Use {@link GCAuthenticationActivity#CODE_HTTP_EXCEPTION}} - for connection problems
     * Use {@link GCAuthenticationActivity#CODE_HTTP_ERROR}} - for server issue, see logcat for detailed error
     * Use {@link GCAuthenticationActivity#CODE_PARSER_EXCEPTION}} - for result parsing errors, see logcat for details
     * </pre>
     * 
     * @param activity
     * @param accountType
     * @param scope
     * @param redirectUri
     * @param clientId
     * @param clientSecret
     */
    public void startAuthenticationActivity(Activity activity,
            AccountType accountType,
            String scope,
            String redirectUri,
            String clientId,
            String clientSecret) {
        Intent intent = new Intent(activity, GCAuthenticationActivity.class);
        authConstants = new GCAuthConstants(accountType, scope, redirectUri, clientId, clientSecret);
        activity.startActivityForResult(intent, AUTHENTICATION_REQUEST_CODE);
    }

    public GCAuthConstants getAuthConstants() {
        return authConstants;
    }

    public static class GCAuthConstants {

        /**
         * Account type string.
         */
        public static String ACCOUNT_TYPE;

        public static final String PARAM_PASSWORD = "password";
        public static final String PARAM_USERNAME = "username";

        public AccountType accountType;
        public String scope;
        public String redirectUri;
        public String clientId;
        public String clientSecret;

        public GCAuthConstants(AccountType accountType,
                String scope,
                String redirectUri,
                String clientId,
                String clientSecret) {
            super();
            this.accountType = accountType;
            this.scope = scope;
            this.redirectUri = redirectUri;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

    }

}
