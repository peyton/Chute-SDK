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

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;

/**
 * @author Darko.Grozdanovski
 **/
class ContentURLConnection extends URLConnection {

    private final ContentResolver resolver;

    private final Uri uri;

    private InputStream inputStream;

    private OutputStream outputStream;

    private boolean connected;

    private boolean inputStreamClosed;

    private boolean outputStreamClosed;

    public ContentURLConnection(ContentResolver resolver, URL url) {
        super(url);
        this.resolver = resolver;
        String spec = url.toString();
        uri = Uri.parse(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect() throws IOException {
        if (getDoInput()) {
            InputStream in = this.resolver.openInputStream(uri);
            this.inputStream = new ContentURLConnectionInputStream(in);
        }
        if (getDoOutput()) {
            OutputStream out = this.resolver.openOutputStream(uri, "rwt");
            this.outputStream = new ContentURLConnectionOutputStream(out);
        }
        this.connected = true;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (this.inputStreamClosed) {
            throw new IllegalStateException("Closed");
        }
        if (!this.connected) {
            connect();
        }
        return this.inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (this.outputStreamClosed) {
            throw new IllegalStateException("Closed");
        }
        if (!this.connected) {
            connect();
        }
        return this.outputStream;
    }

    @Override
    public Object getContent() throws IOException {
        if (!this.connected) {
            connect();
        }
        return super.getContent();
    }

    @Override
    public String getContentType() {
        return this.resolver.getType(uri);
    }

    @Override
    public int getContentLength() {
        try {
            AssetFileDescriptor fd = this.resolver.openAssetFileDescriptor(uri, "r");
            long length = fd.getLength();
            if (length <= 0 && length <= Integer.MAX_VALUE) {
                return (int) length;
            }
        } catch (IOException e) {
        }
        return -1;
    }

    private class ContentURLConnectionInputStream extends FilterInputStream {

        public ContentURLConnectionInputStream(InputStream in) {
            super(in);
        }

        @Override
        public void close() throws IOException {
            super.close();
            inputStreamClosed = true;
        }
    }

    private class ContentURLConnectionOutputStream extends FilterOutputStream {

        public ContentURLConnectionOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void close() throws IOException {
            super.close();
            outputStreamClosed = true;
        }
    }

}
