package com.droidbrew.travelcheap.valueobject;

public class ExpenseRecord {
	private int id;
	private long timeMillis;
	private String type;
	private int picture;
	private double amount;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTimeMillis() {
		return timeMillis;
	}
	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPicture() {
		return picture;
	}
	public void setPicture(int picture) {
		this.picture = picture;
	}
	public ExpenseRecord(int id, long timeMillis, String type, int picture, double amount) {
		this.id = id;
		this.timeMillis = timeMillis;
		this.type = type;
		this.picture = picture;
		this.amount = amount;
	}
	
}
