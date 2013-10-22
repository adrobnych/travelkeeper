package com.droidbrew.travelcheap.valueobject;

import java.util.Date;

import android.app.Application;

import com.droidbrew.travelcheap.R;
import com.droidbrew.travelcheap.TravelApp;

public class ValueObjectFactory {
	Application app;
	
	public ValueObjectFactory(Application app){
		this.app = app;
	}
	
	public ExpenseDayTotal[] getExpenceDayTotals(){
		
		ExpenseDayTotal[] totals = {
		
    	new ExpenseDayTotal(0, "food", (new Date()).getTime(),
    			R.drawable.icon_food_7, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(1, "transport", (new Date()).getTime(), 
    			R.drawable.icon_transport_6, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(2, "shopping", (new Date()).getTime(), 
    			R.drawable.icon_shopping_11, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(3, "accommodation", (new Date()).getTime(),
    			R.drawable.icon_hotel_14, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(4, "entertainment", (new Date()).getTime(), 
    			R.drawable.icon_entertainment_9, ((TravelApp)app).getExpenseManager()),
    	new ExpenseDayTotal(5, "other things", (new Date()).getTime(), 
    			R.drawable.icon_other_9, ((TravelApp)app).getExpenseManager())
    	
		};
		
    	return totals;
	}


}
