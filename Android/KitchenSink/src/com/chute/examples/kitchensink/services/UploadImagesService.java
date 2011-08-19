package com.chute.examples.kitchensink.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.chute.examples.kitchensink.ChuteKitchenSinkActivity;
import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.models.DataUploadToken;
import com.chute.examples.kitchensink.models.PhotoDataModel;
import com.chute.examples.kitchensink.utils.ChuteAccountUtils;
import com.chute.examples.kitchensink.utils.ChuteS3Uploader;
import com.chute.sdk.api.upload.UploadsComplete;
import com.chute.sdk.api.upload.UploadsToken;

public class UploadImagesService extends IntentService {

    private static final String TAG = UploadImagesService.class.getSimpleName();
    private static final int UPLOAD_NOTIFICATION_ID = 1234;
    public static final String EXTRA_IMAGES_LIST = "imagesList";
    private static NotificationManager nm;
    private static Notification notification;
    private static PendingIntent contentIntent;
    private ArrayList<PhotoDataModel> imageList;

    public UploadImagesService() {
	super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
	nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	setSyncIndicator(false);
	imageList = intent.getExtras().getParcelableArrayList(EXTRA_IMAGES_LIST);
	Log.e(TAG, "Upload Service started");
	int totalCount = imageList.size();
	int uploaded = 0;

	setSyncIndicator(true);
	String apiKey = ChuteAccountUtils.getAccountInfo(getApplicationContext()).getPassword();
	ChuteS3Uploader uploader;
	Log.e(TAG, "Images Chosen SIZE: " + imageList.size());
	for (PhotoDataModel photo : imageList) {
	    try {
		setNotificationInfo(String.valueOf(totalCount - uploaded));
		if (TextUtils.isEmpty(photo.getAssetId())
			|| photo.getAssetId().contentEquals("null")) {
		    Log.e(TAG, "No asset ID");
		    continue;
		}
		Log.e("UploadsToken", "An assetID = " + photo.getAssetId());
		Log.e("UploadsToken", "Password = " + apiKey);

		DataUploadToken data = UploadsToken.UploadToken(photo.getAssetId(), apiKey);
		// Do the upload
		Log.e("UploadsToken", "DATA= " + data.toString());

		uploader = new ChuteS3Uploader();
		uploader.setFileParams(data.getUploadUrl(), data.getSignature(), data.getDate(),
			data.getFilepath());
		uploader.startUpload();
		UploadsComplete.uploadsComplete(photo.getAssetId(), apiKey);
		uploaded++;
	    } catch (MalformedURLException e) {
		Log.w(TAG, "", e);
	    } catch (IOException e) {
		Log.w(TAG, "", e);
	    } catch (Exception e) {
		Log.w(TAG, "", e);
	    }
	}

	setSyncIndicator(false);
    }

    public void setSyncIndicator(boolean state) {
	if (state == false) {
	    nm.cancel(UPLOAD_NOTIFICATION_ID);
	    return;
	}
	contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(
		getApplicationContext(), ChuteKitchenSinkActivity.class),
		PendingIntent.FLAG_UPDATE_CURRENT);
	notification = new Notification(R.drawable.icon, null, System.currentTimeMillis());
	notification.setLatestEventInfo(getApplicationContext(), "Chute Upload", "Uploading ",
		contentIntent);
	notification.flags |= Notification.FLAG_ONGOING_EVENT;
	notification.flags |= Notification.FLAG_NO_CLEAR;
	nm.notify(UPLOAD_NOTIFICATION_ID, notification);
    }

    private void setNotificationInfo(String info) {
	try {
	    notification.setLatestEventInfo(getApplicationContext(), "Chute Sync",
		    "Syncing Photos to Chute (" + info + " to go)", contentIntent);
	    nm.notify(UPLOAD_NOTIFICATION_ID, notification);
	} catch (Exception e) {
	    Log.d(TAG, "", e);
	}
    }
}
