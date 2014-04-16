package com.droidbrew.travelcheap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;



public class LanguageActivity extends PreferenceActivity {
	PendingIntent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		Preference restart = (Preference) findPreference("restart");
		
		intent = PendingIntent.getActivity(getApplicationContext(), 0,
	            new Intent(getIntent()), 0);

		
		restart.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, intent);
				System.exit(1);
				return true;
			}
		});
    }
}