package com.ui.freejion.thread;

import java.io.InputStream;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ui.freejion.common.ActivityDetail;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.common.JSonParse;
import com.ui.freejion.common.SharedPreferencesUtil;
import com.ui.freejion.http.CBXHttpRequest;

public class DetailThread extends Thread {

	private static final String TAG = "DetailThread";

	private Context mContext = null;
	private Handler mHandler = null;
	private String mAcitivityId = null;

	public DetailThread(Context context, Handler handler, String id) {
		mContext = context;
		mHandler = handler;
		mAcitivityId = id;
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		String username = SharedPreferencesUtil.getString(mContext,
				SharedPreferencesUtil.KEY_USERNAME);

		String otherInfo = SharedPreferencesUtil.getString(mContext,
				SharedPreferencesUtil.KEY_OTHERINFO);

		InputStream in = CBXHttpRequest.executeHttpGet(CommonUtil.getDetailUrl(
				mAcitivityId, username, otherInfo));
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		ActivityDetail detail = JSonParse.parseDetail(data);

		Message msg = mHandler.obtainMessage();
		msg.arg1 = 0;
		msg.obj = detail;

		mHandler.sendMessage(msg);
	}
}