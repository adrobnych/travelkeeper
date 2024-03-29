package com.droidbrew.travelcheap;

import java.sql.SQLException;

import com.droidbrew.travelcheap.adapter.CurrencyListAdapter;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CurrencyActivity extends Activity {
	
	private String mode = "expenses";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
	
		Intent i = getIntent();
		String extra = i.getStringExtra("type");
		if(extra != null){
			setTitle("select currency for your reports");
			mode = "reports";
		}
		else{
			setTitle("select currency for your expenses");
			mode = "expenses";
		}
		
		ListView cList = (ListView) findViewById(R.id.CurrencylistView);
		try {
			cList.setAdapter(new CurrencyListAdapter(this, ((TravelApp)getApplication()).getCurrencyManager().getWholeList()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				try {
					if(mode.equals("expenses"))
						((TravelApp)getApplication()).getCurrencyManager().setAsEntranceCurrency(
							((TKCurrency)l.getItemAtPosition(position)).getCode()
						);
					else
						((TravelApp)getApplication()).getCurrencyManager().setAsReportCurrency(
							((TKCurrency)l.getItemAtPosition(position)).getCode()
						);
//					Intent myIntent = new Intent(CurrencyActivity.this, HomeActivity.class);
//					CurrencyActivity.this.startActivity(myIntent);
					finish();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		});
	}


}
