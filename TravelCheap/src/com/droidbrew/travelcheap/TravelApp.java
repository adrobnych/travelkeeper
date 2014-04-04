package com.droidbrew.travelcheap;

import java.sql.SQLException;

import android.app.Application;
import android.util.Log;

import com.droidbrew.travelcheap.db.TravelCheapDbHelper;
import com.droidbrew.travelcheap.valueobject.ValueObjectFactory;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.droidbrew.travelkeeper.model.entity.Trip;
import com.droidbrew.travelkeeper.model.manager.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class TravelApp extends Application {
		private static final String TAG = "com.droidbrew.travelcheap.TravelApp";
		private ExpenseManager expenseManager = null;
		private CurrencyDBManager currencyManager = null;
		private TravelCheapDbHelper dbHelper = null; 
		private ValueObjectFactory voFactory = null;
		private CurrencyHTTPHelper currencyHTTPHelper = null;
		private TripManager tripManager = null;
		private LangManager langManager = null;
		
		public LangManager getLanguageManager() {

			if (null == langManager) {
				langManager = new LangManager(getResources().getConfiguration().locale
						.getLanguage());
			}
   
			return langManager;

		}
		
		public CurrencyHTTPHelper getCurrencyHTTPHelper() {
			
			if (null == currencyHTTPHelper) {
				currencyHTTPHelper = new CurrencyHTTPHelper();
			}
   
			return currencyHTTPHelper;
		}

		public ValueObjectFactory getVoFactory() {
			return voFactory;
		}

		public TravelCheapDbHelper getDbHelper() {
			return dbHelper;
		}

		public TravelApp() {
			super();
			dbHelper = new TravelCheapDbHelper(this);
			voFactory = new ValueObjectFactory(this);
		}
		
		public TripManager getTripManager() {
			if (null == tripManager) {
		           
                tripManager = new TripManager();
                try {
                Dao<Trip, String> tripDao =
                        DaoManager.createDao(
                      		  getDbHelper().getConnectionSource()
                      		  , Trip.class);
        		
        			tripManager.setTripDao(tripDao);
        		} catch (SQLException e) {
        			Log.e(TAG, "getExpenseManager", e);
        		}
        	
			}
			return tripManager;
		}

		public ExpenseManager getExpenseManager(){
			if (null == expenseManager) {
	           
	                expenseManager = new ExpenseManager();
	                Dao<Expense, Integer> expenseDao;
	        		try {
	        			expenseDao = DaoManager.createDao(dbHelper.getConnectionSource(), Expense.class);
	        			expenseManager.setExpenseDao(expenseDao);
	        			expenseManager.setCurrencyManager(getCurrencyManager());
	        		} catch (SQLException e) {
	        			Log.e(TAG, "getExpenseManager", e);
	        		}
	        	
	        }
	        return expenseManager;
		}
		
		public CurrencyDBManager getCurrencyManager(){
			if (null == currencyManager) {
	           
	                currencyManager = new CurrencyDBManager();
	                Dao<TKCurrency, String> currencyDao;
	        		try {
	        			currencyDao = DaoManager.createDao(dbHelper.getConnectionSource(), TKCurrency.class);
	        			currencyManager.setCurrencyDao(currencyDao);
	        		} catch (SQLException e) {
	        			Log.e(TAG, "getCurrencyManager", e);
	        		}
	        	
	        }
	        return currencyManager;
		}

	}
