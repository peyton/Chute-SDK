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

import android.content.Context;
import android.os.Environment;

/**
 * @author Darko.Grozdanovski
 **/
public class FileCache {

    private static final String SDCARD_FOLDER = Environment.getExternalStorageDirectory()
	    + "/Android/data/%s/files/";
    private static final String TEMP_CACHE = "/temp/";

    private static File cacheDir;

    public FileCache(Context context) {
	if (android.os.Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED)) {
	    cacheDir = new File(getSdCacheLocation(context));

	} else {
	    cacheDir = context.getCacheDir();
	}
	if (!cacheDir.exists()) {
	    cacheDir.mkdirs();
	}
    }

    public static String getSdCacheLocation(Context context) {
	return String.format(SDCARD_FOLDER, context.getPackageName());
    }

    public static File getTempFile(String filename) {
	File f = new File(cacheDir + TEMP_CACHE);
	if (!f.exists()) {
	    f.mkdirs();
	}
	return new File(f, MD5.md5(filename));
    }

    public File getFile(String url) {
	String filename = String.valueOf(MD5.md5(url));
	File f = new File(cacheDir, filename);
	return f;
    }

    public void clearCache() {
	File[] files = cacheDir.listFiles();
	for (File f : files) {
	    try {
		f.delete();
	    } catch (Exception e) {
	    }
	}
    }

}
