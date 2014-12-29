package com.apartment.management.dto;

public class FlatDTO {
  private String id;

  private String buildingId;

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

  private UserDTO ownerDetails = new UserDTO();

  private UserDTO tenantDetails = new UserDTO();

  private BuildingDTO buildingDTO = new BuildingDTO();

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(String buildingId) {
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

  public void setTwoWheelerParking(final String twoWheelerParking) {
    this.twoWheelerParking = twoWheelerParking;
  }

  public String getFourWheelerParking() {
    return fourWheelerParking;
  }

  public void setFourWheelerParking(final String fourWheelerParking) {
    this.fourWheelerParking = fourWheelerParking;
  }

  public int getTotalTwoWheelerParkings() {
    return totalTwoWheelerParkings;
  }

  public void setTotalTwoWheelerParkings(final int totalTwoWheelerParkings) {
    this.totalTwoWheelerParkings = totalTwoWheelerParkings;
  }

  public int getTotalFourWheelerParkings() {
    return totalFourWheelerParkings;
  }

  public void setTotalFourWheelerParkings(final int totalFourWheelerParkings) {
    this.totalFourWheelerParkings = totalFourWheelerParkings;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public UserDTO getOwnerDetails() {
    return ownerDetails;
  }

  public void setOwnerDetails(final UserDTO ownerDetails) {
    this.ownerDetails = ownerDetails;
  }

  public UserDTO getTenantDetails() {
    return tenantDetails;
  }

  public void setTenantDetails(final UserDTO tenantDetails) {
    this.tenantDetails = tenantDetails;
  }

  public BuildingDTO getBuildingDTO() {
    return buildingDTO;
  }

  public void setBuildingDTO(final BuildingDTO buildingDTO) {
    this.buildingDTO = buildingDTO;
  }

}
