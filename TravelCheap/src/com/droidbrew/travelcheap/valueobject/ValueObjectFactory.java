package com.droidbrew.travelcheap.valueobject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.util.Log;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;
import com.droidbrew.travelkeeper.model.entity.Expense;
import com.droidbrew.travelkeeper.model.manager.TripManager;

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
			totals.add(new ExpenseDayTotal(0,((TravelApp)app).getTripManager().getDefaultTripId(), pictureKey,
					((TravelApp)app).getLanguageManager().getTypeFromDB(pictureKey), date,
    			pictureMap.get(pictureKey), ((TravelApp)app).getExpenseManager()));
		}
		
    	return totals;
	}

	public List<ExpenseTripTotal> getExpenceTripTotals(){
		
		List<ExpenseTripTotal> totals = new ArrayList<ExpenseTripTotal>();
		int tripId = ((TripManager) ((TravelApp)app).getTripManager()).getDefaultTripId();
		
		for(String pictureKey : pictureMap.keySet()){
			totals.add(new ExpenseTripTotal(0, pictureKey,
					((TravelApp)app).getLanguageManager().getTypeFromDB(pictureKey), tripId,
    			pictureMap.get(pictureKey), ((TravelApp)app).getExpenseManager()));
		}
		totals.add(new ExpenseTripTotal(1, "other things",
				((TravelApp)app).getLanguageManager().getMsg1(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		totals.add(new ExpenseTripTotal(2, "other things",
				((TravelApp)app).getLanguageManager().getMsg2(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		totals.add(new ExpenseTripTotal(3, "other things",
				((TravelApp)app).getLanguageManager().getMsg3(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		totals.add(new ExpenseTripTotal(4, "other things",
				((TravelApp)app).getLanguageManager().getMsg4(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		
    	return totals;
	}
	
public List<ExpenseTripTotal> getHistoricalExpenceTripTotals(){
		
		List<ExpenseTripTotal> totals = new ArrayList<ExpenseTripTotal>();
		int tripId = ((TravelApp)app).getHistoricalTripId();
		
		for(String pictureKey : pictureMap.keySet()){
			totals.add(new ExpenseTripTotal(0, pictureKey,
					((TravelApp)app).getLanguageManager().getTypeFromDB(pictureKey), tripId,
    			pictureMap.get(pictureKey), ((TravelApp)app).getExpenseManager()));
		}
		totals.add(new ExpenseTripTotal(1, "other things",
				((TravelApp)app).getLanguageManager().getMsg1(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		totals.add(new ExpenseTripTotal(2, "other things",
				((TravelApp)app).getLanguageManager().getMsg2(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		totals.add(new ExpenseTripTotal(3, "other things",
				((TravelApp)app).getLanguageManager().getMsg3(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		totals.add(new ExpenseTripTotal(4, "other things",
				((TravelApp)app).getLanguageManager().getMsg4(), tripId,
				pictureMap.get("other things"), ((TravelApp)app).getExpenseManager()));
		
    	return totals;
	}

	public List<ExpenseRecord> getExpenseRecords(long date){
		List<ExpenseRecord> expenseVOs = new ArrayList<ExpenseRecord>();

		for(Expense e : ((TravelApp)app).getExpenseManager().expensesByDate(date)){
			ExpenseRecord er = new ExpenseRecord(e.getId(), e.getDateAndTime(), 
					((TravelApp)app).getLanguageManager().getTypeFromDB(e.getType()), pictureMap.get(e.getType()), e.getCurrencyCode(), e.getAmount()/100.0, e.getTripId());
			expenseVOs.add(er);
		}
		
		return expenseVOs;
	}

	public List<ExpenseRecord> getExpenseRecordsByTrip(){
		List<ExpenseRecord> expenseVOs = new ArrayList<ExpenseRecord>();
		int id = ((TripManager) ((TravelApp)app).getTripManager()).getDefaultTripId();
		List<String> dates = ((TravelApp)app).getExpenseManager().datesByTrip(id);

		try {
		for(String date : dates) {
			long d = Long.parseLong(date);
			ExpenseRecord er = new ExpenseRecord(0, 
					d, "other", pictureMap.get("other things"), 
					((TravelApp)app).getCurrencyManager().getReportCurrency(), 
					((TravelApp)app).getExpenseManager().sumAmountByDateAndTrip(d,id)/100.0, 
					id);
			expenseVOs.add(er);
		}
		}catch(SQLException e) {
			Log.e("deb","getExpensesByTrip", e);
		}

		return expenseVOs;
	}
	
	public List<ExpenseRecord> getHistoricalExpenseRecordsByTrip(){
		List<ExpenseRecord> expenseVOs = new ArrayList<ExpenseRecord>();
		int id = ((TravelApp)app).getHistoricalTripId();
		List<String> dates = ((TravelApp)app).getExpenseManager().datesByTrip(id);

		try {
		for(String date : dates) {
			long d = Long.parseLong(date);
			ExpenseRecord er = new ExpenseRecord(0, 
					d, "other", pictureMap.get("other things"), 
					((TravelApp)app).getCurrencyManager().getReportCurrency(), 
					((TravelApp)app).getExpenseManager().sumAmountByDateAndTrip(d,id)/100.0, 
					id);
			expenseVOs.add(er);
		}
		}catch(SQLException e) {
			Log.e("deb","getExpensesByTrip", e);
		}

		return expenseVOs;
	}
}
