package com.chute.sdk.utils;

import android.util.Log;

public class Logger {
	private static String debugTag = "Application";
	private static DebugLevel debugLevel = DebugLevel.VERBOSE;

	private Logger() {
	}

	public static String getDebugTag() {
		return Logger.debugTag;
	}

	public static void setDebugTag(final String debugTag) {
		Logger.debugTag = debugTag;
	}

	public static DebugLevel getDebugLevel() {
		return Logger.debugLevel;
	}

	public static void setDebugLevel(final DebugLevel debugLevel) {
		if (debugLevel == null) {
			throw new IllegalArgumentException("debugLevel must not be null!");
		}
		Logger.debugLevel = debugLevel;
	}

	public static void v(final String message) {
		Logger.v(debugTag, message);
	}

	public static void v(final String tag, final String message) {
		if (!debugLevel.isDebuggable(DebugLevel.VERBOSE)) {
			return;
		}
		Log.v(tag, message);
	}

	public static void d(final String message) {
		Logger.d(debugTag, message, null);
	}

	public static void d(final String message, final Throwable throwable) {
		Logger.d(debugTag, message, throwable);
	}

	public static void d(final String tag, final String message) {
		Logger.d(tag, message, null);
	}

	public static void d(final String tag, final String message,
			final Throwable throwable) {
		if (!debugLevel.isDebuggable(DebugLevel.DEBUG)) {
			return;
		}
		if (throwable == null) {
			Log.d(tag, message);
		} else {
			Log.d(tag, message, throwable);
		}
	}

	public static void i(final String message) {
		Logger.i(debugTag, message, null);
	}

	public static void i(final String message, final Throwable throwable) {
		Logger.i(debugTag, message, throwable);
	}

	public static void i(final String tag, final String message) {
		Logger.i(tag, message, null);
	}

	public static void i(final String tag, final String message,
			final Throwable throwable) {
		if (!debugLevel.isDebuggable(DebugLevel.INFO)) {
			return;
		}
		if (throwable == null) {
			Log.i(tag, message);
		} else {
			Log.i(tag, message, throwable);
		}
	}

	public static void w(final String message) {
		Logger.w(debugTag, message, null);
	}

	public static void w(final Throwable throwable) {
		Logger.w(debugTag, "", throwable);
	}

	public static void w(final String tag, final String message) {
		Logger.w(tag, message, null);
	}

	public static void w(final String tag, final String message,
			final Throwable throwable) {
		if (!debugLevel.isDebuggable(DebugLevel.WARNING)) {
			return;
		}
		if (throwable == null) {
			Log.w(tag, message, new Exception());
		} else {
			Log.w(tag, message, throwable);
		}
	}

	public static void e(final String message) {
		Logger.e(debugTag, message, null);
	}

	public static void e(final Throwable throwable) {
		Logger.e(debugTag, "", throwable);
	}

	public static void e(final String tag, final String message) {
		Logger.e(tag, message, null);
	}

	public static void e(final String tag, final String message,
			final Throwable throwable) {
		if (!debugLevel.isDebuggable(DebugLevel.ERROR)) {
			return;
		}
		if (throwable == null) {
			Log.w(tag, message);
		} else {
			Log.w(tag, message, throwable);
		}
	}

	public static enum DebugLevel implements Comparable<DebugLevel> {
		NONE, ERROR, WARNING, INFO, DEBUG, VERBOSE;

		public static DebugLevel ALL = DebugLevel.VERBOSE;

		private boolean isDebuggable(final DebugLevel debugLevel) {
			return this.compareTo(debugLevel) >= 0;
		}
	}
}
