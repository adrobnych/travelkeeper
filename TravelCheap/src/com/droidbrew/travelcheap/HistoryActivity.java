package com.droidbrew.travelcheap;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droidbrew.travelcheap.fragment.TabFragment;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.Trip;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class HistoryActivity extends FragmentActivity {
	
	public Long dateValue = null;
	private static final int RESULT_SETTINGS = 2;
	private SimpleDateFormat formatter;
	private int itemChoosed = 0;
	TabFragment tabFragment = null;
	
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
        tabFragment = (TabFragment) fm.findFragmentById(R.id.fragment_tab);
        
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

	public void onAddExpenseClick(View viev){
		  final String[] mChoose = { "Food", "Transport", "Shopping" 
				  ,"Accommodation","Entertainment","Other things"};
		  final EditText input = new EditText(this);
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Add Expense")
		            .setCancelable(false)
		       
		       .setView(input)
		      .setPositiveButton("Add", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(input.getText().toString().equals("")) {
						Toast.makeText(
                                getApplicationContext(),
                                "Enter the correct amount",
                                Toast.LENGTH_SHORT).show();
						return;
					}
					Expense expense = new Expense();
					expense.setType(mChoose[itemChoosed].toLowerCase());
					expense.setAmount((long)(100 * Double.valueOf(input.getText().toString())));
					expense.setDateAndTime(dateValue);
					try {
						expense.setTripId(((TravelApp)getApplication()).getTripManager().getDefaultTripId());
						//Log.d(LOG, expense.toString());
						expense.setCurrencyCode(
								((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency()
								);
						((TravelApp)getApplication()).getExpenseManager().create(expense);
						
						tabFragment.gotoGridView();

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			})
			.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
		      		    
		    .setSingleChoiceItems(mChoose, -1,
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog,
                              int item) {
                         itemChoosed = item;
                      }
                  });
		    builder.show();
		    
	  }
	
	
	
	@Override
	public void onBackPressed() {
		Intent homeIntent = new Intent(HistoryActivity.this, HomeActivity.class);
		HistoryActivity.this.startActivity(homeIntent);
		finish();
	}

}