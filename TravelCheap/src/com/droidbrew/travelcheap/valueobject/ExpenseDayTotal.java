package com.droidbrew.travelcheap.valueobject;

import android.app.Application;

import com.droidbrew.travelcheap.TravelApp;

public class ExpenseDayTotal {
	private int id;
	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	private long dayMillis;
	private String type;
	private Application app;
	
	public ExpenseDayTotal(int _id, String _type, long _dayMillis, Application _app){
		id = _id;
		type = _type;
		dayMillis = _dayMillis;
		app = _app;
	}
	
	public double getAmount(){
		return 
				((TravelApp)app).getExpenseManager().sumAmountByTypeAndDate(type, dayMillis)/100.0;
	}
}
