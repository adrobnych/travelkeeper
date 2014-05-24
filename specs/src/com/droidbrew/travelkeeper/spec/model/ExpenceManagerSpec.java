package com.droidbrew.travelkeeper.spec.model;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.entity.TKCurrency;
import com.droidbrew.travelkeeper.model.entity.Trip;
import com.droidbrew.travelkeeper.model.manager.CurrencyDBManager;
import com.droidbrew.travelkeeper.model.manager.ExpenseManager;
import com.droidbrew.travelkeeper.model.manager.TripManager;
import com.droidbrew.travelkeeper.spec.model.db.TestDbHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ExpenceManagerSpec {
	static ExpenseManager em = null;
	static ConnectionSource connectionSource = null;
	static long eurToUsd = 736540;

	@BeforeClass
    public static void setUpDatabaseLayer() throws SQLException {
        connectionSource = new TestDbHelper().getConnectionSource();
        TableUtils.createTableIfNotExists(connectionSource, Expense.class);
        em = new ExpenseManager();
		Dao<Expense, Integer> expenseDao;
		try {
			expenseDao = DaoManager.createDao(connectionSource, Expense.class);
			em.setExpenseDao(expenseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TableUtils.createTableIfNotExists(connectionSource, TKCurrency.class);
		CurrencyDBManager cm = new CurrencyDBManager();
		Dao<TKCurrency, String> currencyDao;
		try {
			currencyDao = DaoManager.createDao(connectionSource, TKCurrency.class);
			cm.setCurrencyDao(currencyDao);
			TableUtils.clearTable(connectionSource, TKCurrency.class);
			TKCurrency currency = new TKCurrency("EUR", "Euro", 736540, true, true);
			cm.create(currency);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		em.setCurrencyManager(cm);
		
		TableUtils.dropTable(connectionSource, Trip.class, true);
		TableUtils.createTableIfNotExists(connectionSource, Trip.class);
		TripManager tm = new TripManager();
		Dao<Trip, Integer> tripDao;
		try {
			tripDao = DaoManager.createDao(connectionSource, Trip.class);
			tm.setTripDao(tripDao);
			//TableUtils.clearTable(connectionSource, Trip.class);
			Trip default_trip = new Trip();
			default_trip.setId(1);
			default_trip.setName("default trip");
			default_trip.setDefault(true);
			tripDao.create(default_trip);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		em.setTripManager(tm);
		
		
    }
	
	private long time1;
	
	@Before
	public void clearAndFeedExpenses(){
		try {
			TableUtils.clearTable(connectionSource, Expense.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense1 = new Expense();
		expense1.setTripId(1);
		expense1.setType("food");
		expense1.setAmount(1200L);
		time1 = 12000000000L;
		expense1.setDateAndTime(time1);
		expense1.setCurrencyCode("EUR");
		
		
		
		try {
			em.create(expense1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense2 = new Expense();
		expense2.setTripId(1);
		expense2.setType("food");
		expense2.setAmount(1000L);
		long time2 = 12000000100L;
		expense2.setDateAndTime(time2);
		expense2.setCurrencyCode("EUR");
		
		
		try {
			em.create(expense2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense3 = new Expense();
		expense3.setTripId(1);
		expense3.setType("food");
		expense3.setAmount(1000L);
		long time3 = 200000000000000100L;
		expense3.setDateAndTime(time3);
		expense3.setCurrencyCode("EUR");
		
		
		try {
			em.create(expense3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense4 = new Expense();
		expense4.setTripId(1);
		expense4.setType("transport");
		expense4.setAmount(1000L);
		expense4.setDateAndTime(time2);
		expense4.setCurrencyCode("EUR");

		
		try {
			em.create(expense4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void itShouldFetchSumOfExpensesByTypeAndDay(){
		
		assertEquals(2200L, em.sumAmountByTypeAndDate("food", time1).longValue());
		
	}
	
	@Test
	public void itShouldFetchSumOfExpensesByDay(){
		
		assertEquals(3200L, em.sumAmountByDate(time1).longValue());
		
	}
	
	@Test
	public void itShouldFetchListOfExpensesByDay(){
		
		assertEquals(1200L, em.expensesByDate(time1).get(0).getAmount().longValue());
		
	}
	
	@Test
	public void itShouldDeleteExpenseById(){
		int first_expense_id = -1;
		try {
			first_expense_id = em.getExpenseDao().queryForAll().get(0).getId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		em.deleteExpenseById(first_expense_id);
		
		Expense ex = null;
		
		try {
			ex = em.getExpenseDao().queryForId(first_expense_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(null, ex);
	}
	
	@Test
	public void itSouldKnowCodeOfCurrency(){
		int first_expense_id = -1;
		Expense ex = null;
		try {
			first_expense_id = em.getExpenseDao().queryForAll().get(0).getId();
			ex = em.getExpenseDao().queryForId(first_expense_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("EUR", ex.getCurrencyCode());
	}
	
	@Test
	public void itSouldKnowUSDEquivalent(){
		int first_expense_id = -1;
		Expense ex = null;
		try {
			first_expense_id = em.getExpenseDao().queryForAll().get(0).getId();
			ex = em.getExpenseDao().queryForId(first_expense_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(1200, ex.getAmount().longValue());
		assertEquals(1629, ex.getUsdAmount().longValue());
		
	}
	
}
