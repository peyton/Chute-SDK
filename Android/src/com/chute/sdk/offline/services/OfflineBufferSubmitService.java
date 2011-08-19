package com.chute.sdk.offline.services;

import android.app.IntentService;
import android.content.Intent;

import com.chute.sdk.offline.CreateChuteBufferUpload;
import com.chute.sdk.offline.CreateCommentBufferUpload;
import com.chute.sdk.offline.CreateHeartBufferUpload;
import com.chute.sdk.offline.CreateParcelBufferUpload;

public class OfflineBufferSubmitService extends IntentService {
    public OfflineBufferSubmitService() {
	super(TAG);
    }

    @SuppressWarnings("unused")
    private static final String TAG = OfflineBufferSubmitService.class.getSimpleName();

    @Override
    protected void onHandleIntent(Intent intent) {
	new CreateChuteBufferUpload(getApplicationContext());
	new CreateParcelBufferUpload(getApplicationContext());
	new CreateCommentBufferUpload(getApplicationContext());
	new CreateHeartBufferUpload(getApplicationContext());
    }
}
