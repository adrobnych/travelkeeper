package com.droidbrew.travelcheap.valueobject;

import java.util.Calendar;

import com.droidbrew.travelkeeper.model.manager.ExpenseManager;

public class ExpenseDayTotal {
	private int id;
	private long dayMillis;
	private String type;
	private String vtype;
	private int picture;
	
	public void setPicture(int picture) {
		this.picture = picture;
	}
	
	public int getPicture() {
		return picture;
	}

	private ExpenseManager em;	
	
	public void setExpenseManager(ExpenseManager em) {
		this.em = em;
	}
	
	public int getId() {
		return id;
	}

	public String getType() {
		return vtype;
	}
	
	public ExpenseDayTotal(int id, String type, String vtype, long dayMillis, int imageResource, ExpenseManager em){
		this.id = id;
		this.type = type;
		this.dayMillis = dayMillis;
		this.picture = imageResource;
		this.em = em;
		this.vtype = vtype;
	}
	
	public double getAmount(){
		return 
				em.sumAmountByTypeAndDate(type, dayMillis)/100.0;
	}
}
