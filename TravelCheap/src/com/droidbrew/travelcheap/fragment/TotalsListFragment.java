package com.droidbrew.travelcheap.fragment;


import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.droidbrew.travelcheap.adapter.TotalsListAdapter;
import com.droidbrew.travelcheap.valueobject.ExpenseDayTotal;



public class TotalsListFragment extends ListFragment {
		
	private ExpenseDayTotal[] totals; 
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        Activity activity = getActivity();
        
        if (activity != null) {
        	totals = new ExpenseDayTotal[3];
        	totals[0] = new ExpenseDayTotal(0, "food", (new Date()).getTime(), activity.getApplication());
        	totals[1] = new ExpenseDayTotal(1, "shopping", (new Date()).getTime(), activity.getApplication());
        	totals[2] = new ExpenseDayTotal(2, "other things", (new Date()).getTime(), activity.getApplication());
            
            ListAdapter listAdapter = new TotalsListAdapter(activity, totals);
            setListAdapter(listAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            ListAdapter listAdapter = getListAdapter();
            ExpenseDayTotal expenseTotal = (ExpenseDayTotal) listAdapter.getItem(position);
            
            Toast.makeText(activity, "Money spent on " + expenseTotal.getType() + ": " + expenseTotal.getAmount(), Toast.LENGTH_SHORT).show();
        }
    }
       
}
