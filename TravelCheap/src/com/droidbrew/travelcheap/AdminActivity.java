package com.droidbrew.travelcheap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Map;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.droidbrew.travelkeeper.model.manager.CurrencyHTTPHelper;
import com.j256.ormlite.table.TableUtils;

public class AdminActivity extends Activity {
	private ProgressDialog pd = null;

	public static final String LOG = "com.droidbrew.travelcheap.AdminActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		setTitle(R.string.action_administration);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onWipeDataClick(View view){


		new AlertDialog.Builder(this)
		.setTitle(R.string.AdminDialogTitle)
		.setMessage(R.string.AdminDialogMessage)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				destroyAllData();
				finish();
			}})
			.setNegativeButton(R.string.AdminDialogNB, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {finish();}})
				.show();
	}

	private void destroyAllData(){
		try {
			TableUtils.clearTable(((TravelApp)getApplication()).getDbHelper().getConnectionSource(),
					Expense.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void onUpdateCoursesClick(View view){
		
		CurrencyRemoteLoadTask clt = new CurrencyRemoteLoadTask();
		pd = ProgressDialog.show(this, getString(R.string.AdminDialogTitleUpd), getString(R.string.AdminDialogMassageUpd), 
				true, false);
		clt.execute();
	}
	
	class CurrencyRemoteLoadTask extends AsyncTask<Void, Void, Void> {
		private boolean success = false;

		@Override
		protected Void doInBackground(Void... params) {
			AssetManager am = getApplication().getAssets();
			CurrencyHTTPHelper cHTTPHelper = ((TravelApp)getApplication()).getCurrencyHTTPHelper();
			String xmlString = null;
			StringBuffer cnamesString = new StringBuffer();
			try {
				
				String entranceCode = ((TravelApp)getApplication()).getCurrencyManager().getEntranceCurrency();
				String reportCode = ((TravelApp)getApplication()).getCurrencyManager().getReportCurrency();

				xmlString = cHTTPHelper.getRemoteFullListAsXMLString();

				String line = null;
				InputStream is = am.open("currency_names.yml");
				BufferedReader breader = new BufferedReader(new InputStreamReader(is));
				while ((line = breader.readLine()) != null) {
					cnamesString.append(line + "\n");
				}

				Map<String, TKCurrency> cMap = cHTTPHelper.buildCurrencyMap(xmlString, 
						cnamesString.toString());

				TableUtils.clearTable(((TravelApp)getApplication()).getDbHelper().getConnectionSource(),
						TKCurrency.class);

				for(TKCurrency currency : cMap.values())
					((TravelApp)getApplication()).getCurrencyManager().create(currency);


				((TravelApp)getApplication()).getCurrencyManager().setAsEntranceCurrency(entranceCode);
				((TravelApp)getApplication()).getCurrencyManager().setAsReportCurrency(reportCode);
				
				success = true;
				
			} catch (Exception e) {
				Log.e(LOG, "courses update error: " + e);
				success = false;
			}

			return null;

		}
		

		@Override
		protected void onPostExecute(Void result) {
			if(success)
				Toast.makeText(getApplicationContext(), R.string.toastSuccessMessage,
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(), R.string.toastFailedMessage,
						Toast.LENGTH_LONG).show();
			pd.dismiss();
		}

	}

}
