package com.droidbrew.travelkeeper.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "trips")
public class Trip {
    @DatabaseField(generatedId = true)
    private Integer id;
	@DatabaseField(columnName = "name", canBeNull = false, index = true, indexName = "name_index")
	private String name;
	@DatabaseField(columnName = "is_default", canBeNull = false)
	private boolean isDefault;
	

	public Trip() {
		
	}
	
	public Trip(String name, boolean isDefault) {
		this.name = name;
		this.isDefault = isDefault;
	}
	
	public boolean isDefault() {
		return this.isDefault;
	}
	
	public void setDefault(boolean def) {
		this.isDefault = def;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}