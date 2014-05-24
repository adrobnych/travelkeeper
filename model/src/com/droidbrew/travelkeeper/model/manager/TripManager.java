package com.droidbrew.travelkeeper.model.manager;

import java.sql.SQLException;
import java.util.List;

import com.droidbrew.travelkeeper.model.entity.Trip;
import com.j256.ormlite.dao.Dao;

public class TripManager {

	private Dao<Trip, Integer> tripDao = null;
	
	public Dao<Trip, Integer> getTripDao() {
		return tripDao;
	}

	public TripManager() {}
	
	public String getNameById(int id) {
		try {
        List<Trip> trip = tripDao.queryBuilder()
        		.where().eq("id", id).query();

        return trip.get(0).getName();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "trip error";
	}
	
	public void setTripDao(Dao<Trip, Integer> tripDao) {
		this.tripDao = tripDao;
	}
	
	public int getDefaultTripId() {
		int id = 0;
		
		try {
			id = tripDao.queryBuilder()
					.where().eq("is_default", true).query().get(0).getId();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
}
