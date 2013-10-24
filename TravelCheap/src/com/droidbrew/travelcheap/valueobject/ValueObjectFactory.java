package com.droidbrew.travelcheap.valueobject;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelkeeper.model.entity.Expense;

public class ValueObjectFactory {
	Application app;
	
	public ValueObjectFactory(Application app){
		this.app = app;
	}
	
	public ExpenseDayTotal[] getExpenceDayTotals(long date){
		
		ExpenseDayTotal[] totals = {
		
    	new ExpenseDayTotal(0, "food", date,
    			R.drawable.icon_food_7, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(1, "transport", date, 
    			R.drawable.icon_transport_6, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(2, "shopping", date, 
    			R.drawable.icon_shopping_11, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(3, "accommodation", date,
    			R.drawable.icon_hotel_14, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(4, "entertainment", date, 
    			R.drawable.icon_entertainment_9, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(5, "other things", date, 
    			R.drawable.icon_other_9, ((TravelApp)app).getExpenseManager())
    	
		};
		
    	return totals;
	}

	public List<ExpenseRecord> getExpenseRecords(long date){
		List<ExpenseRecord> expenseVOs = new ArrayList<ExpenseRecord>();

		for(Expense e : ((TravelApp)app).getExpenseManager().expensesByDate(date)){
			ExpenseRecord er = new ExpenseRecord(e.getId(), e.getDateAndTime(), e.getType(), R.drawable.icon_food_7, e.getAmount());
			expenseVOs.add(er);
		}
		
		return expenseVOs;
	}
}
