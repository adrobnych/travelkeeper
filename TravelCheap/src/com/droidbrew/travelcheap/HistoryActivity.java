package com.droidbrew.travelcheap;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.droidbrew.travelcheap.fragment.TabFragment;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.Trip;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class HistoryActivity extends FragmentActivity {
	public Button btnAddExpensePB, btnAddExpenseNB;
	public Long dateValue = null;
	private static final int RESULT_SETTINGS = 2;
	private SimpleDateFormat formatter;
	private int itemChoosed = 0;
	TabFragment tabFragment = null;
	View dlg = null;
	
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
					" " + getString(R.string.spentOn) + " " + formatter.format(dateValue));
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
    	Button cButton = null;
    	if(dlg != null)
    		cButton = (Button) dlg.findViewById(R.id.button_currency);

    	 try {
 			setTitle(compileFullTotal(dateValue) + " " +
 					((TravelApp)getApplication()).getCurrencyManager().getReportCurrency() +
 					" " + getString(R.string.spentOn) + " " + formatter.format(dateValue));
 			if(cButton != null)
 				cButton.setText(
 						((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency()
 					);
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
		case R.id.menu_language:
			i = new Intent(this, LanguageActivity.class);
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
	
	public void onCurrencyClick(View view){
		Intent myIntent = new Intent(HistoryActivity.this, CurrencyActivity.class);
		HistoryActivity.this.startActivity(myIntent);
	}
	
	public void onAddExpenseClick(View viev){
		final String[] mChoose = getResources().getStringArray(R.array.mChoose);
	    LayoutInflater inflater = this.getLayoutInflater();
	    dlg =inflater.inflate(R.layout.expense_dialog,null);
	    final EditText einput = (EditText) dlg.findViewById(R.id.edit_text_input);
	    
		final Dialog dialog = new Dialog(HistoryActivity.this);
        dialog.setContentView(dlg);
        //dialog.setContentView(input);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mChoose);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) dlg.findViewById(R.id.spinner);
       
        
        
        spinner.setAdapter(adapter);
        
        
        btnAddExpensePB = (Button)dialog.findViewById(R.id.btnAddExpensePB);
        btnAddExpenseNB = (Button)dialog.findViewById(R.id.btnAddExpenseNB);
        
        btnAddExpensePB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(einput.getText().toString().equals("")) {
					return;}
				Expense expense = new Expense();
				
				expense.setType(getTag(mChoose[spinner.getSelectedItemPosition()]).toLowerCase());
				expense.setAmount((long)(100 * Double.valueOf(einput.getText().toString())));
				expense.setDateAndTime(dateValue);
				try {
					expense.setTripId(((TravelApp)getApplication()).getTripManager().getDefaultTripId());
					expense.setCurrencyCode(
							((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency()
							);
					((TravelApp)getApplication()).getExpenseManager().create(expense);
					
					tabFragment.gotoGridView();
					onResume();
					dialog.dismiss();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
		});
        
        btnAddExpenseNB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			
			}
		});
		
		spinner.setPrompt("Title");
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view,
          int position, long id) {
             }
      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
		
           dialog.show();
	}
	
	
	private String getTag(String tagG) {
		String tag = tagG;
		if(tag.equals("Еда"))
			return "food";
		if(tag.equals("Транспорт"))
			return "transport";
		if(tag.equals("Жилье"))
			return "accommodation";
		if(tag.equals("Покупка товаров"))
			return "shopping";
		if(tag.equals("Развлечения"))
			return "entertainment";
		if(tag.equals("Другие вещи"))
			return "other things";
		return tag;
	}
	
	@Override
	public void onBackPressed() {
		Intent homeIntent = new Intent(HistoryActivity.this, HomeActivity.class);
		HistoryActivity.this.startActivity(homeIntent);
		finish();
	}

}