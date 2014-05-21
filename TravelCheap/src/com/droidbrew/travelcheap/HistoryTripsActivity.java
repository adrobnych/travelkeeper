package com.droidbrew.travelcheap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.droidbrew.travelcheap.fragment.HistoryTabFragments;

public class HistoryTripsActivity extends FragmentActivity {
	public Long dateValue = null;
	private static final int RESULT_SETTINGS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_two_activity);

		FragmentManager fm = getSupportFragmentManager();
		HistoryTabFragments tabFragment = (HistoryTabFragments) fm
				.findFragmentById(R.id.fragment_tab1);
		tabFragment.gotoHistoryView();
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
		Intent homeIntent = new Intent(HistoryTripsActivity.this,
				HomeActivity.class);
		HistoryTripsActivity.this.startActivity(homeIntent);
		finish();
	}
}
