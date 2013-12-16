package com.droidbrew.travelcheap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.droidbrew.travelcheap.fragment.TabFragment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class HistoryActivity extends FragmentActivity {
	
	public Long dateValue = null;
	private static final int RESULT_SETTINGS = 2;
	private SimpleDateFormat formatter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        formatter = new SimpleDateFormat("dd MMM yyyy");
        
        Intent intent = getIntent();
        dateValue = intent.getLongExtra("date", -1);
        
        setContentView(R.layout.history_activity);
        try {
			setTitle(compileFullTotal(dateValue) + " " +
					((TravelApp)getApplication()).getCurrencyManager().getReportCurrency() +
					" spent on " + formatter.format(dateValue));
		} catch (SQLException e) {
			e.printStackTrace();
		}

        FragmentManager fm = getSupportFragmentManager();
        TabFragment tabFragment = (TabFragment) fm.findFragmentById(R.id.fragment_tab);
        
        String targetTab = intent.getStringExtra("tab");
        if(targetTab.equals("totals"))
        	tabFragment.gotoListView();
        else
        	tabFragment.gotoGridView();
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
        
    	 try {
 			setTitle(compileFullTotal(dateValue) + " " +
 					((TravelApp)getApplication()).getCurrencyManager().getReportCurrency() +
 					" spent on " + formatter.format(dateValue));
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}

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
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {

		case R.id.menu_report_currency:
			i = new Intent(this, CurrencyActivity.class);
			i.putExtra("type", "currency_for_report");
			startActivityForResult(i, RESULT_SETTINGS);
			break;
		case R.id.menu_administration:
			i = new Intent(this, AdminActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			break;

		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			//showUserSettings();
			break;

		}

	}



}