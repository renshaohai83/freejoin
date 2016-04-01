package com.ui.freejion;

import com.ui.freejion.common.ActivityDetail;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.common.JoinedUserData;
import com.ui.freejion.common.SharedPreferencesUtil;
import com.ui.freejion.thread.DetailThread;
import com.ui.freejion.thread.JoinThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	private static final String TAG = "DetailActivity";

	private Button mHome = null;
	private Button mMe = null;
	private Button mJoinLeave = null;

	private TextView mTitle = null;
	private TextView mOrganizer = null;
	private TextView mSchedule = null;
	private TextView mContent = null;
	private TextView mWhoJoinedTitle = null;
	private TextView mWhoJoined = null;
	private TextView mWhoNotJoinedTitle = null;
	private TextView mWhoNotJoined = null;
	private ScrollView mScrollView = null;

	private String mActivityId = null;

	// 等待对话框
	private ProgressDialog mWaitDlg = null;

	private boolean mJoined = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CBXManageLog.D(TAG, "onCreate");

		setContentView(R.layout.activity_detail);

		mActivityId = getIntent().getStringExtra("ID");
		CBXManageLog.D(TAG, "mActivityId:" + mActivityId);

		mHome = (Button) findViewById(R.id.home);
		mHome.setOnClickListener(mOnClickListener);
		mHome.setBackgroundColor(getResources().getColor(
				R.color.home_btn_pressed));

		mMe = (Button) findViewById(R.id.me);
		mMe.setOnClickListener(mOnClickListener);
		mHome.setBackgroundColor(getResources().getColor(R.color.me_btn_normal));

		mJoinLeave = (Button) findViewById(R.id.join_leave);
		mJoinLeave.setOnClickListener(mOnClickListener);

		mTitle = (TextView) findViewById(R.id.title);
		mOrganizer = (TextView) findViewById(R.id.organizer);
		mSchedule = (TextView) findViewById(R.id.schedule);
		mContent = (TextView) findViewById(R.id.content);
		mWhoJoinedTitle = (TextView) findViewById(R.id.who_joined_title);
		mWhoJoined = (TextView) findViewById(R.id.who_joined);
		mWhoNotJoinedTitle = (TextView) findViewById(R.id.who_not_joined_title);
		mWhoNotJoined = (TextView) findViewById(R.id.who_not_joined);

		mScrollView = (ScrollView) findViewById(R.id.scrollview_activity_detail);
		// 获取详情
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
		if (null == mWaitDlg) {
			mWaitDlg = new ProgressDialog(this);
			mWaitDlg.setCanceledOnTouchOutside(false);
			mWaitDlg.show();
		}

		new DetailThread(getApplicationContext(), mHandler, mActivityId)
				.start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			CBXManageLog.D(TAG, "handleMessage");

			switch (msg.arg1) {
			case 0: // get detail

				if (null != mWaitDlg) {
					mWaitDlg.dismiss();
					mWaitDlg = null;
				}

				ActivityDetail detail = (ActivityDetail) msg.obj;
				if (null == detail) {
					return;
				}

				mTitle.setText(detail.mTitle);
				mOrganizer.setText(detail.mCreatedBy);
				mSchedule.setText(detail.mStartTime);
				mContent.setText(detail.mContent);

				String myName = SharedPreferencesUtil.getString(
						getApplicationContext(),
						SharedPreferencesUtil.KEY_USERNAME);

				mJoined = false;
				if (null != detail.mJoinedUsers) {
					String title = getString(R.string.who_joined);
					mWhoJoinedTitle.setText(String.format(title,
							detail.mJoinedUsers.size()));
					String users = "";
					for (JoinedUserData user : detail.mJoinedUsers) {
						if (user.mUsername.equals(myName)) {
							mJoined = true;
						}
						users += user.mUsername + "\n";
					}
					mWhoJoined.setText(users);
				}

				if (null != detail.mNotJoinedUsers) {
					String title = getString(R.string.who_not_joined);
					mWhoNotJoinedTitle.setText(String.format(title,
							detail.mNotJoinedUsers.size()));
					String users = "";
					for (JoinedUserData user : detail.mNotJoinedUsers) {
						users += user.mUsername + "\n";
					}
					mWhoNotJoined.setText(users);
				}

				if (mJoined) {
					mJoinLeave.setText(R.string.leave);
				} else {
					mJoinLeave.setText(R.string.join);
				}
				break;

			case 1: // join/leave
				String result = (String) msg.obj;
				if ("1".equals(result)) {
					// success
					Toast.makeText(getApplicationContext(), R.string.success,
							Toast.LENGTH_SHORT).show();

					queryData();
				} else {
					if (null != mWaitDlg) {
						mWaitDlg.dismiss();
						mWaitDlg = null;
					}

					Toast.makeText(getApplicationContext(), R.string.failed,
							Toast.LENGTH_SHORT).show();
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

			case R.id.me:
				Intent myIntent = new Intent(DetailActivity.this,
						MyActivity.class);
				startActivity(myIntent);

				finish();
				break;

			case R.id.join_leave:
				mWaitDlg = new ProgressDialog(DetailActivity.this);
				mWaitDlg.setCanceledOnTouchOutside(false);
				mWaitDlg.show();

				if (mJoined) {
					// to leave
					new JoinThread(getApplicationContext(), mHandler,
							mActivityId, "0").start();
				} else {
					// to join
					new JoinThread(getApplicationContext(), mHandler,
							mActivityId, "1").start();
				}
				break;

			default:
				break;
			}

		}
	};
}