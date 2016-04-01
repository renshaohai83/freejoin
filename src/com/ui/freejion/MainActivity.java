package com.ui.freejion;

import java.util.List;
import java.util.Map;

import com.ui.freejion.adapter.ListViewAdapter;
import com.ui.freejion.common.ActivityData;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.GroupData;
import com.ui.freejion.thread.GetGroupThread;
import com.ui.freejion.thread.ListThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private ListView mListView = null;
	private ListViewAdapter mAdapter = null;

	private List<ActivityData> mData = null;

	private Button mCreate = null;
	private Button mMe = null;

	// 等待对话框
	private ProgressDialog mWaitDlg = null;

	public static Map<String, GroupData> mGroups = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CBXManageLog.D(TAG, "onCreate");

		setContentView(R.layout.activity_main);

		mCreate = (Button) findViewById(R.id.create_btn);
		mCreate.setOnClickListener(mOnClickListener);

		mMe = (Button) findViewById(R.id.me);
		mMe.setOnClickListener(mOnClickListener);

		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(mOnItemClickListener);

		// 获取活动列表
		queryData();
	}

	@Override
	protected void onStop() {
		super.onDestroy();
		CBXManageLog.D(TAG, "onStop");

		if (null != mWaitDlg) {
			mWaitDlg.dismiss();
			mWaitDlg = null;
		}
	}

	private void queryData() {
		mWaitDlg = new ProgressDialog(this);
		mWaitDlg.setCanceledOnTouchOutside(false);
		mWaitDlg.show();

		new ListThread(mHandler).start();

		// 获取分组
		new GetGroupThread().start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			CBXManageLog.D(TAG, "handleMessage");

			if (null != mWaitDlg) {
				mWaitDlg.dismiss();
				mWaitDlg = null;
			}

			mData = (List<ActivityData>) msg.obj;

			mAdapter = new ListViewAdapter(MainActivity.this, mData);
			mListView.setAdapter(mAdapter);

			switch (msg.arg1) {
			default:
				break;
			}
		}
	};

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.create_btn:

				Intent createIntent = new Intent(MainActivity.this,
						CreateActivity.class);
				startActivity(createIntent);
				break;

			case R.id.me:
				Intent myIntent = new Intent(MainActivity.this,
						MyActivity.class);
				startActivity(myIntent);
				break;

			default:
				break;
			}

		}
	};

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent detailIntent = new Intent(MainActivity.this,
					DetailActivity.class);
			detailIntent.putExtra("ID", mData.get(position).mId);

			startActivity(detailIntent);
		}
	};
}
