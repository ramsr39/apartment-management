package com.apartment.management.dao;

import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;

public interface CommunityDetailsDao {
	public long saveCommunityDetails(CommunityDTO communityDTO);
	public BuildingDTO saveBuildingDetails(BuildingDTO buildingDTO);
}
