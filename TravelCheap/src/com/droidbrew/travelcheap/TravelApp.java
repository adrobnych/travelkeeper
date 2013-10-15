package com.droidbrew.travelcheap;

import android.app.Application;
import java.sql.SQLException;
import android.util.Log;

import com.droidbrew.travelcheap.db.TravelCheapDbHelper;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.manager.ExpenseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class TravelApp extends Application {
		private static final String TAG = "com.droidbrew.travelcheap.TravelApp";
		private ExpenseManager expenseManager = null;
		private TravelCheapDbHelper dbHelper = null; 
		
		public TravelCheapDbHelper getDbHelper() {
			return dbHelper;
		}

		public TravelApp() {
			super();
			dbHelper = new TravelCheapDbHelper(this);
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
