package com.droidbrew.travelkeeper.model.manager;

import java.sql.SQLException;
import java.util.Calendar;

import com.droidbrew.travelkeeper.model.entity.Expense;
import com.j256.ormlite.dao.Dao;

public class ExpenseManager {
	private Dao<Expense, Integer> expenseDao = null;

	public Dao<Expense, Integer> getExpenseDao() {
		return expenseDao;
	}

	public void setExpenseDao(Dao<Expense, Integer> expenseDao) {
		this.expenseDao = expenseDao;
	}

	public void create(Expense expense) throws SQLException {
		getExpenseDao().create(expense);	
	}

	public Long sumAmountByTypeAndDate(String type, long time_of_day) {
		
		try {
			return getExpenseDao().queryRawValue(
					"select sum(amount) from expenses where type = ? and date_and_time >= ? and date_and_time <= ?",
					type, firstMSecondOfTheDay(time_of_day), lastMSecondOfTheDay(time_of_day)
					);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String firstMSecondOfTheDay(long time_of_day_millis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time_of_day_millis);
	    cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
	    return "" + cal.getTime().getTime();
	}
	
	private String lastMSecondOfTheDay(long time_of_day_millis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time_of_day_millis);
	    cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
	    return "" + cal.getTime().getTime();
	}
	

}
