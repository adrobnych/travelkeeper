package com.droidbrew.travelkeeper.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "currencies")
public class TKCurrency {
	@DatabaseField(id = true)
	private String code;
	@DatabaseField(columnName = "name", canBeNull = false, index = true, indexName = "name_index")
	private String name;
	@DatabaseField(columnName = "course", canBeNull = false)
	private long course;
	@DatabaseField(columnName = "selected_for_report", indexName = "report_flag_index")
	private boolean selectedForReport;
	@DatabaseField(columnName = "selected_for_entrance", indexName = "entrance_flag_index")
	private boolean selectedForEntrance;
	
	public TKCurrency(){
	}
	
	public TKCurrency(String code, String name, long course,
		boolean selectedForReport, boolean selectedForEntrace) {
		super();
		this.code = code;
		this.name = name;
		this.course = course;
		this.selectedForReport = selectedForReport;
		this.selectedForEntrance = selectedForEntrace;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCourse() {
		return course;
	}

	public void setCourse(long course) {
		this.course = course;
	}

	public boolean isSelectedForReport() {
		return selectedForReport;
	}

	public void setSelectedForReport(boolean selectedForReport) {
		this.selectedForReport = selectedForReport;
	}

	public boolean isSelectedForEntrance() {
		return selectedForEntrance;
	}

	public void setSelectedForEntrance(boolean selectedForEntrance) {
		this.selectedForEntrance = selectedForEntrance;
	}
	
	
}
