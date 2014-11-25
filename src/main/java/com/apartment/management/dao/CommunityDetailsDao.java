package com.apartment.management.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;

public interface CommunityDetailsDao {
	@Transactional(propagation = Propagation.REQUIRED)
	public long saveCommunityDetails(CommunityDTO communityDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public long saveBuildingDetails(BuildingDTO buildingDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public long updateCommunityDetails(CommunityDTO communityDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public long updateBuildingDetails(BuildingDTO buildingDTO,String buildingName);

}
