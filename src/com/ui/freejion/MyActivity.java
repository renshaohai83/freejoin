package com.ui.freejion;

import com.ui.freejion.adapter.MyAdapter;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.MyActivitiesData;
import com.ui.freejion.common.SharedPreferencesUtil;
import com.ui.freejion.thread.MyActivitiesThread;
import com.ui.freejion.thread.SaveMeThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyActivity extends Activity {

	private static final String TAG = "DetailActivity";

	private Button mHome = null;
	private Button mSave = null;

	private EditText mUsername = null;
	private EditText mOtherInfo = null;

	private ListView mListView = null;
	private MyAdapter mAdapter = null;

	// 等待对话框
	private ProgressDialog mWaitDlg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CBXManageLog.D(TAG, "onCreate");

		setContentView(R.layout.activity_my);

		mHome = (Button) findViewById(R.id.home);
		mHome.setOnClickListener(mOnClickListener);

		mSave = (Button) findViewById(R.id.save);
		mSave.setOnClickListener(mOnClickListener);

		mUsername = (EditText) findViewById(R.id.username);
		mOtherInfo = (EditText) findViewById(R.id.other_info);

		String username = SharedPreferencesUtil.getString(
				getApplicationContext(), SharedPreferencesUtil.KEY_USERNAME);
		mUsername.setText(username);

		String otherInfo = SharedPreferencesUtil.getString(
				getApplicationContext(), SharedPreferencesUtil.KEY_OTHERINFO);
		mOtherInfo.setText(otherInfo);

		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(mOnItemClickListener);

		// 获取我的活动
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

		new MyActivitiesThread(getApplicationContext(), mHandler).start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			CBXManageLog.D(TAG, "handleMessage");

			if (null != mWaitDlg) {
				mWaitDlg.dismiss();
				mWaitDlg = null;
			}

			switch (msg.arg1) {
			case 0: // save
				String result = (String) msg.obj;
				if ("1".equals(result)) {
					// success
					Toast.makeText(getApplicationContext(), R.string.success,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), R.string.failed,
							Toast.LENGTH_SHORT).show();
				}
				break;

			case 1: // my activities
				MyActivitiesData detail = (MyActivitiesData) msg.obj;

				if (null != detail && null != detail.mJoinedActivities
						&& 0 < detail.mJoinedActivities.size()) {
					mListView.setVisibility(View.VISIBLE);

					mAdapter = new MyAdapter(MyActivity.this,
							detail.mJoinedActivities);
					mListView.setAdapter(mAdapter);
				} else {
					mListView.setVisibility(View.INVISIBLE);
				}

				break;
			default:
				break;
			}
		}
	};

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.home:
				finish();
				break;

			case R.id.save:
				String username = mUsername.getEditableText().toString();
				if (TextUtils.isEmpty(username)) {
					Toast.makeText(getApplicationContext(),
							R.string.username_is_empty, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				String otherInfo = mOtherInfo.getEditableText().toString();
				if (TextUtils.isEmpty(otherInfo)) {
					Toast.makeText(getApplicationContext(),
							R.string.other_info_is_empty, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				SharedPreferencesUtil.save(getApplicationContext(),
						SharedPreferencesUtil.KEY_USERNAME, username);
				SharedPreferencesUtil.save(getApplicationContext(),
						SharedPreferencesUtil.KEY_OTHERINFO, otherInfo);

				mWaitDlg = new ProgressDialog(MyActivity.this);
				mWaitDlg.setCanceledOnTouchOutside(false);
				mWaitDlg.show();

				new SaveMeThread(mHandler, username, otherInfo).start();

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
			Intent detailIntent = new Intent(MyActivity.this,
					DetailActivity.class);
			detailIntent.putExtra("ID", mAdapter.getActivityId(position));

			startActivity(detailIntent);
		}
	};
}