package com.ui.freejion.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ui.freejion.R;
import com.ui.freejion.common.ActivityData;

import java.util.List;
import java.util.Random;

public class ListViewAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<ActivityData> mData = null;

	private static int[] imgs = {
			R.drawable.basketball_03,
			R.drawable.coding_03,
			R.drawable.kaihui_03,
			R.drawable.xiaotiqing_03,
			R.drawable.zhuoshangzuqiu_03,
			R.drawable.huaxue_03,
			R.drawable.basketball_03,
			R.drawable.badminton_03,
			R.drawable.tb_03,
			R.drawable.football_03
	};

	private static int size = imgs.length;

	private static Random rand = new Random(200);

	private LayoutInflater inflater = null;

	public ListViewAdapter(Context context, List<ActivityData> data) {
		mContext = context;
		mData = data;

		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return null == mData ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {
		return null == mData ? null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.list_item, null);
			viewHolder = new ViewHolder();

			viewHolder.mCreatedBy = (TextView) convertView
					.findViewById(R.id.who);
			viewHolder.mStartTime = (TextView) convertView
					.findViewById(R.id.time);
			//viewHolder.mEndTime = (TextView) convertView
			//		.findViewById(R.id.endtime);
			viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);


			viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ActivityData data = mData.get(position);

		viewHolder.mTitle.setText(data.mTitle);
		viewHolder.mStartTime.setText(data.mStartTime);
//		viewHolder.mEndTime.setText(" to " +data.mEndTime);
		viewHolder.mCreatedBy.setText(data.mCreatedBy);

		int img = rand.nextInt(size);
		viewHolder.mIcon.setImageResource(imgs[img]);

		return convertView;
	}

	public static class ViewHolder {

		public TextView mCreatedBy;
		public TextView mStartTime;
//		public TextView mEndTime;
		public TextView mTitle;
		public ImageView mIcon;
	}
}
