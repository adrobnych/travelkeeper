package com.droidbrew.travelcheap;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CurrencyCalculatorActivity extends Activity {
	
	private static final int RESULT_SETTINGS = 2;
	SharedPreferences pref;
	
	 @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.currency_calculator_activity);
	    pref = PreferenceManager.getDefaultSharedPreferences(this);
	    
	    EditText eField = (EditText) this.findViewById(R.id.input_convert);
	    eField.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) { refresh();	}
	    });
	    
	 }
	 
	 private void refresh() {
			try {
				String str = ((TextView) this.findViewById(R.id.input_convert))
						.getText().toString();
				if(str.equals(""))
					str = "0";
				double amount = 100*Double.parseDouble(str.toString());
				String code = ((TravelApp)getApplication())
						.getCurrencyManager().getEntranceCurrency();
				double course = ((TravelApp)getApplication())
						.getCurrencyManager().find(code).getCourse();
				long usdamount = Math.round(amount/(course/1000000.0));
				
				TextView tv = (TextView) findViewById(R.id.amount1);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)getApplication()).getCurrencyManager()
						.find(pref.getString("convert1", "USD"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) findViewById(R.id.amount2);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)getApplication()).getCurrencyManager()
						.find(pref.getString("convert2", "EUR"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) findViewById(R.id.amount3);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)getApplication()).getCurrencyManager()
						.find(pref.getString("convert3", "CNY"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) findViewById(R.id.amount4);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)getApplication()).getCurrencyManager()
						.find(pref.getString("convert4", "JPY"))
						.getCourse() / 1000000.0)/100.0);
				
				tv = (TextView) findViewById(R.id.amount5);
				tv.setText(""+Math.round(usdamount * 
						((TravelApp)getApplication()).getCurrencyManager()
						.find(pref.getString("convert5", "GBP"))
						.getCourse() / 1000000.0)/100.0);
				
			}catch(SQLException ex) {
				Log.e("CurrencyCalculatorActivity", ex.getMessage());
			}
		 
	 }
	 
	 public void onCurrencyClick(View view){
		 int id = view.getId();
			Intent i = new Intent(CurrencyCalculatorActivity.this, CurrencyActivity.class);
		 
		 switch(id) {
		 case R.id.btn1:
			 i.putExtra("convert", "convert1");
			 break;
		 case R.id.btn2:
			 i.putExtra("convert", "convert2");
			 break;
		 case R.id.btn3:
			 i.putExtra("convert", "convert3");
			 break;
		 case R.id.btn4:
			 i.putExtra("convert", "convert4");
			 break;
		 case R.id.btn5:
			 i.putExtra("convert", "convert5");
			 break;
		 }

		 CurrencyCalculatorActivity.this.startActivity(i);
		 
	 }
	 
		@Override
		protected void onResume() {
			super.onResume();
	        setTitle(R.string.CurrencyCalculator);

			Button cButton = (Button) findViewById(R.id.button_currency);
			Button btn1 = (Button) findViewById(R.id.btn1);
			btn1.setText(pref.getString("convert1", "USD"));
			Button btn2 = (Button) findViewById(R.id.btn2);
			btn2.setText(pref.getString("convert2", "EUR"));
			Button btn3 = (Button) findViewById(R.id.btn3);
			btn3.setText(pref.getString("convert3", "CNY"));
			Button btn4 = (Button) findViewById(R.id.btn4);
			btn4.setText(pref.getString("convert4", "JPY"));
			Button btn5 = (Button) findViewById(R.id.btn5);
			btn5.setText(pref.getString("convert5", "GBP"));
	        try {
				cButton.setText(
					((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency()
				);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	        refresh();
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
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
			case R.id.menu_home:
				i = new Intent(this, HomeActivity.class);
				startActivityForResult(i, RESULT_SETTINGS);
				break;
					}
			return true;
		}
		
		@Override
		public void onBackPressed() {
			Intent homeIntent = new Intent(CurrencyCalculatorActivity.this, HomeActivity.class);
			CurrencyCalculatorActivity.this.startActivity(homeIntent);
			finish();
		}
}
