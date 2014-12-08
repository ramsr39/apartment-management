package com.apartment.management.dto;

import java.util.ArrayList;
import java.util.List;

public class BuildingDTO {
	private long id;
	private long communityId;
	private String name;
	private int totalUnits;
	private int totalFloors;
	private String imageUrl;
	private String communityName;
	private String country;
	private String city;
	private String state;
	private int postalCode;
	private String address1;
	private String address2;
	private String address3;
	private UserDTO ownerDetails = new UserDTO();
	private List<FlatDTO> flatList = new ArrayList<FlatDTO>();

	public long getId() {
		return id;
	}
	public void setId(final long id) {
		this.id = id;
	}
	
	public long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(long communityId) {
		this.communityId = communityId;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public int getTotalUnits() {
		return totalUnits;
	}
	public void setTotalUnits(final int totalUnits) {
		this.totalUnits = totalUnits;
	}
	public int getTotalFloors() {
		return totalFloors;
	}
	public void setTotalFloors(final int totalFloors) {
		this.totalFloors = totalFloors;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(final String communityName) {
		this.communityName = communityName;
	}
	public List<FlatDTO> getFlatList() {
		return flatList;
	}
	public void setFlatList(final List<FlatDTO> flatList) {
		this.flatList = flatList;
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
	public UserDTO getOwnerDetails() {
		return ownerDetails;
	}
	public void setOwnerDetails(final UserDTO ownerDetails) {
		this.ownerDetails = ownerDetails;
	}

}
