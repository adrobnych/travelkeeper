package com.droidbrew.travelcheap.fragment;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.droidbrew.travelcheap.HistoryActivity;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.adapter.RecordsListAdapter;
import com.droidbrew.travelcheap.valueobject.ExpenseRecord;

public class RecordsListFragment extends ListFragment {
    
	private List<ExpenseRecord> expenses; 
	private Activity activity;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		activity = getActivity();

		refreshRecords();
	}

	@Override
	public void onResume(){
		super.onResume();

		refreshRecords();
	}

	private void refreshRecords(){
		if (activity != null) {
			expenses = ((TravelApp)activity.getApplication()).getVoFactory().getExpenseRecords(
					((HistoryActivity)activity).dateValue);
			setListAdapter(new RecordsListAdapter(activity, expenses));
		}
	}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            ListAdapter listAdapter = getListAdapter();
            final ExpenseRecord expense = (ExpenseRecord) listAdapter.getItem(position);
            
            //Toast.makeText(activity, "Money spent on " + expense.getType() + ": " + expense.getAmount(), Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this.getActivity())
        	.setTitle("Are you sure?")
        	.setMessage("Record to be deleted: " + expense.getType() + ": " + expense.getAmount())
        	.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) { 
        			((TravelApp)getActivity().getApplication()).getExpenseManager().deleteExpenseById(expense.getId());
        			getActivity().finish();
        			Intent intent = getActivity().getIntent();
        			intent.putExtra("tab", "records");
        			startActivity(intent);
        		}})
        	.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int which) {}})
        	.show();
        }
    }

}
