package com.droidbrew.travelcheap;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
		pref = this.getSharedPreferences("TravelApp", MODE_PRIVATE);

		EditText eField = (EditText) this.findViewById(R.id.amount1);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				 if(hasFocus) ((EditText)v).setText("");
			}
		});
		eField = (EditText) this.findViewById(R.id.amount2);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				 if(hasFocus) ((EditText)v).setText("");
			}
		});
		eField = (EditText) this.findViewById(R.id.amount3);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				 if(hasFocus) ((EditText)v).setText("");
			}
		});
		eField = (EditText) this.findViewById(R.id.amount4);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				 if(hasFocus) ((EditText)v).setText("");
			}
		});
		eField = (EditText) this.findViewById(R.id.amount5);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				 if(hasFocus) ((EditText)v).setText("");
			}
		});
	}

	public void refresh(View view) {
		try {

			if (view == null)
				view = (EditText) findViewById(R.id.amount1);
			String str = ((EditText) view).getText().toString();
			if (str.equals(""))
				str = "0";
			double amount = 100 * Double.parseDouble(str.toString());
			String code = "EUR";
			switch (view.getId()) {
			case R.id.amount1:
				code = pref.getString("convert1", "USD");
				break;
			case R.id.amount2:
				code = pref.getString("convert2", "EUR");
				break;
			case R.id.amount3:
				code = pref.getString("convert3", "CNY");
				break;
			case R.id.amount4:
				code = pref.getString("convert4", "JPY");
				break;
			case R.id.amount5:
				code = pref.getString("convert5", "GBP");
				break;

			}
			double course = ((TravelApp) getApplication()).getCurrencyManager()
					.find(code).getCourse();
			long usdamount = Math.round(amount / (course / 1000000.0));

			if (view.getId() != R.id.amount1)
				((EditText) findViewById(R.id.amount1)).setText(getResult(
						usdamount, pref.getString("convert1", "USD")));
			if (view.getId() != R.id.amount2)
				((EditText) findViewById(R.id.amount2)).setText(getResult(
						usdamount, pref.getString("convert2", "EUR")));
			if (view.getId() != R.id.amount3)
				((EditText) findViewById(R.id.amount3)).setText(getResult(
						usdamount, pref.getString("convert3", "CNY")));
			if (view.getId() != R.id.amount4)
				((EditText) findViewById(R.id.amount4)).setText(getResult(
						usdamount, pref.getString("convert4", "JPY")));
			if (view.getId() != R.id.amount5)
				((EditText) findViewById(R.id.amount5)).setText(getResult(
						usdamount, pref.getString("convert5", "GBP")));

		} catch (SQLException ex) {
			Log.e("CurrencyCalculatorActivity", ex.getMessage());
		}
	}

	private String getResult(long usdamount, String code) throws SQLException {
		return ""
				+ Math.round(usdamount
						* ((TravelApp) getApplication()).getCurrencyManager()
								.find(code).getCourse() / 1000000.0) / 100.0;
	}

	public void onCurrencyClick(View view) {
		int id = view.getId();
		Intent i = new Intent(CurrencyCalculatorActivity.this,
				CurrencyActivity.class);

		switch (id) {
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

		refresh(null);
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
		case R.id.menu_home:
			i = new Intent(this, HomeActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent homeIntent = new Intent(CurrencyCalculatorActivity.this,
				HomeActivity.class);
		CurrencyCalculatorActivity.this.startActivity(homeIntent);
		finish();
	}

	public void onClickEditText(){
		((EditText) findViewById(R.id.amount1)).setText("");
	}
	
	private class SomeWatcher implements TextWatcher {

		private View view;

		private SomeWatcher(View view) {
			this.view = view;
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (view.isFocused())
				refresh(view);
		}

	}

}
