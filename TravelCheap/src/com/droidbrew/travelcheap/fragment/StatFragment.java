package com.droidbrew.travelcheap.fragment;

import java.util.List;

import com.droidbrew.travelcheap.HistoryActivity;
import com.droidbrew.travelcheap.LangManager;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelcheap.adapter.StatListAdapter;
import com.droidbrew.travelcheap.adapter.TotalsListAdapter;
import com.droidbrew.travelcheap.valueobject.ExpenseDayTotal;
import com.droidbrew.travelcheap.valueobject.ExpenseTripTotal;
import com.droidbrew.travelcheap.R;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class StatFragment extends ListFragment {

	private List<ExpenseTripTotal> totals; 
	private Activity activity;
	private LangManager lm;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        activity = getActivity();
        lm = new LangManager(getResources().getConfiguration().locale.getLanguage());
        
        refreshTotals();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
 
    	refreshTotals();
    }
    
    private void refreshTotals(){
    	if (activity != null) {
        	totals = ((TravelApp)activity.getApplication()).getVoFactory().getExpenceTripTotals();
            setListAdapter(new StatListAdapter(activity, totals));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {   
            ListAdapter listAdapter = getListAdapter();
            ExpenseTripTotal expenseTotal = (ExpenseTripTotal) listAdapter.getItem(position);
            
            Toast.makeText(activity, getString(R.string.statrFragmentToast)+ " " + lm.getTypeFromDB(expenseTotal.getType()) + ": " + expenseTotal.getAmount(), Toast.LENGTH_SHORT).show();
        }
    }

}
