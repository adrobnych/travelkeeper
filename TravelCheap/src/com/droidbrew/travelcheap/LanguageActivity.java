package com.droidbrew.travelcheap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class LanguageActivity extends PreferenceActivity {
	PendingIntent intent;
	private static final int RESULT_SETTINGS = 2;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		Preference enrestart = (Preference) findPreference("enrestart");
		Preference rurestart = (Preference) findPreference("rurestart");

		intent = PendingIntent.getActivity(getApplicationContext(), 0,
				new Intent(LanguageActivity.this, HomeActivity.class), 0);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		final SharedPreferences.Editor editor = preferences.edit();

		enrestart
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						editor.putString("lang", "en");
						editor.commit();
						restart();
						return true;
					}
				});

		rurestart
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						editor.putString("lang", "ru");
						editor.commit();
						restart();
						return true;
					}
				});
	}

	private void restart() {
		AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, intent);
		System.exit(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_for_language, menu);
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
}