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

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;

/**
 * @author Darko.Grozdanovski
 **/
public class MemoryCache {

	private final ConcurrentHashMap<String, SoftReference<Bitmap>> softReference;

	// Hard cache, with a fixed maximum capacity and a life duration
	private final HashMap<String, Bitmap> sHardBitmapCache;
	private int hardCacheCapacity = 25;

	/**
	 * Set the size of the hard cache, note that the soft cache will still be
	 * filled with displayed photos
	 * 
	 * @param hardCacheCapacity
	 */
	public MemoryCache(int hardCacheCapacity) {
		super();
		this.hardCacheCapacity = hardCacheCapacity;
		sHardBitmapCache = new LinkedHashMapExtension(hardCacheCapacity / 2,
				0.75f, true);
		softReference = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
				hardCacheCapacity / 2);
	}

	public Bitmap get(String id) {
		// First try the hard reference cache
		synchronized (sHardBitmapCache) {
			final Bitmap bitmap = sHardBitmapCache.get(id);
			if (bitmap != null) {
				// Bitmap found in hard cache
				// Move element to first position, so that it is removed last
				sHardBitmapCache.remove(id);
				sHardBitmapCache.put(id, bitmap);
				return bitmap;
			}
		}

		// Then try the soft reference cache
		SoftReference<Bitmap> bitmapReference = softReference.get(id);
		if (bitmapReference != null) {
			final Bitmap bitmap = bitmapReference.get();
			if (bitmap != null) {
				// Bitmap found in soft cache
				return bitmap;
			}
		}
		// Soft reference has been Garbage Collected
		softReference.remove(id);
		return null;
	}

	public void put(String id, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(id, bitmap);
			}
		}
	}

	public String getObjectCounters() {
		return "Hard Cache: " + sHardBitmapCache.size() + " Soft Reference"
				+ softReference.size();
	}

	public void clear() {
		softReference.clear();
		sHardBitmapCache.clear();
	}

	private final class LinkedHashMapExtension extends
			LinkedHashMap<String, Bitmap> {
		private static final long serialVersionUID = -3956509122620786256L;

		private LinkedHashMapExtension(int initialCapacity, float loadFactor,
				boolean accessOrder) {
			super(initialCapacity, loadFactor, accessOrder);
		}

		@Override
		protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
			if (size() > hardCacheCapacity) {
				// Entries push-out of hard reference cache are transferred to
				// soft reference cache
				softReference.put(eldest.getKey(), new SoftReference<Bitmap>(
						eldest.getValue()));
				return true;
			} else {
				return false;
			}
		}
	}
}
