package com.droidbrew.travelcheap.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.valueobject.ExpenseRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryListAdapter extends BaseAdapter {

	private class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}

	private List<ExpenseRecord> expenses;
	private LayoutInflater inflater;

	public HistoryListAdapter(Context context, List<ExpenseRecord> expenses) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.expenses = expenses;
	}

	@Override
	public int getCount() {
		if (expenses != null) {
			return expenses.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (expenses != null && position >= 0 && position < getCount()) {
			return expenses.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (expenses != null && position >= 0 && position < getCount()) {
			return expenses.get(position).getId();
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder viewHolder;

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		if (view == null) {
			view = inflater.inflate(R.layout.item_totals_list, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) view.findViewById(R.id.list_label);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		ExpenseRecord record = expenses.get(position);

		String time = "[" + formatter.format(record.getTimeMillis()) + "] ";
		viewHolder.textView.setText(time + " " + record.getAmount() + " "
				+ record.getCurrencyCode());

		return view;
	}
}
