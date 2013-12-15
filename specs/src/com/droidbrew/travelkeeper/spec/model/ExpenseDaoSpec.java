package com.droidbrew.travelkeeper.spec.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.spec.model.db.TestDbHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ExpenseDaoSpec {
	static Dao<Expense, Integer> eDao = null;
	static ConnectionSource connectionSource = null;
	static long eurToUsd = 736540;

	@BeforeClass
    public static void setUpDatabaseLayer() throws SQLException {
        connectionSource = new TestDbHelper().getConnectionSource();
        TableUtils.createTableIfNotExists(connectionSource, Expense.class);
       
		try {
			eDao = DaoManager.createDao(connectionSource, Expense.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }
	
	@Before
	public void clearExpenses(){
		try {
			TableUtils.clearTable(connectionSource, Expense.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void ExpenseCanBeStoredInDB(){
		
		Expense expense = new Expense();
		expense.setType("food");
		expense.setAmount(1200L);
		expense.setDateAndTime(123445L);
		expense.setCurrencyCode("EUR");
		expense.setUsdAmount(Math.round(1200/(eurToUsd/1000000.0)));
		
		try {
			eDao.create(expense);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertTrue(expense.getId() != null);
		
		Expense new_e = null;
		try {
			new_e = eDao.queryForId((Integer)expense.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(1200L, new_e.getAmount().longValue());
		
	}
	
	@Test
	public void ItSavesDateAndTimeofExpense(){
		Expense expense = new Expense();
		expense.setType("food");
		expense.setAmount(1200L);
		long now = System.currentTimeMillis();
		expense.setDateAndTime(now);
		expense.setCurrencyCode("EUR");
		expense.setUsdAmount(Math.round(1200/(eurToUsd/1000000.0)));
		
		try {
			eDao.create(expense);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense new_e = null;
		try {
			new_e = eDao.queryForId((Integer)expense.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(now, new_e.getDateAndTime().longValue());
		
	}
}
