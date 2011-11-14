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
package com.darko.imagedownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.chute.sdk.utils.GCConstants;

public class ImageLoader {

    private final MemoryCache memoryCache = new MemoryCache();
    private static final String TAG = ImageLoader.class.getSimpleName();
    private final FileCache fileCache;
    private final Map<ImageView, String> imageViews = Collections
	    .synchronizedMap(new WeakHashMap<ImageView, String>());
    private final BitmapContentHandler handler;

    /**
     * Use with {@link Context#getSystemService(String)} to retrieve an
     * {@link ImageLoader} for loading images.
     * <p>
     * Since {@link ImageLoader} is not a standard system service, you must
     * create a custom {@link Application} subclass implementing
     * {@link Application#getSystemService(String)} and add it to your
     * {@code AndroidManifest.xml}.
     * <p>
     * Using this constant is optional and it is only provided for convenience
     * and to promote consistency across deployments of this component.
     */
    public static final String IMAGE_LOADER_SERVICE = "com.darko.imageloader";

    public ImageLoader(Context context, int stubResourceId) {
	// Make the background thead low priority. This way it will not affect
	// the UI performance
	this.stubId = stubResourceId;
	photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
	fileCache = new FileCache(context);
	handler = new BitmapContentHandler();
	cr = context.getContentResolver();
    }

    public static ImageLoader get(Context context) {
	ImageLoader loader = (ImageLoader) context.getSystemService(IMAGE_LOADER_SERVICE);
	if (loader == null) {
	    context = context.getApplicationContext();
	    loader = (ImageLoader) context.getSystemService(IMAGE_LOADER_SERVICE);
	}
	if (loader == null) {
	    throw new IllegalStateException("ImageLoader not available");
	}
	return loader;
    }

    private int stubId;

    public void setStub(int stubId) {
	this.stubId = stubId;
    }

    public void setRequiredSize(int size) {
	handler.setRequiredSize(size);
    }

    public void displayImage(String url, ImageView imageView) {
	try {
	    imageViews.put(imageView, url);
	    Bitmap bitmap = memoryCache.get(url);
	    if (bitmap != null) {
		imageView.setImageBitmap(bitmap);
	    } else {
		queuePhoto(url, imageView);
		imageView.setImageResource(stubId);
	    }
	} catch (Exception e) {
	    Log.w(TAG, "", e);
	}

    }

    private void queuePhoto(String url, ImageView imageView) {
	// This ImageView may be used for other images before. So there may be
	// some old tasks in the queue. We need to discard them.
	photosQueue.Clean(imageView);
	PhotoToLoad p = new PhotoToLoad(url, imageView);
	synchronized (photosQueue.photosToLoad) {
	    photosQueue.photosToLoad.push(p);
	    photosQueue.photosToLoad.notifyAll();
	}

	// start thread if it's not started yet
	if (photoLoaderThread.getState() == Thread.State.NEW) {
	    photoLoaderThread.start();
	}
    }

    private Bitmap downloadBitmap(String url) {
	// TRY TO GET IT FROM SDCARD...
	// Then TODO Cache on SDCARD for offline access
	// Log.e(LOG_TAG, url);
	if (TextUtils.isEmpty(url)) {
	    return null;
	}
	final File f = fileCache.getFile(url);
	try {

	    if (f.exists()) {
		// from SD cache
		Bitmap b = BitmapFactory.decodeFile(f.getPath());
		if (b != null) {
		    return b; // Can be null
		}
	    }
	} catch (StringIndexOutOfBoundsException e) {
	} catch (Exception e) {
	    if (GCConstants.DEBUG) {
		Log.w(TAG, "", e);
	    }
	}
	// TRY TO GET FROM SDPATH - The url can be an sd card filepath

	/*FileOutputStream out;
	try {
	    out = new FileOutputStream(f);
	    Bitmap bmp = BitmapFactory.decodeFile(url);
	    if (bmp == null) {
		throw new NullPointerException();
	    }
	    bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
	    out.flush();
	    out.close();
	    return bmp;
	} catch (FileNotFoundException e) {
	    Log.d(TAG, "", e);
	} catch (IOException e) {
	    Log.d(TAG, "", e);
	} catch (NullPointerException e) {
	    Log.d(TAG, "", e);
	}
	*/
	// IF IT DOESNT RETURN A BITMAP DOWNLOAD IT
	try {
	    OutputStream outStream = new FileOutputStream(f);
	    Bitmap bmp = null;
	    try {
		bmp = handler.getContent(new URL(url).openConnection());
		if (bmp.getHeight() > 20 && bmp.getWidth() > 20) {
		    bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
		}
	    } catch (MalformedURLException e) {
		bmp = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(url)));
	    } catch (OutOfMemoryError e) {
		if (GCConstants.DEBUG) {
		    Log.w(TAG, "", e);
		}
		System.gc();
	    }
	    if (bmp != null) {
		return bmp;
	    }
	} catch (IOException e) {
	    if (GCConstants.DEBUG) {
		Log.v(TAG, "", e);
	    }
	}
	return null;
    }

    /*
     * An InputStream that skips the exact number of bytes provided, unless it
     * reaches EOF.
     */

    // Task for the queue
    private class PhotoToLoad {
	public String url;
	public ImageView imageView;

	public PhotoToLoad(String u, ImageView i) {
	    url = u;
	    imageView = i;
	}
    }

    PhotosQueue photosQueue = new PhotosQueue();

    public void stopThread() {
	photoLoaderThread.interrupt();
    }

    // stores list of photos to download
    class PhotosQueue {
	private final Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

	// removes all instances of this ImageView
	public void Clean(ImageView image) {
	    for (int j = 0; j < photosToLoad.size();) {
		if (photosToLoad.get(j).imageView == image) {
		    photosToLoad.remove(j);
		} else {
		    ++j;
		}
	    }
	}
    }

    class PhotosLoader extends Thread {
	@Override
	public void run() {
	    try {
		while (true) {
		    // thread waits until there are any images to load in the
		    // queue
		    if (photosQueue.photosToLoad.size() == 0) {
			synchronized (photosQueue.photosToLoad) {
			    photosQueue.photosToLoad.wait();
			}
		    }
		    if (photosQueue.photosToLoad.size() != 0) {
			PhotoToLoad photoToLoad;
			synchronized (photosQueue.photosToLoad) {
			    photoToLoad = photosQueue.photosToLoad.pop();
			}
			Bitmap bmp = downloadBitmap(photoToLoad.url);
			if (bmp != null) {
			    memoryCache.put(photoToLoad.url, bmp);
			}
			String tag = imageViews.get(photoToLoad.imageView);
			if (tag != null && tag.equals(photoToLoad.url)) {
			    BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
			    try {
				Activity a = (Activity) photoToLoad.imageView.getContext();
				a.runOnUiThread(bd);
			    } catch (Exception e) {
				Log.w(TAG,
					"Init the Adapter with an Activity context, Not application",
					e);
			    }
			}
		    }
		    if (Thread.interrupted()) {
			break;
		    }
		}
	    } catch (InterruptedException e) {
		// allow thread to exit
	    }
	}
    }

    private final PhotosLoader photoLoaderThread = new PhotosLoader();
    private final ContentResolver cr;

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
	Bitmap bitmap;
	ImageView imageView;

	public BitmapDisplayer(Bitmap b, ImageView i) {
	    bitmap = b;
	    imageView = i;
	}

	@Override
	public void run() {
	    if (bitmap != null) {
		imageView.setImageBitmap(bitmap);
	    } else {
		imageView.setImageResource(stubId);
	    }
	}
    }

    public void clearMemoryCache() {
	memoryCache.clear();
    }

    public void clearSDCache() {
	fileCache.clearCache();
    }

}
