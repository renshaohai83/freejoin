package com.ui.freejion.thread;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.http.CBXHttpRequest;

public class SaveMeThread extends Thread {

	private static final String TAG = "SaveMeThread";

	private Handler mHandler = null;
	private String mUsername = null;
	private String mOtherInfo = null;

	public SaveMeThread(Handler handler, String username, String otherInfo) {
		mHandler = handler;
		mUsername = username;
		mOtherInfo = otherInfo;
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		JSONObject postJson = new JSONObject();
		try {
			postJson.put("userName", mUsername);
			postJson.put("mobile", mOtherInfo);
		} catch (JSONException e) {
			CBXManageLog.E(TAG, "JSONException:" + e);
		}

		CBXManageLog.D(TAG, "postJson:" + postJson);

		InputStream in = CBXHttpRequest.executeHttpPost(
				CommonUtil.getSaveMeUrl(), postJson.toString());
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		Message msg = mHandler.obtainMessage();
		msg.arg1 = 0;
		msg.obj = data;

		mHandler.sendMessage(msg);
	}
}