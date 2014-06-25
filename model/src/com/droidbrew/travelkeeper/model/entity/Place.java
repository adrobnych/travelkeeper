package com.droidbrew.travelkeeper.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "places")
public class Place {

	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(columnName = "latitude", canBeNull = false)
	private Double latitude;
	@DatabaseField(columnName = "longitude", canBeNull = false)
	private Double longitude;
	@DatabaseField(columnName = "title", canBeNull = false, index = true, indexName = "title_index")
	private String title;
	@DatabaseField(columnName = "comment", canBeNull = false)
	private String comment;
	@DatabaseField(columnName = "like", canBeNull = false)
	private boolean like;
	@DatabaseField(columnName = "picture", canBeNull = false)
	private String picture;
	@DatabaseField(columnName = "imei", canBeNull = false)
	private String imei;
	@DatabaseField(columnName = "place_id", canBeNull = false, index = true, indexName = "placeid_index")
	private String placeId;

	public Place() {

	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public boolean getLike() {
		return like;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture() {
		return picture;
	}

	public void setIMEI(String imei) {
		this.imei = imei;
	}

	public String getIMEI() {
		return imei;
	}

	public void setPlaceId(String id) {
		this.placeId = id;
	}

	public String getPlaceId() {
		return placeId;
	}

}
