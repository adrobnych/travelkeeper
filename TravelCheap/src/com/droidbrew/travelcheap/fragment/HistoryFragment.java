package com.droidbrew.travelcheap.fragment;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.droidbrew.travelcheap.HistoryActivity;
import com.droidbrew.travelcheap.HomeActivity;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.adapter.HistoryListAdapter;
import com.droidbrew.travelcheap.adapter.RecordsListAdapter;
import com.droidbrew.travelcheap.valueobject.ExpenseRecord;

public class HistoryFragment extends ListFragment {

	private List<ExpenseRecord> expenses;
	private Activity activity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		activity = getActivity();

		refreshRecords();
	}

	@Override
	public void onResume() {
		super.onResume();

		refreshRecords();
	}

	private void refreshRecords() {
		if (activity != null) {
			expenses = ((TravelApp)activity.getApplication()).getVoFactory().getHistoricalExpenseRecordsByTrip();

			setListAdapter(new HistoryListAdapter(activity, expenses));
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Activity activity = getActivity();

		if (activity != null) {
			ListAdapter listAdapter = getListAdapter();
			final ExpenseRecord expense = (ExpenseRecord) listAdapter
					.getItem(position);

			Intent historyIntent = new Intent(this.getActivity(),
					HistoryActivity.class);
			historyIntent.putExtra("date", expense.getTimeMillis());
			historyIntent.putExtra("tab", "totals");
			HistoryFragment.this.startActivity(historyIntent);

		}
	}

}
