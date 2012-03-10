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
package com.darko.imagedownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author Darko.Grozdanovski
 **/
public class ImageLoader {

    private final MemoryCache memoryCache;
    private static final String TAG = ImageLoader.class.getSimpleName();
    public static final int DEFAULT_HARD_CACHE_SIZE = 25;
    private final FileCache fileCache;
    private final boolean shouldCacheOnSD;
    private boolean shouldCacheInMemory = true;
    private final Map<ImageView, String> imageViews = Collections
	    .synchronizedMap(new WeakHashMap<ImageView, String>());
    private final BitmapContentHandler handler;
    private final ContentURLStreamHandlerFactory streamFactory;
    private int defaultImageId;
    private boolean onlyFromCache = false;

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
    public static final String IMAGE_LOADER_SERVICE = "com.imagedownloader";

    public ImageLoader(Context context, int stubResourceId) {
	this(context, stubResourceId, true, DEFAULT_HARD_CACHE_SIZE);
    }

    public ImageLoader(Context context, int stubResourceId, boolean shouldCacheOnSD) {
	this(context, stubResourceId, shouldCacheOnSD, DEFAULT_HARD_CACHE_SIZE);
    }

    /**
     * @param context
     *            application or activity context
     * @param defaultImageId
     *            the placeholder icon that will be displayed while the photo is
     *            loading or in case of an error
     * @param shouldCacheOnSD
     *            if the files should be cached on SD card
     * @param hardCacheCount
     *            the maximum amount of photos that should be kept in memory
     */
    public ImageLoader(Context context, int defaultImageId, boolean shouldCacheOnSD,
	    int hardCacheCount) {
	// Make the background thead low priority. This way it will not affect
	// the UI performance
	this.defaultImageId = defaultImageId;
	this.shouldCacheOnSD = shouldCacheOnSD;
	this.shouldCacheInMemory = (hardCacheCount == 0 ? false : true);
	photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
	fileCache = new FileCache(context);
	if (shouldCacheInMemory) {
	    memoryCache = new MemoryCache(hardCacheCount);
	} else {
	    memoryCache = null;
	}
	handler = new BitmapContentHandler();
	streamFactory = new ContentURLStreamHandlerFactory(context.getContentResolver());
    }

    public static ImageLoader getLoader(Context context) {
	ImageLoader loader = (ImageLoader) context.getSystemService(IMAGE_LOADER_SERVICE);
	if (loader == null) {
	    context = context.getApplicationContext();
	    loader = (ImageLoader) context.getSystemService(IMAGE_LOADER_SERVICE);
	}
	if (loader == null) {
	    throw new IllegalStateException(
		    "ImageLoader not available, please init the ImageLoader service in the Application class");
	}
	return loader;
    }

    public void setDefaultImageResource(int defaultImageId) {
	this.defaultImageId = defaultImageId;
    }

    public void setDefaultImageSize(int size) {
	handler.setDefaultImageSize(size);
    }

    public void setOnlyFromCache(boolean onlyFromCache) {
	this.onlyFromCache = onlyFromCache;
    }

    public void displayImage(String url, ImageView imageView) {
	try {
	    imageViews.put(imageView, url);
	    Bitmap bitmap = null;
	    if (shouldCacheInMemory) {
		bitmap = memoryCache.get(url);
	    }
	    if (bitmap != null) {
		imageView.setImageBitmap(bitmap);
	    } else {
		queuePhoto(url, imageView);
		imageView.setImageResource(defaultImageId);
	    }
	} catch (Exception e) {
	    Log.e(TAG, e.getMessage(), e);
	}
    }

    private void queuePhoto(String url, ImageView imageView) {
	// This ImageView may be used for other images before. So there may be
	// some old tasks in the queue. We need to discard them.
	photosQueue.clean(imageView);
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

    public Bitmap downloadBitmap(String url) {
	// TRY TO GET IT FROM SDCARD...
	if (TextUtils.isEmpty(url)) {
	    return null;
	}
	Bitmap bmp = null;
	File f = null;
	if (shouldCacheOnSD) {
	    Log.d(TAG, "url" + url);
	    f = fileCache.getFile(url);
	    try {
		Log.d(TAG, "file" + f.length());
		if (f.exists()) {
		    Log.d(TAG, "file exists");
		    // from SD cache
		    bmp = BitmapFactory.decodeFile(f.getPath());
		    if (bmp != null) {
			return bmp; // Can be null
		    }
		}
	    } catch (Exception e) {
		Log.e(TAG, e.getMessage(), e);
	    }
	}
	if (onlyFromCache) {
	    return null;
	}
	// IF IT DOESNT RETURN A BITMAP DOWNLOAD IT
	try {

	    try {
		String protocol = Utils.getProtocol(url);
		URLStreamHandler streamHandler = streamFactory.createURLStreamHandler(protocol);
		bmp = handler.getContent(new URL(null, url, streamHandler).openConnection());
		if (shouldCacheOnSD) {
		    OutputStream outStream = new FileOutputStream(f);
		    bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
		}
	    } catch (OutOfMemoryError e) {
		Log.e(TAG, e.getMessage(), e);
		System.gc();
	    }
	    if (bmp != null) {
		return bmp;
	    }
	} catch (IOException e) {
	    Log.e(TAG, "Connection error while downloading photo" + e.getMessage());
	}
	return null;
    }

    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
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
	public void clean(ImageView image) {
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
			if (bmp != null && shouldCacheInMemory) {
			    memoryCache.put(photoToLoad.url, bmp);
			}
			String tag = imageViews.get(photoToLoad.imageView);
			if (tag != null && tag.equals(photoToLoad.url)) {
			    BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
			    try {
				Activity a = (Activity) photoToLoad.imageView.getContext();
				a.runOnUiThread(bd);
			    } catch (Exception e) {
				Log.e(TAG,
					"Init the Adapter with an Activity context, NOT application!!!");
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

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {

	private final Bitmap bitmap;
	private final ImageView imageView;

	public BitmapDisplayer(Bitmap b, ImageView i) {
	    bitmap = b;
	    imageView = i;
	}

	@Override
	public void run() {
	    if (bitmap != null) {
		imageView.setImageBitmap(bitmap);
	    } else {
		imageView.setImageResource(defaultImageId);
	    }
	}
    }

    public void clearMemoryCache() {
	if (shouldCacheInMemory) {
	    memoryCache.clear();
	}
    }

    public void clearSDCache() {
	if (shouldCacheOnSD) {
	    fileCache.clearCache();
	}
    }

    public String getMemoryLog() {
	if (shouldCacheInMemory) {
	    return memoryCache.getObjectCounters() + " ImageViews in memory: " + imageViews.size();
	}
	return "No memory cache created";
    }

}
