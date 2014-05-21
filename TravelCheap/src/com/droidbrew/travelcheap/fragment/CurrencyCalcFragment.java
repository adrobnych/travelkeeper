package com.droidbrew.travelcheap.fragment;

import java.sql.SQLException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.droidbrew.travelcheap.CurrencyActivity;
import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;

public class CurrencyCalcFragment extends Fragment {

	private static final int RESULT_SETTINGS = 2;
	SharedPreferences pref;
	private boolean refresh = false;

	public void setRefresh(boolean bool) {
		refresh = bool;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_currency, container,
				false);
		pref = this.getActivity().getSharedPreferences("TravelApp",
				Context.MODE_PRIVATE);

		final Button btn1 = (Button) view.findViewById(R.id.btn1);
		btn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onCurrencyClick(arg0);
			}
		});

		final Button btn2 = (Button) view.findViewById(R.id.btn2);
		btn2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onCurrencyClick(arg0);
			}
		});

		final Button btn3 = (Button) view.findViewById(R.id.btn3);
		btn3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onCurrencyClick(arg0);
			}
		});

		final Button btn4 = (Button) view.findViewById(R.id.btn4);
		btn4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onCurrencyClick(arg0);
			}
		});

		final Button btn5 = (Button) view.findViewById(R.id.btn5);
		btn5.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onCurrencyClick(arg0);
			}
		});

		EditText eField = (EditText) view.findViewById(R.id.amount1);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField = (EditText) view.findViewById(R.id.amount2);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField = (EditText) view.findViewById(R.id.amount3);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField = (EditText) view.findViewById(R.id.amount4);
		eField.addTextChangedListener(new SomeWatcher(eField));
		eField = (EditText) view.findViewById(R.id.amount5);
		eField.addTextChangedListener(new SomeWatcher(eField));
		return view;
	}

	public void onStart() {
		super.onStart();
		Button btn1 = (Button) this.getActivity().findViewById(R.id.btn1);
		btn1.setText(pref.getString("convert1", "USD"));
		Button btn2 = (Button) this.getActivity().findViewById(R.id.btn2);
		btn2.setText(pref.getString("convert2", "EUR"));
		Button btn3 = (Button) this.getActivity().findViewById(R.id.btn3);
		btn3.setText(pref.getString("convert3", "CNY"));
		Button btn4 = (Button) this.getActivity().findViewById(R.id.btn4);
		btn4.setText(pref.getString("convert4", "JPY"));
		Button btn5 = (Button) this.getActivity().findViewById(R.id.btn5);
		btn5.setText(pref.getString("convert5", "GBP"));

		setStart();
		if (refresh)
			refresh(null);
	}

	private void setStart() {
		try {
			TKCurrency cur = ((TravelApp) this.getActivity().getApplication())
					.getCurrencyManager().find("EUR");
			if (cur != null)
				setRefresh(true);
		} catch (SQLException ex) {
		}
	}

	public void refresh(View view) {
		try {

			if (view == null)
				view = (TextView) getActivity().findViewById(R.id.amount1);
			String str = ((TextView) view).getText().toString();
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
			double course = ((TravelApp) getActivity().getApplication())
					.getCurrencyManager().find(code).getCourse();
			long usdamount = Math.round(amount / (course / 1000000.0));

			if (view.getId() != R.id.amount1)
				((TextView) getActivity().findViewById(R.id.amount1))
						.setText(getResult(usdamount,
								pref.getString("convert1", "USD")));
			if (view.getId() != R.id.amount2)
				((TextView) getActivity().findViewById(R.id.amount2))
						.setText(getResult(usdamount,
								pref.getString("convert2", "EUR")));
			if (view.getId() != R.id.amount3)
				((TextView) getActivity().findViewById(R.id.amount3))
						.setText(getResult(usdamount,
								pref.getString("convert3", "CNY")));
			if (view.getId() != R.id.amount4)
				((TextView) getActivity().findViewById(R.id.amount4))
						.setText(getResult(usdamount,
								pref.getString("convert4", "JPY")));
			if (view.getId() != R.id.amount5)
				((TextView) getActivity().findViewById(R.id.amount5))
						.setText(getResult(usdamount,
								pref.getString("convert5", "GBP")));

		} catch (SQLException ex) {
			Log.e("CurrencyCalculatorActivity", ex.getMessage());
		}
	}

	private String getResult(long usdamount, String code) throws SQLException {
		return ""
				+ Math.round(usdamount
						* ((TravelApp) getActivity().getApplication())
								.getCurrencyManager().find(code).getCourse()
						/ 1000000.0) / 100.0;
	}

	public void onCurrencyClick(View view) {
		int id = view.getId();
		Intent i = new Intent(this.getActivity(), CurrencyActivity.class);

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

		this.getActivity().startActivity(i);
	}

	@Override
	public void onResume() {
		super.onResume();

		Button btn1 = (Button) this.getActivity().findViewById(R.id.btn1);
		btn1.setText(pref.getString("convert1", "USD"));
		Button btn2 = (Button) this.getActivity().findViewById(R.id.btn2);
		btn2.setText(pref.getString("convert2", "EUR"));
		Button btn3 = (Button) this.getActivity().findViewById(R.id.btn3);
		btn3.setText(pref.getString("convert3", "CNY"));
		Button btn4 = (Button) this.getActivity().findViewById(R.id.btn4);
		btn4.setText(pref.getString("convert4", "JPY"));
		Button btn5 = (Button) this.getActivity().findViewById(R.id.btn5);
		btn5.setText(pref.getString("convert5", "GBP"));

		if (refresh)
			refresh(null);
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
