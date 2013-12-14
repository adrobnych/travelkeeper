package com.droidbrew.travelkeeper.spec.model;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.droidbrew.travelkeeper.model.manager.CurrencyDBManager;
import com.droidbrew.travelkeeper.spec.model.db.TestDbHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class CurrencyDBHelperSpec {

	static CurrencyDBManager cm = null;
	static ConnectionSource connectionSource = null;

	@BeforeClass
    public static void setUpDatabaseLayer() throws SQLException {
        connectionSource = new TestDbHelper().getConnectionSource();
        TableUtils.createTableIfNotExists(connectionSource, TKCurrency.class);
        cm = new CurrencyDBManager();
		Dao<TKCurrency, String> currencyDao;
		try {
			currencyDao = DaoManager.createDao(connectionSource, TKCurrency.class);
			cm.setCurrencyDao(currencyDao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
	
	@Before
	public void clearCurrencies(){
		try {
			TableUtils.clearTable(connectionSource, TKCurrency.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void itShouldSaveCurrency(){
		
		TKCurrency currency = new TKCurrency("UAH", "Ukrainian Hryvnia", 8233000, false, false);
		
		try {
			cm.create(currency);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals("Ukrainian Hryvnia", cm.getCurrencyDao().queryForId("UAH").getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void itShouldFindCurrency(){
		
		TKCurrency currency = new TKCurrency("UAH", "Ukrainian Hryvnia", 8233000, false, false);
		
		try {
			cm.create(currency);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals("Ukrainian Hryvnia", cm.find("UAH").getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void itShouldUpdateCurrency(){
		
		TKCurrency currency = new TKCurrency("UAH", "Ukrainian Hryvnia", 8233000, false, false);
		
		try {
			cm.create(currency);
			cm.updateCourse("UAH", 8500000);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals(8500000, cm.find("UAH").getCourse());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void itShouldGetAllCurrencies(){
		TKCurrency currency1 = new TKCurrency("UAH", "Ukrainian Hryvnia", 8233000, false, true);
		TKCurrency currency2 = new TKCurrency("EUR", "Euro", 1233000, false, false);
		
		try {
			cm.create(currency1);
			cm.create(currency2);
		
			assertEquals(2, cm.getWholeList().size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void itShouldSetEntranceCurrency(){
		
		TKCurrency currency1 = new TKCurrency("UAH", "Ukrainian Hryvnia", 8233000, false, true);
		TKCurrency currency2 = new TKCurrency("EUR", "Euro", 1233000, false, false);
		
		try {
			cm.create(currency1);
			cm.create(currency2);
			
			assertEquals("UAH", cm.getEntranceCurrency());
			
			cm.setAsEntranceCurrency("EUR");

			assertEquals("EUR", cm.getEntranceCurrency());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void itShouldSetReportCurrency(){
		
		TKCurrency currency1 = new TKCurrency("UAH", "Ukrainian Hryvnia", 8233000, true, false);
		TKCurrency currency2 = new TKCurrency("EUR", "Euro", 1233000, false, false);
		
		try {
			cm.create(currency1);
			cm.create(currency2);
			
			assertEquals("UAH", cm.getReportCurrency());
			
			cm.setAsReportCurrency("EUR");

			assertEquals("EUR", cm.getReportCurrency());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
