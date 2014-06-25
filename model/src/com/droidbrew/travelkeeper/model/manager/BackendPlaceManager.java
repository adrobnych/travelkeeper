package com.droidbrew.travelkeeper.model.manager;

import java.sql.SQLException;
import java.util.List;

import com.droidbrew.travelkeeper.model.entity.Place;
import com.j256.ormlite.dao.Dao;

public class BackendPlaceManager {
	
	private Dao<Place, Integer> dao = null;
	
	public Dao<Place, Integer> getPlaceDao() {
		return dao;
	}
	
	public void setPlaceDao(Dao<Place, Integer> dao) {
		this.dao = dao;
	}
	
	public void create(Place place) throws SQLException {
		dao.create(place);
	}
	
	public List<Place> getAll() throws SQLException {
		return dao.queryForAll();
	}
	
	public List<Place> getByLocation(double lat, double lon) throws SQLException {
		return dao.queryBuilder().where().eq("latitude", lat).and().eq("longitude", lon).query();
	}
	
	public List<Place> getByLocation(String id) throws SQLException {
		return dao.queryBuilder().where().eq("place_id", id).query();
	}
	
	public boolean hasPlace(String imei, String id) throws SQLException {
		List<Place> list = dao.queryBuilder().where()
				.eq("imei", imei).and().eq("place_id", id)
				.query();
		if(list.size() > 0)
			return true;
		return false;
	}

}
