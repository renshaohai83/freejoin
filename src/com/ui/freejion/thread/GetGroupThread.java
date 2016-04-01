package com.ui.freejion.thread;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.ui.freejion.MainActivity;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.CommonUtil;
import com.ui.freejion.common.GroupData;
import com.ui.freejion.common.JSonParse;
import com.ui.freejion.http.CBXHttpRequest;

public class GetGroupThread extends Thread {

	private static final String TAG = "GetGroupThread";

	public GetGroupThread() {
	}

	@Override
	public void run() {

		CBXManageLog.D(TAG, "run");

		InputStream in = CBXHttpRequest
				.executeHttpGet(CommonUtil.getGroupUrl());
		String data = CommonUtil.InputStream2String(in);
		CBXManageLog.D(TAG, "data:" + data);

		List<GroupData> groups = JSonParse.parseGroup(data);

		if (null != groups) {
			if (null == MainActivity.mGroups) {
				MainActivity.mGroups = new HashMap<String, GroupData>();
			} else {
				MainActivity.mGroups.clear();
			}

			for (GroupData group : groups) {
				MainActivity.mGroups.put(group.mName, group);
			}
		}
	}
}