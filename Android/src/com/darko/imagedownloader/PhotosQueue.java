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

import android.widget.ImageView;

import com.darko.imagedownloader.queue.DequeStrategy;
import com.darko.imagedownloader.queue.QueueStrategy;
import com.darko.imagedownloader.queue.StackStrategy;

/**
 * @author darko.grozdanovski
 */
public class PhotosQueue {

	final DequeStrategy<PhotoToLoad> photosToLoad;

	public enum QueueMethod {
		STACK, QUEUE;
	}

	public PhotosQueue(QueueMethod method) {
		super();
		if (method == QueueMethod.STACK) {
			photosToLoad = new StackStrategy<PhotoToLoad>();
			return;
		}
		if (method == QueueMethod.QUEUE) {
			photosToLoad = new QueueStrategy<PhotoToLoad>();
			return;
		}
		throw new IllegalArgumentException("Input a valid dequeue method, See "
				+ QueueMethod.class.getName());
	}

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

	// Task for the queue
	static class PhotoToLoad {

		public String url;
		public ImageView imageView;
		public final int photoSizeInPixels;

		public PhotoToLoad(String u, ImageView i, int photoSizeInPixels) {
			url = u;
			imageView = i;
			this.photoSizeInPixels = photoSizeInPixels;
		}
	}
}