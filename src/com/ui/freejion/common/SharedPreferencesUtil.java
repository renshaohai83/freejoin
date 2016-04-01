package com.ui.freejion.common;

import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

	private static final String NAME = "FREE_JOIN";

	// username
	public static final String KEY_USERNAME = "USERNAME";
	// other info
	public static final String KEY_OTHERINFO = "OTHERINFO";

	/**
	 * 存储数据String
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void save(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString(key, value);
		// 提交当前数据
		editor.commit();
	}

	public static void save(Context context, String key, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putInt(key, value);
		// 提交当前数据
		editor.commit();
	}

	public static void save(Context context, String key, Set<String> values) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putStringSet(key, values);
		// 提交当前数据
		editor.commit();
	}

	/**
	 * 获取数据String
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");
	}

	/**
	 * 获取数据int
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getInt(key, 0);
	}

	/**
	 * 获取数据String Set
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static Set<String> getStringSet(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				NAME, Activity.MODE_PRIVATE);
		return sharedPreferences.getStringSet(key, null);
	}

}
