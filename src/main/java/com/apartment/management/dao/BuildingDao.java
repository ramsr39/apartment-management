package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.BuildingDTO;

public interface BuildingDao {

 @Transactional(propagation = Propagation.REQUIRED)
 public long save(BuildingDTO buildingDTO);

@Transactional(propagation = Propagation.REQUIRED)
 public void update(BuildingDTO buildingDTO);

@Transactional(propagation = Propagation.REQUIRED)
 public void delete(long buiildingId);

 public List<BuildingDTO> findBuildingDetailsByCommunityId(long communityId);
 
 public BuildingDTO getBuildingDetailsByBuildingId(long buildingId);

}
