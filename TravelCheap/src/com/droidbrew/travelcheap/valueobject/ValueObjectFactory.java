package com.droidbrew.travelcheap.valueobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelkeeper.model.entity.Expense;

public class ValueObjectFactory {
	Application app;
	
	Map<String, Integer> pictureMap = new HashMap<String, Integer>();
	
	public ValueObjectFactory(Application app){
		this.app = app;
		
		pictureMap.put("food", R.drawable.icon_food_7);
		pictureMap.put("transport", R.drawable.icon_transport_6);
		pictureMap.put("shopping", R.drawable.icon_shopping_11);
		pictureMap.put("accommodation", R.drawable.icon_hotel_14);
		pictureMap.put("entertainment", R.drawable.icon_entertainment_9);
		pictureMap.put("other things", R.drawable.icon_other_9);
		
	}
	
	public List<ExpenseDayTotal> getExpenceDayTotals(long date){
		
		List<ExpenseDayTotal> totals = new ArrayList<ExpenseDayTotal>();
		
		for(String pictureKey : pictureMap.keySet()){
			totals.add(new ExpenseDayTotal(0, pictureKey, date,
    			pictureMap.get(pictureKey), ((TravelApp)app).getExpenseManager()));
		}
		
    	return totals;
	}

	public List<ExpenseRecord> getExpenseRecords(long date){
		List<ExpenseRecord> expenseVOs = new ArrayList<ExpenseRecord>();

		for(Expense e : ((TravelApp)app).getExpenseManager().expensesByDate(date)){
			ExpenseRecord er = new ExpenseRecord(e.getId(), e.getDateAndTime(), e.getType(), pictureMap.get(e.getType()), e.getAmount()/100.0);
			expenseVOs.add(er);
		}
		
		return expenseVOs;
	}
}
