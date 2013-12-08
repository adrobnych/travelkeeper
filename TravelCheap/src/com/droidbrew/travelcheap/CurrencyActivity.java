package com.droidbrew.travelcheap;

import java.sql.SQLException;

import com.droidbrew.travelcheap.adapter.CurrencyListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class CurrencyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		
		ListView cList = (ListView) findViewById(R.id.CurrencylistView);
		try {
			cList.setAdapter(new CurrencyListAdapter(this, ((TravelApp)getApplication()).getCurrencyManager().getWholeList()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.currency, menu);
		return true;
	}

}
