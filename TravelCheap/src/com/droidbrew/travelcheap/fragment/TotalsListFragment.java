package com.droidbrew.travelcheap.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.adapter.TotalsListAdapter;
import com.droidbrew.travelcheap.valueobject.ExpenseDayTotal;



public class TotalsListFragment extends ListFragment {
		
	private ExpenseDayTotal[] totals; 
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        Activity activity = getActivity();
        
        if (activity != null) {
        	totals = ((TravelApp)activity.getApplication()).getVoFactory().getExpenceDayTotals();
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
