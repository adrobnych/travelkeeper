package com.droidbrew.travelcheap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.droidbrew.travelcheap.fragment.TabFragment;

import java.text.SimpleDateFormat;

public class HistoryActivity extends FragmentActivity {
	
	public Long dateValue = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        
        Intent intent = getIntent();
        dateValue = intent.getLongExtra("date", -1);
        
        setContentView(R.layout.history_activity);
        setTitle(compileFullTotal(dateValue) + " EUR spent on " + formatter.format(dateValue));

        FragmentManager fm = getSupportFragmentManager();
        TabFragment tabFragment = (TabFragment) fm.findFragmentById(R.id.fragment_tab);
        tabFragment.gotoListView();
    }
    
    private String compileFullTotal(long date){
    	String result = "";
    	result += ((TravelApp)getApplication()).getExpenseManager().sumAmountByDate(date)/100.0;
    	return result;
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