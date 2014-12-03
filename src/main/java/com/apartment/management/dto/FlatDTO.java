package com.apartment.management.dto;

public class FlatDTO {
	private long id;
	private long buildingId;
	private String unitNumber;
	private String floorNumber;
	private int unitSize;
	private int totalBedRooms;
	private int totalBathRooms;
	private int totalRooms;
	private String residentType;
	private String twoWheelerParking;
	private String fourWheelerParking;
	private int totalTwoWheelerParkings;
	private int totalFourWheelerParkings;
	private String description;
	
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(final long buildingId) {
		this.buildingId = buildingId;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(final String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(final String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(final int unitSize) {
		this.unitSize = unitSize;
	}

	public int getTotalBedRooms() {
		return totalBedRooms;
	}

	public void setTotalBedRooms(final int totalBedRooms) {
		this.totalBedRooms = totalBedRooms;
	}

	public int getTotalBathRooms() {
		return totalBathRooms;
	}

	public void setTotalBathRooms(final int totalBathRooms) {
		this.totalBathRooms = totalBathRooms;
	}

	public int getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(final int totalRooms) {
		this.totalRooms = totalRooms;
	}

	public String getResidentType() {
		return residentType;
	}

	public void setResidentType(final String residentType) {
		this.residentType = residentType;
	}

	public String getTwoWheelerParking() {
		return twoWheelerParking;
	}

	public void setTwoWheelerParking(String twoWheelerParking) {
		this.twoWheelerParking = twoWheelerParking;
	}

	public String getFourWheelerParking() {
		return fourWheelerParking;
	}

	public void setFourWheelerParking(String fourWheelerParking) {
		this.fourWheelerParking = fourWheelerParking;
	}

	public int getTotalTwoWheelerParkings() {
		return totalTwoWheelerParkings;
	}

	public void setTotalTwoWheelerParkings(int totalTwoWheelerParkings) {
		this.totalTwoWheelerParkings = totalTwoWheelerParkings;
	}

	public int getTotalFourWheelerParkings() {
		return totalFourWheelerParkings;
	}

	public void setTotalFourWheelerParkings(int totalFourWheelerParkings) {
		this.totalFourWheelerParkings = totalFourWheelerParkings;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
