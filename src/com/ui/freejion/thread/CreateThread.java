package com.ui.freejion.thread;

import java.io.InputStream;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ui.freejion.common.CBXDateUtil;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.common.SharedPreferencesUtil;
import com.ui.freejion.http.CBXHttpRequest;

public class CreateThread extends Thread {

	private static final String TAG = "CreateThread";
	private Context mContext = null;

	private Handler mHandler = null;
	private String mTitle = null;
	private String mStartTime = null;
	private String mEndTime = null;
	private String mContent = null;
	private String mGroupId = null;

	public CreateThread(Context context, Handler handler, String title,
			String startTime, String endTime, String content, String groupId) {
		mContext = context;
		mHandler = handler;

		mTitle = title;
		mStartTime = startTime;
		mEndTime = endTime;
		mContent = content;
		mGroupId = groupId;
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		JSONObject postJson = new JSONObject();
		try {
			postJson.put("title", mTitle);
			postJson.put("startTime", mStartTime);
			postJson.put("endTime", mEndTime);
			postJson.put("content", mContent);
			postJson.put("groupId", mGroupId);
			postJson.put("createdBy", SharedPreferencesUtil.getString(mContext,
					SharedPreferencesUtil.KEY_USERNAME));
			postJson.put("createdAt", CBXDateUtil.getDateTimeFormatString(
					new Date(), CBXDateUtil.FormatType.YYYY_MM_DD));
		} catch (JSONException e) {
			CBXManageLog.E(TAG, "JSONException:" + e);
		}

		CBXManageLog.D(TAG, "postJson:" + postJson);

		InputStream in = CBXHttpRequest.executeHttpPost(
				CommonUtil.getCreateUrl(), postJson.toString());
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		Message msg = mHandler.obtainMessage();
		msg.obj = data;

		mHandler.sendMessage(msg);
	}
}
