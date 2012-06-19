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
package com.chute.sdk.utils.rest.entities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.InputStreamEntity;

public class CountingInputStreamEntity extends InputStreamEntity {

	private UploadListener listener;

	public CountingInputStreamEntity(InputStream instream, long length) {
		super(instream, length);
	}

	public void setUploadListener(UploadListener listener) {
		this.listener = listener;
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		super.writeTo(new CountingOutputStream(outstream));
	}

	class CountingOutputStream extends OutputStream {
		private long counter = 0l;
		private final OutputStream outputStream;

		public CountingOutputStream(OutputStream outputStream) {
			this.outputStream = outputStream;
		}

		@Override
		public void write(byte[] buffer, int offset, int count)
				throws IOException {
			this.outputStream.write(buffer, offset, count);
			this.counter += count;
			listener.onChange(counter);
		}

		@Override
		public void write(int oneByte) throws IOException {
			this.outputStream.write(oneByte);
			counter++;
			if (listener != null) {
				// int percent = (int) ((counter * 100) / length);
				listener.onChange(counter);
			}
		}
	}

	public interface UploadListener {
		public void onChange(long current);
	}

}
