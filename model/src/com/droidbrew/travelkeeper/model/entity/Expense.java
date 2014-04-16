package com.droidbrew.travelkeeper.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "expenses")
public class Expense {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "trip_id", canBeNull = false)
    private Integer tripId;
    @DatabaseField(columnName = "type", canBeNull = false, index = true, indexName = "type_index")
    private String type;
    @DatabaseField(columnName = "currency_code", canBeNull = false)
    private String currencyCode;
    @DatabaseField(columnName = "amount", canBeNull = false)
    private Long amount;
    @DatabaseField(columnName = "usd_amount", canBeNull = false)
    private Long usdAmount;
    public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Long getUsdAmount() {
		return usdAmount;
	}
	public void setUsdAmount(Long usdAmount) {
		this.usdAmount = usdAmount;
	}
	@DatabaseField(columnName = "date_and_time", canBeNull = false)
    private Long dateAndTime;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer id) {
		this.tripId = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Long dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
}
