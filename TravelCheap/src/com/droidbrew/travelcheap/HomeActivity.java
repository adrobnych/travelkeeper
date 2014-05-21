package com.droidbrew.travelcheap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbrew.travelcheap.fragment.CurrencyCalcFragment;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.droidbrew.travelkeeper.model.entity.Trip;
import com.droidbrew.travelkeeper.model.manager.CurrencyHTTPHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class HomeActivity extends FragmentActivity {

	private static final String LOG = "com.droidbrew.dfa.HomeActivity";
	private CaldroidFragment dialogCaldroidFragment;
	private CaldroidFragment caldroidFragment;
	private Bundle savedInstanceState;
	private TextView amount;
	private ProgressDialog pd = null;
	private PendingIntent intent;

	private static final int RESULT_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		savedInstanceState = _savedInstanceState;
		setContentView(R.layout.activity_home);
		amount = (TextView) findViewById(R.id.amount);
		loadCurrencies();

		intent = PendingIntent.getActivity(getApplicationContext(), 0,
				new Intent(getIntent()), 0);
		setTitle(((TravelApp) getApplication()).getTripManager().getNameById(
				((TravelApp) getApplication()).getTripManager()
						.getDefaultTripId()));
	}

	@Override
	protected void onResume() {
		super.onResume();
		setTitle(((TravelApp) getApplication()).getTripManager().getNameById(
				((TravelApp) getApplication()).getTripManager()
						.getDefaultTripId()));

		Button cButton = (Button) findViewById(R.id.button_currency);
		try {
			cButton.setText(((TravelApp) getApplication()).getCurrencyManager()
					.getEntranceCurrency());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadCurrencies() {
		try {
			if (((TravelApp) getApplication()).getCurrencyManager()
					.getWholeList("").size() == 0)
				loadCurrenciesFromAssets();
			Log.i("CURRENCY SIZE", ""
					+ ((TravelApp) getApplication()).getCurrencyManager()
							.getWholeList("").size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadCurrenciesFromAssets() {
		pd = ProgressDialog.show(this, getString(R.string.AdminDialogTitleUpd),
				getString(R.string.pdStartHomeActivity), true, false);
		CurrencyLoadTask clt = new CurrencyLoadTask();
		clt.execute();
	}

	public void onClick(View view) {
		String old_value = (String) amount.getText();
		String button_text = (String) ((Button) view).getText();
		String new_value = null;
		if (button_text.equals("C"))
			if (old_value.length() == 1)
				new_value = "0";
			else
				new_value = old_value.substring(0, old_value.length() - 1);
		else {
			if (old_value.equals("0"))
				old_value = "";
			if ((!button_text.equals(".") || (button_text.equals(".") && thisIsNotSecondDot(old_value)))
					&& thisIsNotThirdDigitAfterDot(old_value))
				new_value = old_value + button_text;
			else
				new_value = old_value;
		}
		amount.setText(new_value);
	}

	private boolean thisIsNotThirdDigitAfterDot(String amountLine) {
		if (amountLine.indexOf(".") == -1)
			return true;
		else
			return (amountLine.length() - amountLine.indexOf(".") <= 2);
	}

	private boolean thisIsNotSecondDot(String amountLine) {
		return (amountLine.indexOf(".") == -1);
	}

	private String getTag(String tagG) {
		String tag = tagG;
		if (tag.equals("Еда"))
			return "food";
		if (tag.equals("Транспорт"))
			return "transport";
		if (tag.equals("Жилье"))
			return "accommodation";
		if (tag.equals("Покупка товаров"))
			return "shopping";
		if (tag.equals("Развлечения"))
			return "entertainment";
		if (tag.equals("Другие вещи"))
			return "other things";
		return tag;
	}

	public void onExpenseClick(View view) {

		String amountLine = amount.getText().toString();

		String type = getTag((String) view.getTag());
		Expense expense = new Expense();
		expense.setType(type);
		expense.setAmount((long) (100 * Double.valueOf(amountLine)));
		final long time = (new Date()).getTime();
		expense.setDateAndTime(time);
		try {
			expense.setTripId(((TravelApp) getApplication()).getTripManager()
					.getDefaultTripId());
			expense.setCurrencyCode(((TravelApp) getApplication())
					.getCurrencyManager().getEntranceCurrency());
			if (!amountLine.equals("0")) {
				((TravelApp) getApplication()).getExpenseManager().create(
						expense);
			}

			new AlertDialog.Builder(this)
					.setTitle(composeDialogTitle(amountLine))
					.setMessage(
							getString(R.string.homeActivityDialogMessage)
									+ " "
									+ (((TravelApp) getApplication())
											.getExpenseManager()
											.sumAmountByTypeAndDate(
													type,
													((TravelApp) getApplication())
															.getTripManager()
															.getDefaultTripId(),
													time) / 100.0)
									+ " "
									+ ((TravelApp) getApplication())
											.getCurrencyManager()
											.getReportCurrency() + " "
									+ getString(R.string.dialogFor) + " "
									+ (String) view.getTag())

					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									amount.setText("0");
								}
							}).show();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String composeDialogTitle(String amountValue) {
		if (amountValue.equals("0"))
			return getString(R.string.calendar_massage);
		else
			return getString(R.string.composeDialogTitleElse);

	}

	final CaldroidListener listener = new CaldroidListener() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

		@Override
		public void onSelectDate(Date date, View view) {
			Intent historyIntent = new Intent(HomeActivity.this,
					HistoryActivity.class);
			historyIntent.putExtra("date", date.getTime());
			historyIntent.putExtra("tab", "totals");
			HomeActivity.this.startActivity(historyIntent);
			finish();
		}

		@Override
		public void onLongClickDate(Date date, View view) {
			Toast.makeText(getApplicationContext(),
					" " + formatter.format(date), Toast.LENGTH_SHORT).show();
		}
	};

	public void onHistoryClick(View view) {

		final Bundle state = savedInstanceState;
		caldroidFragment = new CaldroidFragment();

		dialogCaldroidFragment = new CaldroidFragment();
		dialogCaldroidFragment.setCaldroidListener(listener);

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
					getString(R.string.select_a_date));
		} else {
			Bundle bundle = new Bundle();
			bundle.putString(CaldroidFragment.DIALOG_TITLE,
					getString(R.string.select_a_date));
			dialogCaldroidFragment.setArguments(bundle);
		}

		List<String> dates = ((TravelApp) getApplication()).getExpenseManager()
				.datesByTrip(
						((TravelApp) getApplication()).getTripManager()
								.getDefaultTripId());
		for (String date : dates)
			dialogCaldroidFragment.setBackgroundResourceForDate(
					R.color.caldroid_holo_blue_light,
					new Date(Long.parseLong(date)));

		dialogCaldroidFragment.show(getSupportFragmentManager(), dialogTag);
	}

	public void onMyTripsClick(View view) {
		Intent intent = new Intent(this, MyTripsActivity.class);
		startActivity(intent);
	}

	public void onCurrencyCalculatorClick(View view) {
		Intent intent = new Intent(this, CurrencyCalculatorActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_for_home, menu);
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
		}
		return true;
	}

	public void onCurrencyClick(View view) {
		Intent myIntent = new Intent(HomeActivity.this, CurrencyActivity.class);
		HomeActivity.this.startActivity(myIntent);
	}

	class CurrencyLoadTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			AssetManager am = getApplication().getAssets();
			StringBuffer xmlString = new StringBuffer();
			StringBuffer cnamesString = new StringBuffer();
			try {
				InputStream is = am.open("quote.xml");
				BufferedReader breader = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = breader.readLine()) != null) {
					xmlString.append(line + "\n");
				}
				is = am.open("currency_names.yml");
				breader = new BufferedReader(new InputStreamReader(is));
				while ((line = breader.readLine()) != null) {
					cnamesString.append(line + "\n");
				}
			} catch (IOException e) {
				Log.e(LOG, "xml file read error: " + e);
			}

			CurrencyHTTPHelper currencyHTTPHelper = new CurrencyHTTPHelper();

			Map<String, TKCurrency> cMap = currencyHTTPHelper.buildCurrencyMap(
					xmlString.toString(), cnamesString.toString());

			try {
				for (TKCurrency currency : cMap.values())
					((TravelApp) getApplication()).getCurrencyManager().create(
							currency);

				((TravelApp) getApplication()).getCurrencyManager()
						.setAsEntranceCurrency("EUR");

			} catch (SQLException e) {
				e.printStackTrace();
			}

			CurrencyCalcFragment frag = (CurrencyCalcFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment_currency);
			if (frag != null)
				frag.setRefresh(true);
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
		}

	}
}
