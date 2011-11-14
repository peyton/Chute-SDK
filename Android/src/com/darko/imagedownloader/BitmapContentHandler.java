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
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * A {@link ContentHandler} that decodes a {@link Bitmap} from a
 * {@link URLConnection}.
 * <p>
 * The implementation includes a work-around for <a
 * href="http://code.google.com/p/android/issues/detail?id=6066">Issue 6066</a>.
 * <p>
 * An {@link IOException} is thrown if there is a decoding exception.
 */
public class BitmapContentHandler extends ContentHandler {
    /** Default value */
    private int requiredSize = 75;

    public int getRequiredSize() {
	return requiredSize;
    }

    public void setRequiredSize(int requiredSize) {
	this.requiredSize = requiredSize;
    }

    @Override
    public Bitmap getContent(URLConnection connection) throws IOException {
	//
	InputStream input = connection.getInputStream();
	File tempFile = FileCache.getTempFile(connection.getURL().toString());
	try {
	    input = new BlockingFilterInputStream(input);

	    final FileOutputStream fileOutput = new FileOutputStream(tempFile);
	    final byte[] buffer = new byte[1024];
	    int bufferLength = 0; // used to store a temporary size of the
	    // buffer
	    while ((bufferLength = input.read(buffer)) > 0) {
		// add the data in the buffer to the file in the file output
		// stream (the file on the sd card
		fileOutput.write(buffer, 0, bufferLength);
	    }
	    fileOutput.flush();
	    fileOutput.close();

	    Options decodeInBounds = decodeInBounds(tempFile);
	    Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getPath(), decodeInBounds);
	    if (bitmap == null) {
		throw new IOException("Image could not be decoded");
	    }
	    return bitmap;
	} finally {
	    tempFile.delete();
	    input.close();
	}
    }

    private BitmapFactory.Options decodeInBounds(File file) throws IOException {
	BitmapFactory.Options o = new BitmapFactory.Options();
	o.inJustDecodeBounds = true;
	BitmapFactory.Options o2;
	try {

	    BitmapFactory.decodeFile(file.getPath(), o);
	    int width_tmp = o.outWidth, height_tmp = o.outHeight;
	    int scale = 1;
	    while (true) {
		if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize) {
		    break;
		}
		width_tmp /= 2;
		height_tmp /= 2;
		scale *= 2;
	    }
	    o2 = new BitmapFactory.Options();
	    o2.inSampleSize = scale;
	} finally {
	}
	return o2;
    }
}
