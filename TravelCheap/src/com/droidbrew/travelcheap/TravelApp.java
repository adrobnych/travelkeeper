package com.droidbrew.travelcheap;

import java.sql.SQLException;

import android.app.Application;
import android.util.Log;

import com.droidbrew.travelcheap.db.TravelCheapDbHelper;
import com.droidbrew.travelcheap.valueobject.ValueObjectFactory;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.manager.ExpenseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class TravelApp extends Application {
		private static final String TAG = "com.droidbrew.travelcheap.TravelApp";
		private ExpenseManager expenseManager = null;
		private TravelCheapDbHelper dbHelper = null; 
		private ValueObjectFactory voFactory = null;
		
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

		public ExpenseManager getExpenseManager(){
			if (null == expenseManager) {
	           
	                expenseManager = new ExpenseManager();
	                Dao<Expense, Integer> expenseDao;
	        		try {
	        			expenseDao = DaoManager.createDao(dbHelper.getConnectionSource(), Expense.class);
	        			expenseManager.setExpenseDao(expenseDao);
	        		} catch (SQLException e) {
	        			Log.e(TAG, "getExpenseManager", e);
	        		}
	        	
	        }
	        return expenseManager;
		}


	}
