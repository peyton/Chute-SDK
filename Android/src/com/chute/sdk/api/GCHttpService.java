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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.chute.sdk.api.asset.AssetsUploadRequest;
import com.chute.sdk.utils.AsyncTaskEx;
import com.chute.sdk.utils.GCConstants;

public class GCHttpService extends Service {
    public static final String TAG = GCHttpService.class.getSimpleName();
    private GCHttpRequestStore instance;
    private WakeLock wl;

    public GCHttpService() {
	super();
    }

    @Override
    public void onCreate() {
	try {
	    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	    wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PartialWakeLock");
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}
	super.onCreate();
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	onHandleIntent(intent);
	return START_REDELIVER_INTENT;
    }

    protected void onHandleIntent(Intent intent) {
	final Integer id = intent.getExtras().getInt(GCHttpRequestStore.ID);
	try {
	    if (instance == null) {
		instance = GCHttpRequestStore.getInstance(getApplicationContext());
	    }
	    GCHttpRequest block = instance.getBlock(id);
	    if (block instanceof AssetsUploadRequest) {
		new AsyncExecutorTask().execute(block);
	    } else {
		new AsyncRequestExecutorTask().execute(block);
	    }
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	} finally {
	    instance.removeBlock(id);
	}
    }

    private class AsyncExecutorTask extends AsyncTask<GCHttpRequest, Void, Void> {
	@Override
	protected Void doInBackground(GCHttpRequest... params) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG, "Seperate thread for uploading photos");
	    }
	    try {
		wl.acquire();
	    } catch (Exception e1) {
		Log.d(TAG, "", e1);
	    }
	    for (GCHttpRequest gcHttpRequest : params) {
		try {
		    gcHttpRequest.execute();
		} catch (Exception e) {
		    Log.d(TAG, "", e);
		}
	    }
	    try {
		wl.release();
	    } catch (Exception e) {
		Log.d(TAG, "", e);
	    }
	    return null;
	}
    }

    @Override
    public IBinder onBind(Intent arg0) {
	return null;
    }

    private class AsyncRequestExecutorTask extends AsyncTaskEx<GCHttpRequest, Void, Void> {
	@Override
	protected Void doInBackground(GCHttpRequest... params) {
	    if (GCConstants.DEBUG) {
		Log.d(TAG, "Async Task EX");
	    }
	    try {
		params[0].execute();
	    } catch (Exception e) {
		if (GCConstants.DEBUG) {
		    Log.d(TAG, "", e);
		}
	    }
	    return null;
	}
    }
}
