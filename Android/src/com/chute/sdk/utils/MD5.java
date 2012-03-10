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
package com.chute.sdk.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class MD5 {

   public static final String TAG = MD5.class.getSimpleName();

   private static byte[] createChecksum(String filename) throws Exception {
      InputStream fis = new FileInputStream(filename);

      byte[] buffer = new byte[1024];
      MessageDigest complete = MessageDigest.getInstance("MD5");
      int numRead;
      do {
	 numRead = fis.read(buffer);
	 if (numRead > 0) {
	    complete.update(buffer, 0, numRead);
	 }
      } while (numRead != -1);
      fis.close();
      return complete.digest();
   }

   // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public static String getMD5Checksum(String filename) throws Exception {
      byte[] b = createChecksum(filename);
      String result = "";
      for (int i = 0; i < b.length; i++) {
	 result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
      }
      return result;
   }

   public static String getStringMD5(String s) {
      try {
	 // Create MD5 Hash
	 MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	 digest.update(s.getBytes());
	 byte messageDigest[] = digest.digest();

	 // Create Hex String
	 StringBuffer hexString = new StringBuffer();
	 for (int i = 0; i < messageDigest.length; i++) {
	    String h = Integer.toHexString(0xFF & messageDigest[i]);
	    while (h.length() < 2) {
	       h = "0" + h;
	    }
	    hexString.append(h);
	 }
	 return hexString.toString();

      } catch (NoSuchAlgorithmException e) {
	 Log.w(TAG, "", e);
      }
      return "";
   }
}
