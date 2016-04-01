package com.ui.freejion.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class CommonUtil {

	private static final String TAG = "CommonUtil";

	// 主界面请求地址
	public static String getListUrl(String username, String number) {
		return Constant.URL + Constant.ACTIVITIES_LIST + "?un=" + username
				+ "&mn=" + number;
	}

	// Group请求地址
	public static String getGroupUrl() {
		return Constant.URL + Constant.GET_GROUP;
	}

	// 创建活动请求地址
	public static String getCreateUrl() {
		return Constant.URL + Constant.CREATE_ACTIVITY;
	}

	// 活动详情请求地址
	public static String getDetailUrl(String id, String username, String number) {
		return Constant.URL + Constant.ACTIVITY_DETAIL + id + "?un=" + username
				+ "&mn=" + number;
	}

	// 参加活动求地址(joinFlag:1 join,0 leave)
	public static String getJoinUrl(String id, String username, String number,
			String joinFlag) {
		return Constant.URL + Constant.ACTIVITY_JOIN + "?un=" + username
				+ "&mn=" + number + "&activityId=" + id + "&joinFlag="
				+ joinFlag;
	}
	/*
        public static String getJoinUrl(){
            return Constant.URL + Constant.ACTIVITY_JOIN;
        }
        */
	// Save me请求地址
	public static String getSaveMeUrl() {
		return Constant.URL + Constant.SAVE_ME;
	}

	// List me请求地址
	public static String getListMeUrl(String username, String number) {
		return Constant.URL + Constant.LIST_ME + "?un=" + username + "&mn="
				+ number;
	}

	public static String InputStream2String(InputStream in) {

		if (null == in) {
			return null;
		}

		StringBuffer sBuffer = new StringBuffer();
		byte[] buf = new byte[1024];
		try {
			for (int n; (n = in.read(buf)) != -1;) {
				sBuffer.append(new String(buf, 0, n, "utf-8"));
			}

			in.close();
			in = null;
		} catch (UnsupportedEncodingException e) {
			CBXManageLog.E(TAG,
					"UnsupportedEncodingException:" + e.getMessage());
		} catch (IOException e) {
			CBXManageLog.E(TAG, "IOException:" + e.getMessage());
		}

		return sBuffer.toString();
	}
}
