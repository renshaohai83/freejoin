package com.ui.freejion.adapter;

import java.util.List;

import com.ui.freejion.R;
import com.ui.freejion.common.ActivityData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<ActivityData> mData = null;

	private LayoutInflater inflater = null;

	public MyAdapter(Context context, List<ActivityData> data) {
		mContext = context;
		mData = data;

		inflater = LayoutInflater.from(context);
	}

	public String getActivityId(int position) {
		if (null == mData || 0 > position || position > mData.size()) {
			return null;
		}

		return mData.get(position).mId;
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
			convertView = inflater.inflate(R.layout.my_list_item, null);
			viewHolder = new ViewHolder();

			viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ActivityData data = mData.get(position);

		viewHolder.mTitle.setText(data.mTitle);

		return convertView;
	}

	public static class ViewHolder {

		public TextView mTitle;
	}
}
