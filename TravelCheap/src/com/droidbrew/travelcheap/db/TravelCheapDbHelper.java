package com.droidbrew.travelcheap.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.droidbrew.travelkeeper.model.entity.Trip;

public class TravelCheapDbHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG = "com.droidbrew.travelcheap.db.TravelCheapDbHelper";
	private static final String DB_NAME = "travelapp.db";
	private static final int DB_VERSION = 2;
	private Context context;

	public TravelCheapDbHelper(Context context) throws SQLException {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {

		try {

			TableUtils.createTableIfNotExists(connectionSource, Expense.class);
			TableUtils.createTableIfNotExists(connectionSource,
					TKCurrency.class);
			TableUtils.createTableIfNotExists(connectionSource, Trip.class);

			Dao<Trip, String> tripDao = DaoManager.createDao(connectionSource,
					Trip.class);
			tripDao.create(new Trip("default trip", true));

		} catch (java.sql.SQLException e) {
			Log.e(TAG, "onCreate", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {

		if (oldVersion == 1 && newVersion == 2) {
			try {
				TableUtils.createTableIfNotExists(connectionSource, Trip.class);

				Dao<Trip, String> tripDao = DaoManager.createDao(
						connectionSource, Trip.class);
				tripDao.create(new Trip("default trip", true));
				tripDao.executeRaw("ALTER TABLE `expenses` ADD COLUMN trip_id INTEGER DEFAULT '1'");

			} catch (java.sql.SQLException e) {
				Log.e(TAG, "onCreate", e);
				e.printStackTrace();
			}
		}
	}

	public Context getContext() {
		return context;
	}
}