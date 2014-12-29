package com.apartment.management.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.BuildingDTO;

public interface BuildingDao {

  @Transactional(propagation = Propagation.REQUIRED)
  public String save(BuildingDTO buildingDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void update(BuildingDTO buildingDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void delete(long buiildingId);

  public List<BuildingDTO> findBuildingDetailsByCommunityId(String communityId);

  public BuildingDTO getBuildingDetailsByBuildingId(String buildingId);

  public String getBuildingNameById(final String buildingId);

  public Map<String, String> getBuildingCommunityById(final String buildingId);

}
