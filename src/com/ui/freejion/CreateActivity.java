package com.ui.freejion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ui.freejion.adapter.GroupSpinnerAdapter;
import com.ui.freejion.common.CBXManageLog;
import com.ui.freejion.thread.CreateThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends Activity {
	private static final String TAG = "CreateActivity";

	private Button mHome = null;
	private Button mMe = null;
	private Button mSubmit = null;

	private EditText mTitle = null;
	private EditText mStartTimeEditor = null;
	private EditText mEndTimeEditor = null;
	private EditText mContent = null;

	private Spinner mGroupSpinner = null;
	private GroupSpinnerAdapter mGroupAdapter = null;

	private ImageView mStartTimeImg = null;
	private ImageView mEndTimeImg = null;
	private ImageView mLaImg = null;

	private String mStartTime = null;
	private String mEndTime = null;

	private String mGroupId = null;
	private List<String> mGroupIds = null;

	public static String[] startss = new String[20];
	public static String[] endss = new String[20];


	// 等待对话框
	private ProgressDialog mWaitDlg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CBXManageLog.D(TAG, "onCreate");

		setContentView(R.layout.activity_create);

		mHome = (Button) findViewById(R.id.home);
		mHome.setOnClickListener(mOnClickListener);
		mHome.setBackgroundColor(getResources().getColor(
				R.color.home_btn_pressed));

		mMe = (Button) findViewById(R.id.me);
		mMe.setOnClickListener(mOnClickListener);
		mHome.setBackgroundColor(getResources().getColor(R.color.me_btn_normal));

		mSubmit = (Button) findViewById(R.id.submit);
		mSubmit.setOnClickListener(mOnClickListener);

		mTitle = (EditText) findViewById(R.id.title);
		mStartTimeEditor = (EditText) findViewById(R.id.startTime);
		mEndTimeEditor = (EditText) findViewById(R.id.endTime);
		mContent = (EditText) findViewById(R.id.content);

		mStartTimeImg = (ImageView) findViewById(R.id.start_time_img);
		mStartTimeImg.setOnClickListener(mOnClickListener);

		mEndTimeImg = (ImageView) findViewById(R.id.end_time_img);
		mEndTimeImg.setOnClickListener(mOnClickListener);

		mLaImg = (ImageView) findViewById(R.id.la_image);
		mLaImg.setOnClickListener(mOnClickListener);

		mGroupSpinner = (Spinner) findViewById(R.id.group_spinner);

		// 设为只读
		mStartTimeEditor.setCursorVisible(false);
		mStartTimeEditor.setFocusable(false);
		mStartTimeEditor.setFocusableInTouchMode(false);

		mEndTimeEditor.setCursorVisible(false);
		mEndTimeEditor.setFocusable(false);
		mEndTimeEditor.setFocusableInTouchMode(false);

		initGroup();
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

	private void initGroup() {

		if (null == mGroupIds) {
			mGroupIds = new ArrayList<String>();
		} else {
			mGroupIds.clear();
		}

		List<String> data = new ArrayList<String>();
		if (null != MainActivity.mGroups) {
			Set<String> keys = MainActivity.mGroups.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String name = it.next();
				data.add(name);
				mGroupIds.add(MainActivity.mGroups.get(name).mId);
			}
		}

		mGroupAdapter = new GroupSpinnerAdapter(this, data);
		mGroupSpinner.setAdapter(mGroupAdapter);
		// 注册事件
		mGroupSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						mGroupId = mGroupIds.get(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}

				});
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home:
				finish();
				break;

			case R.id.me:
				Intent myIntent = new Intent(CreateActivity.this,
						MyActivity.class);
				startActivity(myIntent);

				finish();
				break;

			case R.id.submit:

				String title = mTitle.getEditableText().toString();
				if (TextUtils.isEmpty(title)) {
					Toast.makeText(getApplicationContext(),
							R.string.title_is_empty, Toast.LENGTH_SHORT).show();
					return;
				}
				String content = mContent.getEditableText().toString();
				if (TextUtils.isEmpty(content)) {
					Toast.makeText(getApplicationContext(),
							R.string.content_is_empty, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				String mStartTime_all = mStartTimeEditor.getEditableText().toString();

				startss = mStartTime_all.split(" ");
				String StartDate = startss[0];
				String StartTime = startss[1];
				String mStartTime = StartDate + "T" + StartTime + ":00.000+0000";


				if (TextUtils.isEmpty(mStartTime)) {
					Toast.makeText(getApplicationContext(),
							R.string.start_time_is_empty, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				String mEndTime_all = mEndTimeEditor.getEditableText().toString();
				endss = mEndTime_all.split(" ");
                String EndDate = endss[0];
                String EndTime = endss[1];
                String mEndTime = EndDate + "T" + EndTime +  ":00.000+0000";


				if (TextUtils.isEmpty(mEndTime)) {
					Toast.makeText(getApplicationContext(),
							R.string.end_time_is_empty, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				mWaitDlg = new ProgressDialog(CreateActivity.this);
				mWaitDlg.setCanceledOnTouchOutside(false);
				mWaitDlg.show();

				new CreateThread(getApplicationContext(), mHandler, title,
						mStartTime, mEndTime, content, mGroupId).start();

				break;

			case R.id.start_time_img:
				selectStartTime();
				break;

			case R.id.end_time_img:
				selectEndTime();
				break;

			case R.id.la_image:
				mGroupSpinner.performClick();
				break;

			default:
				break;
			}

		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			CBXManageLog.D(TAG, "handleMessage");

			if (null != mWaitDlg) {
				mWaitDlg.dismiss();
				mWaitDlg = null;
			}

			String result = (String) msg.obj;
			if ("1".equals(result)) {
				// success
				Toast.makeText(getApplicationContext(), R.string.success,
						Toast.LENGTH_SHORT).show();

				finish();
			} else {
				Toast.makeText(getApplicationContext(), R.string.failed,
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void selectStartTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.date_time_dialog, null);

		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.datepicker);
		final TimePicker timePicker = (android.widget.TimePicker) view
				.findViewById(R.id.timepicker);

		builder.setView(view);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);

		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(Calendar.MINUTE);

		builder.setTitle(R.string.start_time);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer startSb = new StringBuffer();
						startSb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));

						startSb.append(" ");
						startSb.append(timePicker.getCurrentHour()).append(":")
								.append(timePicker.getCurrentMinute());

						mStartTime = startSb.toString();
						CBXManageLog.D(TAG, "mStartTime:" + mStartTime );

						mStartTimeEditor.setText(mStartTime);

						dialog.cancel();
					}
				});
		builder.show();
	}

	private void selectEndTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.date_time_dialog, null);

		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.datepicker);
		final TimePicker timePicker = (android.widget.TimePicker) view
				.findViewById(R.id.timepicker);

		builder.setView(view);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);

		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(Calendar.MINUTE);

		builder.setTitle(R.string.end_time);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer endSb = new StringBuffer();
						endSb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));

						endSb.append(" ");
						endSb.append(timePicker.getCurrentHour()).append(":")
								.append(timePicker.getCurrentMinute());

						mEndTime = endSb.toString();
						CBXManageLog.D(TAG, "mEndTime:" + mEndTime);

						mEndTimeEditor.setText(mEndTime);

						dialog.cancel();
					}
				});
		builder.show();
	}
}