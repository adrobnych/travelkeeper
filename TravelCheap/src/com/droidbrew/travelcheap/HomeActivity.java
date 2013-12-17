package com.droidbrew.travelcheap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.droidbrew.travelkeeper.model.manager.CurrencyHTTPHelper;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


public class HomeActivity extends FragmentActivity{
	
	private static final String LOG = "com.droidbrew.dfa.HomeActivity";
	private CaldroidFragment dialogCaldroidFragment;
	private CaldroidFragment caldroidFragment;
	private Bundle savedInstanceState;
	private TextView amount;
	
	private static final int RESULT_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		savedInstanceState = _savedInstanceState;
		setContentView(R.layout.activity_home);
		amount = (TextView) findViewById(R.id.amount);
        loadCurrencies();
        setTitle("Spend money in smart way!");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Button cButton = (Button) findViewById(R.id.button_currency);
        try {
			cButton.setText(
				((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency()
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadCurrencies() {
		try {
			if(((TravelApp)getApplication()).getCurrencyManager().getWholeList().size() == 0)
				loadCurrenciesFromAssets();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void loadCurrenciesFromAssets() {
		CurrencyLoadTask clt = new CurrencyLoadTask();
		clt.execute();
	}


	public void onClick(View view) {
		String old_value = (String) amount.getText();
		String button_text = (String) ((Button)view).getText();
		String new_value = null;
		if(button_text.equals("C"))
			if(old_value.length() == 1)
				new_value = "0";
			else
				new_value = old_value.substring(0, old_value.length()-1);
		else {
			if(old_value.equals("0"))
				old_value = "";
			if((!button_text.equals(".") || 
					(button_text.equals(".") && thisIsNotSecondDot(old_value))) && 
					thisIsNotThirdDigitAfterDot(old_value))
				new_value = old_value + button_text;
			else
				new_value = old_value;
		}
		amount.setText(new_value);
	}
	
	private boolean thisIsNotThirdDigitAfterDot(String amountLine){
		if(amountLine.indexOf(".") == -1)
			return true;
		else
			return (amountLine.length() -  amountLine.indexOf(".") <= 2);
	}
	
	private boolean thisIsNotSecondDot(String amountLine){
		return (amountLine.indexOf(".") == -1);
	}
	
	public void onExpenseClick(View view){

		String amountLine = amount.getText().toString();

		String type = (String)view.getTag();
		Expense expense = new Expense();
		expense.setType(type);
		expense.setAmount((long)(100 * Double.valueOf(amountLine)));
		final long time = (new Date()).getTime();
		expense.setDateAndTime(time);
		try {
			expense.setCurrencyCode(
					((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency()
					);
			if(!amountLine.equals("0")){
				((TravelApp)getApplication()).getExpenseManager().create(expense);
			}

			new AlertDialog.Builder(this)
			.setTitle(composeDialogTitle(amountLine))
			.setMessage("Today you spent " + 
					(((TravelApp)getApplication()).getExpenseManager().sumAmountByTypeAndDate(type, time)/100.0)
					+ " " + ((TravelApp)getApplication()).getCurrencyManager().getReportCurrency()
					+ " for " + type)
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) { 
							amount.setText("0");
						}
					})
					.show();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String composeDialogTitle(String amountValue){
		if(amountValue.equals("0"))
			return "Today\'s expenses";
		else
			return "New expense reported";

	}

	
	final CaldroidListener listener = new CaldroidListener() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

		@Override
		public void onSelectDate(Date date, View view) {
//			Toast.makeText(getApplicationContext(), formatter.format(date),
//					Toast.LENGTH_SHORT).show();
			
			Intent historyIntent = new Intent(HomeActivity.this, HistoryActivity.class);
			historyIntent.putExtra("date", date.getTime());
			historyIntent.putExtra("tab", "totals");
			HomeActivity.this.startActivity(historyIntent);

		}


		@Override
		public void onLongClickDate(Date date, View view) {
			Toast.makeText(getApplicationContext(),
					"Long click " + formatter.format(date),
					Toast.LENGTH_SHORT).show();
		}

	};



	public void onHistoryClick(View view){

		final Bundle state = savedInstanceState;
		caldroidFragment = new CaldroidFragment();

		// Setup caldroid to use as dialog
		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(listener);

		// If activity is recovered from rotation
		final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
		if (state != null) {
			dialogCaldroidFragment.restoreDialogStatesFromKey(
					getSupportFragmentManager(), state,
					"DIALOG_CALDROID_SAVED_STATE", dialogTag);
			Bundle args = dialogCaldroidFragment.getArguments();
			if (args == null) {
				args = new Bundle();
				dialogCaldroidFragment.setArguments(args);
			}
			args.putString(CaldroidFragment.DIALOG_TITLE,
					"Select a date");
		} else {
			// Setup arguments
			Bundle bundle = new Bundle();
			// Setup dialogTitle
			bundle.putString(CaldroidFragment.DIALOG_TITLE,
					"Select a date");
			dialogCaldroidFragment.setArguments(bundle);
		}

		dialogCaldroidFragment.show(getSupportFragmentManager(),
				dialogTag);
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
			finish();
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
	
	public void onCurrencyClick(View view){
		Intent myIntent = new Intent(HomeActivity.this, CurrencyActivity.class);
		HomeActivity.this.startActivity(myIntent);
		finish();
	}


	class CurrencyLoadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			AssetManager am = getApplication().getAssets();
			StringBuffer xmlString = new StringBuffer();
			StringBuffer cnamesString = new StringBuffer();
			try {
				InputStream is = am.open("quote.xml");
				BufferedReader breader = new BufferedReader(new InputStreamReader(is));
				String line;
				while ((line = breader.readLine()) != null) {
					xmlString.append(line + "\n");
				}       
				is = am.open("currency_names.yml");
				breader = new BufferedReader(new InputStreamReader(is));
				while ((line = breader.readLine()) != null) {
					cnamesString.append(line + "\n");
				}
			} catch (IOException e) {
				Log.e(LOG, "xml file read error: " + e);
			}

			CurrencyHTTPHelper currencyHTTPHelper = new CurrencyHTTPHelper();

			Map<String, TKCurrency> cMap = currencyHTTPHelper.buildCurrencyMap(xmlString.toString(), 
					cnamesString.toString());

			try {
				for(TKCurrency currency : cMap.values())
					((TravelApp)getApplication()).getCurrencyManager().create(currency);
				
				((TravelApp)getApplication()).getCurrencyManager().setAsEntranceCurrency("EUR");

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;

		}

	}
	
//	@Override
//	public void onBackPressed() {
//	    finish();
//	}
}
