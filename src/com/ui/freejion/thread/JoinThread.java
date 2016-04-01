package com.ui.freejion.thread;

import java.io.InputStream;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ui.freejion.common.CBXDateUtil;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.common.SharedPreferencesUtil;
import com.ui.freejion.http.CBXHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinThread extends Thread {

	private static final String TAG = "JoinThread";

	private Context mContext = null;
	private Handler mHandler = null;
	private String mActivityId = null;
	private String mJoinFlag = null; // 1 join,0 leave

	public JoinThread(Context context, Handler handler, String activityId,
			String joinFlag) {
		mContext = context;
		mHandler = handler;
		mJoinFlag = joinFlag;
		mActivityId = activityId;
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		String username = SharedPreferencesUtil.getString(mContext,
				SharedPreferencesUtil.KEY_USERNAME);

		String otherInfo = SharedPreferencesUtil.getString(mContext,
				SharedPreferencesUtil.KEY_OTHERINFO);

		JSONObject postJson = new JSONObject();
		try {
			postJson.put("activityId", mActivityId);
			postJson.put("un", username);
			postJson.put("mn", otherInfo);
			postJson.put("joinFlag", mJoinFlag);
		} catch (JSONException e) {
			CBXManageLog.E(TAG, "JSONException:" + e);
		}

		InputStream in = CBXHttpRequest.executeHttpPostNotPostData(
                CommonUtil.getJoinUrl(mActivityId,username,otherInfo,mJoinFlag));
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		Message msg = mHandler.obtainMessage();
		msg.arg1 = 1;
		msg.obj = data;

		mHandler.sendMessage(msg);
	}
}