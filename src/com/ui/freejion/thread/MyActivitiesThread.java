package com.ui.freejion.thread;

import java.io.InputStream;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.common.JSonParse;
import com.ui.freejion.common.MyActivitiesData;
import com.ui.freejion.common.SharedPreferencesUtil;
import com.ui.freejion.http.CBXHttpRequest;

public class MyActivitiesThread extends Thread {

	private static final String TAG = "MyActivitiesThread";

	private Context mContext = null;
	private Handler mHandler = null;

	public MyActivitiesThread(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		String username = SharedPreferencesUtil.getString(mContext,
				SharedPreferencesUtil.KEY_USERNAME);

		String otherInfo = SharedPreferencesUtil.getString(mContext,
				SharedPreferencesUtil.KEY_OTHERINFO);

		InputStream in = CBXHttpRequest.executeHttpGet(CommonUtil.getListMeUrl(
				username, otherInfo));
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		MyActivitiesData myData = JSonParse.parseMyActivities(data);

		Message msg = mHandler.obtainMessage();
		msg.arg1 = 1;
		msg.obj = myData;

		mHandler.sendMessage(msg);
	}
}