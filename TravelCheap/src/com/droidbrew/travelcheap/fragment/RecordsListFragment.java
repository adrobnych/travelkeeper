package com.droidbrew.travelcheap.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.droidbrew.travelcheap.HistoryActivity;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.adapter.RecordsListAdapter;
import com.droidbrew.travelcheap.valueobject.ExpenseRecord;

public class RecordsListFragment extends ListFragment {
    
private List<ExpenseRecord> expenses; 
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        Activity activity = getActivity();
        
        if (activity != null) {
        	expenses = ((TravelApp)activity.getApplication()).getVoFactory().getExpenseRecords(
        			((HistoryActivity)activity).dateValue);
            ListAdapter listAdapter = new RecordsListAdapter(activity, expenses);
            setListAdapter(listAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            ListAdapter listAdapter = getListAdapter();
            ExpenseRecord expense = (ExpenseRecord) listAdapter.getItem(position);
            
            Toast.makeText(activity, "Money spent on " + expense.getType() + ": " + expense.getAmount(), Toast.LENGTH_SHORT).show();
        }
    }

}
