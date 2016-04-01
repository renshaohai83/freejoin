package com.ui.freejion.thread;

import java.io.InputStream;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.ui.freejion.common.ActivityData;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.common.JSonParse;
import com.ui.freejion.http.CBXHttpRequest;

public class ListThread extends Thread {

	private static final String TAG = "ListThread";

	private Handler mHandler = null;

	public ListThread(Handler handler) {
		mHandler = handler;
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		InputStream in = CBXHttpRequest.executeHttpGet(CommonUtil.getListUrl(
				"", ""));
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		List<ActivityData> list = JSonParse.parseList(data);

		Message msg = mHandler.obtainMessage();
		msg.obj = list;

		mHandler.sendMessage(msg);
	}
}
