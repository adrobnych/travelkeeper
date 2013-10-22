package com.droidbrew.travelcheap;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.droidbrew.travelcheap.fragment.TabFragment;

public class HistoryActivity extends FragmentActivity {
	
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.history_activity);

        FragmentManager fm = getSupportFragmentManager();
        TabFragment tabFragment = (TabFragment) fm.findFragmentById(R.id.fragment_tab);
        tabFragment.gotoListView();
    }
    
    public String[] getTotals(Long timeMillis){
    	String[] types = {"food", "transport", "other"};
    	String[] result = new String[3];
    	int i = 0;
    	
    	for(String type : types)
    		result[i++] = "" +
    			((TravelApp)getApplication()).getExpenseManager().sumAmountByTypeAndDate(type, timeMillis)/100.0;
    	
    	return result;
    }

}