package com.chute.sdk.offline.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chute.examples.kitchensink.utils.ChuteAppUtils;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {
    @SuppressWarnings("unused")
    private static final String TAG = ConnectivityBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
	Log.e(TAG, "Connectivity Change");
	if (ChuteAppUtils.isOnline(context)) {
	    Intent serviceIntent = new Intent(context, OfflineBufferSubmitService.class);
	    context.startService(serviceIntent);
	}
    }
}