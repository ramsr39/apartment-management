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

}
