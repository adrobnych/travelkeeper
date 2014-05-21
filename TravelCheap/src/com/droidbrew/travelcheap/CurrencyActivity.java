package com.droidbrew.travelcheap;

import java.sql.SQLException;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.droidbrew.travelcheap.adapter.CurrencyListAdapter;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;

public class CurrencyActivity extends Activity {

	private String mode = "expenses";
	private SharedPreferences pref;
	private String searchText = "";
	private CurrencyListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);

		pref = this.getSharedPreferences("TravelApp", MODE_PRIVATE);
		final SharedPreferences.Editor editor = pref.edit();

		Intent i = getIntent();

		EditText et = (EditText) findViewById(R.id.search);
		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				searchText = s.toString();
				changeData();
			}
		});

		String extra = i.getStringExtra("type");
		final String convert = i.getStringExtra("convert");
		if (convert != null)
			mode = "convert";
		else {
			if (extra != null) {
				setTitle(R.string.currencyActivityTitle);
				mode = "reports";
			} else {
				setTitle(R.string.currencyActivityTitleElse);
				mode = "expenses";
			}
		}
		ListView cList = (ListView) findViewById(R.id.CurrencylistView);
		try {
			adapter = new CurrencyListAdapter(this,
					((TravelApp) getApplication()).getCurrencyManager()
							.getWholeList(searchText));
			cList.setAdapter(adapter);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long id) {
				try {
					if (mode.equals("expenses"))
						((TravelApp) getApplication()).getCurrencyManager()
								.setAsEntranceCurrency(
										((TKCurrency) l
												.getItemAtPosition(position))
												.getCode());
					if (mode.equals("reports"))
						((TravelApp) getApplication()).getCurrencyManager()
								.setAsReportCurrency(
										((TKCurrency) l
												.getItemAtPosition(position))
												.getCode());
					if (mode.equals("convert")) {
						editor.putString(convert, ((TKCurrency) l
								.getItemAtPosition(position)).getCode());
						editor.commit();
					}
					finish();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void changeData() {
		try {
			adapter.setData(((TravelApp) getApplication()).getCurrencyManager()
					.getWholeList(searchText));
			adapter.notifyDataSetChanged();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
