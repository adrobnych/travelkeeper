package com.droidbrew.travelcheap.valueobject;

import java.util.Calendar;

import android.util.Log;

import com.droidbrew.travelkeeper.model.manager.ExpenseManager;

public class ExpenseTripTotal {
	private int id;
	private int tripId;
	private String type;
	private int picture;
	private String vtype;
	
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
	
	public ExpenseTripTotal(int id, String type, String vtype, int tripId, int imageResource, ExpenseManager em){
		this.id = id;
		this.type = type;
		this.tripId = tripId;
		this.picture = imageResource;
		this.em = em;
		this.vtype = vtype;
	}
	
	public double getAmount(){
		if(id!=0) {
			Calendar cal = Calendar.getInstance();
			Log.d("time", cal.getTime().toString());
			long today = cal.getTime().getTime();
			if(id==1) {
				cal.add(Calendar.DAY_OF_MONTH, -3);
				return em.getBeetwenDays(tripId, cal.getTime().getTime(), today)/100.0;
			}
			if(id==2) {
				cal.add(Calendar.DAY_OF_MONTH, -7);
				return em.getBeetwenDays(tripId, cal.getTime().getTime(), today)/100.0;
			}
			if(id==3) {
				cal.add(Calendar.MONTH, -1);
				return em.getBeetwenDays(tripId, cal.getTime().getTime(), today)/100.0;
			}

		}
		return 
				em.sumAmountByTypeAndTrip(type, tripId)/100.0;
	}

}
