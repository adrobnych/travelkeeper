package com.droidbrew.travelcheap.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.droidbrew.travelcheap.R;

public class CommentAdapter extends BaseAdapter {

	private class ViewHolder {
		public TextView textView;
	}

	private List<String> comment;
	private LayoutInflater inflater;

	public CommentAdapter(Context context, List<String> comment) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.comment = comment;
	}

	public void setData(List<String> comment) {
		this.comment = comment;
	}
	
	public void add(String com) {
		comment.add(com);
	}
	
	public void clear() {
		comment.clear();
	}

	@Override
	public int getCount() {
		if (comment != null) {
			return comment.size();
		}
		return 0;
	}

	@Override
	public String getItem(int position) {
		if (comment != null && position >= 0 && position < getCount()) {
			return comment.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (comment != null && position >= 0 && position < getCount()) {
			return position;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(android.R.layout.simple_list_item_1, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) view.findViewById(android.R.id.text1);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		String com = comment.get(position);
		viewHolder.textView.setText(com);
		return view;
	}
}