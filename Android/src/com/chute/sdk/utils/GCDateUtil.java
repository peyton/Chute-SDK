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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GCDateUtil {

	/** The date format in iso. */
	public static String FORMAT_DATE_ISO = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**
	 * Takes in an ISO date string of the following format:
	 * yyyy-mm-ddThh:mm:ss.ms+HoMo
	 * 
	 * @param isoDateString
	 *            the iso date string
	 * @return the date
	 * @throws Exception
	 *             the exception
	 */
	public static Date fromISODateString(String isoDateString) throws Exception {
		DateFormat f = new SimpleDateFormat(FORMAT_DATE_ISO);
		f.setTimeZone(TimeZone.getTimeZone("Zulu"));
		return f.parse(isoDateString);
	}

	/**
	 * Render date
	 * 
	 * @param date
	 *            the date obj
	 * @param format
	 *            - if not specified, will use FORMAT_DATE_ISO
	 * @param tz
	 *            - tz to set to, if not specified uses local timezone
	 * @return the iso-formatted date string
	 */
	public static String toISOString(Date date, String format, TimeZone tz) {
		if (format == null) {
			format = FORMAT_DATE_ISO;
		}
		if (tz == null) {
			tz = TimeZone.getDefault();
		}
		DateFormat f = new SimpleDateFormat(format);
		f.setTimeZone(tz);
		return f.format(date);
	}

	public static String toISOString(Date date) {
		return toISOString(date, FORMAT_DATE_ISO, TimeZone.getDefault());
	}
}
