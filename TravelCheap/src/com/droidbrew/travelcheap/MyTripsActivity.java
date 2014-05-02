package com.droidbrew.travelcheap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.droidbrew.travelkeeper.model.entity.Trip;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class MyTripsActivity extends Activity {
	private static final String TAG = "myLogs";
	private static final int RESULT_SETTINGS = 1;
	
	List<String> trips;
	ArrayAdapter<String> adapter;
	ListView lView;
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_mytrips);
	    
	    
		  lView = (ListView) findViewById(R.id.LView);
		  lView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		  
		  
		  try {
          trips = new ArrayList<String>();

          
          adapter = new ArrayAdapter<String>(this,
				  android.R.layout.simple_list_item_single_choice, trips);
		  lView.setAdapter(adapter);
          setTripList();

		  } catch(SQLException e) {
			  e.printStackTrace();
		  }
		  		  
		 // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		//		  android.R.layout.simple_list_item_single_choice, names);
		//  lView.setAdapter(adapter);
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
	  
	  private void setTripList() throws SQLException {
		  trips.clear();
          Dao<Trip, String> tripDao =
                  DaoManager.createDao(
                		  ((TravelApp)getApplication()).getDbHelper().getConnectionSource()
                		  , Trip.class);
          List<Trip> list = tripDao.queryForAll();
          int def = 0;
          for(Trip tr : list) {
        	  trips.add(tr.getName());
        	  if(tr.isDefault())
        		  def = trips.indexOf(tr.getName());
          }
          adapter.notifyDataSetChanged();
          lView.setItemChecked(def, true);
		  
	  }
	  
	  public void onNewTripsClick(View view) {
		  AlertDialog.Builder alert = new AlertDialog.Builder(this);
		  alert.setTitle(R.string.myTripDialogNewTitle);
		  alert.setMessage(R.string.myTripDialogNewMassage);
		  
		
		  final EditText input = new EditText(this);
		  alert.setView(input);
		  
		  alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				try {
		            Dao<Trip, String> tripDao =
		                    DaoManager.createDao(
		                    		((TravelApp)getApplication()).getDbHelper().getConnectionSource()
		                    		, Trip.class);
		            tripDao.create(new Trip(input.getText().toString(), false));
		            setTripList();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			
			}
		});
		  
		  alert.setNegativeButton(R.string.AdminDialogNB, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO Auto-generated method stub
				
			}
		});
		  
		  
		  alert.show();
	  }
	  
	  public void onDeleteClick(View view) {
		 
		
		  Log.d(TAG,""+ lView.getCheckedItemPosition());
		 
		  //Log.d(TAG,lView.getSelectedItem().toString());
		  if(lView.getCheckedItemPosition() == -1)
			  return;
		  AlertDialog.Builder alert = new AlertDialog.Builder(this);
		  alert.setTitle(R.string.myTripDialodDelTitle);
		  alert.setMessage(R.string.myTripDialodDelMassage);
		  alert.setPositiveButton(R.string.dialogDelPB, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 try {
			            Dao<Trip, String> tripDao =
			                    DaoManager.createDao(
			                    		((TravelApp)getApplication()).getDbHelper().getConnectionSource()
			                    		, Trip.class);
			            Trip trip = tripDao.queryBuilder()
			            		.where().eq("name", trips.get(lView.getCheckedItemPosition())).query().get(0);
			            tripDao.delete(trip);
			            if(trip.isDefault()) {
				            trip = tripDao.queryForAll().get(0);
				            trip.setDefault(true);
				            tripDao.update(trip);
			            	
			            }
			            setTripList();
				 } catch(SQLException e) {
					 e.printStackTrace();
				 }
				
			}
		});
		
		alert.setNegativeButton(R.string.AdminDialogNB, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		alert.show();
		
	}
	  	  
	  public void onDefaultClick(View view) {
	  
		  if(lView.getCheckedItemPosition() == -1)
			  return;
		  AlertDialog.Builder alert = new AlertDialog.Builder(this);
		  alert.setTitle(R.string.myTripDialogDefaultTitle);
		  alert.setMessage(R.string.myTripDialogDefaultMassage);
		  
		  alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
		            Dao<Trip, String> tripDao =
		                    DaoManager.createDao(
		                    		((TravelApp)getApplication()).getDbHelper().getConnectionSource()
		                    		, Trip.class);
		            Trip trip = tripDao.queryBuilder()
		            		.where().eq("is_default", true).query().get(0);
		            trip.setDefault(false);
		            tripDao.update(trip);
		            
		            trip = tripDao.queryBuilder()
		            		.where().eq("name", trips.get(lView.getCheckedItemPosition())).query().get(0);
		            trip.setDefault(true);
		            tripDao.update(trip);
		            setTripList();
			  } catch(SQLException e) {
				  e.printStackTrace();
			  }
				
			}
		});
		  alert.setNegativeButton(R.string.AdminDialogNB, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			alert.show();
	  }
	  public void onHistoryClick(View view){
			Intent intent = new Intent(this, HistoryTripsActivity.class);
			startActivity(intent);
		}
	   
}
		  
	 

