package com.droidbrew.travelcheap;


import java.sql.SQLException;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.droidbrew.travelcheap.fragment.HistoryTabFragments;
import com.droidbrew.travelcheap.fragment.TabFragment;

public class HistoryTripsActivity extends FragmentActivity {
	public Long dateValue = null;
	private static final int RESULT_SETTINGS = 2;
	private static final String LOG = "com.droidbrew.dfa.HomeActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.history_two_activity);
        
        FragmentManager fm = getSupportFragmentManager();
        HistoryTabFragments tabFragment = (HistoryTabFragments) fm.findFragmentById(R.id.fragment_tab1);
        tabFragment.gotoHistoryView();
        //Log.d(LOG, tabFragment.toString());
    }
    
    
/*    @Override
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
*/    

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

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//		switch (requestCode) {
//		case RESULT_SETTINGS:
//			//showUserSettings();
//			break;
//
//		}
//
//	}

	@Override
	public void onBackPressed() {
		Intent homeIntent = new Intent(HistoryTripsActivity.this, HomeActivity.class);
		HistoryTripsActivity.this.startActivity(homeIntent);
		finish();
	}
}
