/* ---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
*/

package com.pekall.http;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;


class DebugTimer {
	private static final int START = 0;
	private static final int END = 1;

	private static HashMap<String, Long> mTime = new HashMap<String, Long>();
	private static long mStartTime = 0;
	@SuppressWarnings("UnusedDeclaration")
    private static long mLastTime = 0;

	/**
	 * Start a timer
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static void start() {
		reset();
		mStartTime = touch();
	}

	/**
	 * Mark current time
	 * 
	 * @param tag
	 *            mark tag
	 * @return
	 */
	private static long mark(String tag) {
		long time = System.currentTimeMillis() - mStartTime;
		mTime.put(tag, time);
		return time;
	}

	/**
	 * Mark current time
	 * 
	 * @param tag
	 *            mark tag
	 * @return
	 */
	private static long between(String tag, int startOrEnd) {
		Log.v("DEBUG", tag + " " + startOrEnd);
		switch (startOrEnd) {
		case START:
			return mark(tag);
		case END:
			long time = System.currentTimeMillis() - mStartTime
					- get(tag, mStartTime);
			mTime.put(tag, time);
			// touch();
			return time;
		default:
			return -1;
		}
	}

	@SuppressWarnings("SameParameterValue")
    public static long betweenStart(String tag) {
		return between(tag, START);
	}

	@SuppressWarnings("SameParameterValue")
    public static long betweenEnd(String tag) {
		return between(tag, END);
	}

	/**
	 * Stop timer
	 * 
	 * @return result
	 */
	private static String stop() {
		mTime.put("_TOTLE", touch() - mStartTime);
		return __toString();
	}

	@SuppressWarnings("UnusedDeclaration")
    public static String stop(String tag) {
		mark(tag);
		return stop();
	}

	/**
	 * Get a mark time
	 * 
	 * @param tag
	 *            mark tag
	 * @return time(milliseconds) or NULL
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static long get(String tag) {
		return get(tag, 0);
	}

	private static long get(String tag, long defaultValue) {
		if (mTime.containsKey(tag)) {
			return mTime.get(tag);
		}
		return defaultValue;
	}

	/**
	 * Reset timer
	 */
	private static void reset() {
		mTime = new HashMap<String, Long>();
		mStartTime = 0;
		mLastTime = 0;
	}

	/**
	 * static toString()
	 * 
	 * @return
	 */
	private static String __toString() {
		return "Debuger [time =" + mTime.toString() + "]";
	}

	private static long touch() {
		return mLastTime = System.currentTimeMillis();
	}

	private static DebugProfile[] getProfile() {
		DebugProfile[] profile = new DebugProfile[mTime.size()];

		long totel = mTime.get("_TOTLE");

		int i = 0;
		for (String key : mTime.keySet()) {
			long time = mTime.get(key);
			profile[i] = new DebugProfile(key, time, time / (totel * 1.0));
			i++;
		}

		try {
			Arrays.sort(profile);
		} catch (NullPointerException e) {
			// in case item is null, do nothing
		}
		return profile;
	}

	@SuppressWarnings("UnusedDeclaration")
    public static String getProfileAsString() {
		StringBuilder sb = new StringBuilder();
		for (DebugProfile p : getProfile()) {
			sb.append("TAG: ");
			sb.append(p.tag);
			sb.append("\t INC: ");
			sb.append(p.inc);
			sb.append("\t INCP: ");
			sb.append(p.incPercent);
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return __toString();
	}

}

class DebugProfile implements Comparable<DebugProfile> {

    public final String tag;
	public final long inc;
	public final String incPercent;

	@SuppressWarnings("UnusedDeclaration")
    public DebugProfile(String tag, long inc, double incPercent) {
		this.tag = tag;
		this.inc = inc;

        NumberFormat percent = new DecimalFormat("0.00#%");
		this.incPercent = percent.format(incPercent);
	}

	public int compareTo(DebugProfile o) {
		// TODO Auto-generated method stub
		return (int) (o.inc - this.inc);
	}

}
