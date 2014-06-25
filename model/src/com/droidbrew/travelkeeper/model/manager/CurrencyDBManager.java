package com.droidbrew.travelkeeper.model.manager;

import java.sql.SQLException;
import java.util.List;

import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class CurrencyDBManager {
	private Dao<TKCurrency, String> currencyDao = null;

	public Dao<TKCurrency, String> getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(Dao<TKCurrency, String> currencyDao) {
		this.currencyDao = currencyDao;
	}

	public void create(TKCurrency currency) throws SQLException {
		getCurrencyDao().create(currency);
	}

	public TKCurrency find(String currencyCode) throws SQLException {
		TKCurrency currency = null;
		currency = getCurrencyDao().queryForId(currencyCode);
		return currency;
	}

	public void updateCourse(String currencyCode, long newCourse) throws SQLException {
		TKCurrency currency = getCurrencyDao().queryForId(currencyCode);
		currency.setCourse(newCourse);
		getCurrencyDao().update(currency);
	}

	public void setAsEntranceCurrency(String currencyCode) throws SQLException {
		List<TKCurrency> currenciesToRemoveFlag = 
			getCurrencyDao().queryBuilder().where().eq("selected_for_entrance", true).query();
		
		for(TKCurrency currency : currenciesToRemoveFlag){
			currency.setSelectedForEntrance(false);
			getCurrencyDao().update(currency);
		}
		
		TKCurrency newEntranceCurrency = find(currencyCode);
		newEntranceCurrency.setSelectedForEntrance(true);
		getCurrencyDao().update(newEntranceCurrency);
		
	}

	public String getEntranceCurrency() throws SQLException {
		String result = "EUR";
		TKCurrency currency = 
			getCurrencyDao().queryBuilder()
				.where().eq("selected_for_entrance", true).queryForFirst();
		if(null != currency)
		   result = currency.getCode();
		return result;
	}

	public List<TKCurrency> getWholeList(String search) throws SQLException {
		QueryBuilder<TKCurrency, String> qb = getCurrencyDao().queryBuilder();
		qb.where().like("name", "%" + search + "%").or()
				.like("code", "%" + search + "%");
		return qb.orderBy("name", true).query();
	}

	public String getReportCurrency() throws SQLException {
		String result = "EUR";
		TKCurrency currency = 
			getCurrencyDao().queryBuilder()
				.where().eq("selected_for_report", true).queryForFirst();
		if(null != currency)
		   result = currency.getCode();
		return result;
	}

	public void setAsReportCurrency(String currencyCode) throws SQLException {
		List<TKCurrency> currenciesToRemoveFlag = 
				getCurrencyDao().queryBuilder().where().eq("selected_for_report", true).query();
			
			for(TKCurrency currency : currenciesToRemoveFlag){
				currency.setSelectedForReport(false);
				getCurrencyDao().update(currency);
			}
			
			TKCurrency newReportCurrency = find(currencyCode);
			newReportCurrency.setSelectedForReport(true);
			getCurrencyDao().update(newReportCurrency);
		
	}
	
}
