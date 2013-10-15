package com.droidbrew.travelkeeper.spec.model.db;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class TestDbHelper {
	private static final String DB_NAME = "unit_tests.db";
	private ConnectionSource connectionSource;
	private String databaseUrl = "jdbc:sqlite:" + DB_NAME;

	// create a connection source to our database

	public ConnectionSource getConnectionSource() {
		try {
			connectionSource = new JdbcConnectionSource(databaseUrl);
			return connectionSource;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
