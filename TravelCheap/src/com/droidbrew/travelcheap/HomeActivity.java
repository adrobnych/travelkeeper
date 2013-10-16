package com.droidbrew.travelcheap;

import java.sql.SQLException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelcheap.TravelApp;

public class HomeActivity extends Activity implements View.OnClickListener{
	
	private TextView amount;
	private TextView message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		amount = (TextView) findViewById(R.id.amount);
		
//		Button button = (Button)findViewById(R.id.button_1);
//      button.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		String old_value = (String) amount.getText();
		String button_text = (String) ((Button)view).getText();
		String new_value;
		if(button_text.equals("C"))
			if(old_value.length() == 1)
				new_value = "0";
			else
				new_value = old_value.substring(0, old_value.length()-1);
		else {
			if(old_value.equals("0"))
				old_value = "";
			new_value = old_value + button_text;
		}
		amount.setText(new_value);
	}
	
	public void onExpenseClick(View view){
		//to do: get amount from amount widget and save using expenseManager
		
		String type = (String)view.getTag();
		Expense expense = new Expense();
		expense.setType(type);
		expense.setAmount(Long.valueOf(amount.getText().toString()));
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
	    		((TravelApp)getApplication()).getExpenseManager().sumAmountByTypeAndDate(type, time).toString()
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

}
