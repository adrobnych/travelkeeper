package com.droidbrew.travelkeeper.spec.model;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.manager.ExpenseManager;
import com.droidbrew.travelkeeper.spec.model.db.TestDbHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import static org.junit.Assert.assertEquals;

public class ExpenceManagerSpec {
	static ExpenseManager em = null;
	static ConnectionSource connectionSource = null;

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
		
    }
	
	private long time1;
	
	@Before
	public void clearExpenses(){
		try {
			TableUtils.clearTable(connectionSource, Expense.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense1 = new Expense();
		expense1.setType("food");
		expense1.setAmount(1200L);
		time1 = 12000000000L;
		expense1.setDateAndTime(time1);
		
		try {
			em.create(expense1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense2 = new Expense();
		expense2.setType("food");
		expense2.setAmount(1000L);
		long time2 = 12000000100L;
		expense2.setDateAndTime(time2);
		
		try {
			em.create(expense2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense3 = new Expense();
		expense3.setType("food");
		expense3.setAmount(1000L);
		long time3 = 200000000000000100L;
		expense3.setDateAndTime(time3);
		
		try {
			em.create(expense3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Expense expense4 = new Expense();
		expense4.setType("transport");
		expense4.setAmount(1000L);
		expense4.setDateAndTime(time2);
		
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
}
