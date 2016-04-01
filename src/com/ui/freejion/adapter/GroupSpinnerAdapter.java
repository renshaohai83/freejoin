package com.ui.freejion.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ui.freejion.R;

public class GroupSpinnerAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<String> mData = null;

	private LayoutInflater inflater = null;

	public GroupSpinnerAdapter(Context context, List<String> data) {
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
			convertView = inflater.inflate(R.layout.group_spinner_item, null);
			viewHolder = new ViewHolder();

			viewHolder.mName = (TextView) convertView
					.findViewById(R.id.group_name);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String data = mData.get(position);

		viewHolder.mName.setText(data);

		return convertView;
	}

	public static class ViewHolder {

		public TextView mName;
	}
}