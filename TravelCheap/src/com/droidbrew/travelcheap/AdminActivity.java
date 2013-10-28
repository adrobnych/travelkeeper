package com.droidbrew.travelcheap;

import java.sql.SQLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.j256.ormlite.table.TableUtils;

public class AdminActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		setTitle("Administration");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
public void onWipeDataClick(View view){
		

	new AlertDialog.Builder(this)
	.setTitle("Are you sure?")
	.setMessage("ALL DATA WILL BE LOST")
	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) { 
			destroyAllData();
			finish();
		}})
	.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

}
