package com.droidbrew.travelcheap.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class TravelCheapDbHelper extends OrmLiteSqliteOpenHelper {
		private static final String TAG = "com.droidbrew.travelcheap.db.TravelCheapDbHelper";
	    private static final String DB_NAME = "travelapp.db";
	    private static final int DB_VERSION = 1;
	    private Context context;

	    public TravelCheapDbHelper(Context context) throws SQLException {
	        super(context, DB_NAME, null, DB_VERSION);
	        this.context = context;
	    }

	    @Override
	    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
	        try {

	            TableUtils.createTableIfNotExists(connectionSource, Expense.class);

	        } catch (java.sql.SQLException e) {
	            Log.e(TAG, "onCreate", e);
	        } 
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
	       
	    }

	    public Context getContext() {
	        return context;
	    }
	}