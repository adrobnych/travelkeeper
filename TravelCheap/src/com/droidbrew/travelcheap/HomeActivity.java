package com.droidbrew.travelcheap;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


public class HomeActivity extends FragmentActivity{
	private CaldroidFragment dialogCaldroidFragment;
	private CaldroidFragment caldroidFragment;
	private Bundle savedInstanceState;
	private TextView amount;


	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		savedInstanceState = _savedInstanceState;
		setContentView(R.layout.activity_home);
		amount = (TextView) findViewById(R.id.amount);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
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
		//to do: get amount from amount widget and save using expenseManager

		String type = (String)view.getTag();
		Expense expense = new Expense();
		expense.setType(type);
		expense.setAmount((long)(100 * Double.valueOf(amount.getText().toString())));
		final long time = (new Date()).getTime();
		expense.setDateAndTime(time);

		try {
			((TravelApp)getApplication()).getExpenseManager().create(expense);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		new AlertDialog.Builder(this)
		.setTitle(composeDialogTitle(amount.getText().toString()))
		.setMessage("Today you spent " + 
				(((TravelApp)getApplication()).getExpenseManager().sumAmountByTypeAndDate(type, time)/100.0)
				+ " Euro for " + type)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						amount.setText("0");
					}
				})
				.show();
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
			Toast.makeText(getApplicationContext(), formatter.format(date),
					Toast.LENGTH_SHORT).show();
			
			Intent historyIntent = new Intent(HomeActivity.this, HistoryActivity.class);
			HomeActivity.this.startActivity(historyIntent);

		}

//		@Override
//		public void onChangeMonth(int month, int year) {
//			String text = "month: " + month + " year: " + year;
//			Toast.makeText(getApplicationContext(), text,
//					Toast.LENGTH_SHORT).show();
//		}

		@Override
		public void onLongClickDate(Date date, View view) {
			Toast.makeText(getApplicationContext(),
					"Long click " + formatter.format(date),
					Toast.LENGTH_SHORT).show();
		}

//		@Override
//		public void onCaldroidViewCreated() {
//			if (caldroidFragment.getLeftArrowButton() != null) {
//				Toast.makeText(getApplicationContext(),
//						"Caldroid view is created", Toast.LENGTH_SHORT)
//						.show();
//			}
//		}

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



}
