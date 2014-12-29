package com.apartment.management.dto;

import java.util.ArrayList;
import java.util.List;

public class CommunityDTO {

  private String id;

  private String name;

  private String country;

  private String city;

  private String state;

  private int postalCode;

  private String address1;

  private String address2;

  private String address3;

  private String type;

  private String description;

  private long totalFlats;

  private List<BuildingDTO> buildingList = new ArrayList<BuildingDTO>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(final String state) {
    this.state = state;
  }

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(final int postalCode) {
    this.postalCode = postalCode;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(final String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(final String address2) {
    this.address2 = address2;
  }

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(final String address3) {
    this.address3 = address3;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public long getTotalFlats() {
    return totalFlats;
  }

  public void setTotalFlats(final long totalFlats) {
    this.totalFlats = totalFlats;
  }

  public List<BuildingDTO> getBuildingList() {
    return buildingList;
  }

  public void setBuildingList(List<BuildingDTO> buildingList) {
    this.buildingList = buildingList;
  }

}
