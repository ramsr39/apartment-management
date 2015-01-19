package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.UtilityDTO;

public interface UtilityDao {
 
  @Transactional(propagation = Propagation.REQUIRED)
  public UtilityDTO save(UtilityDTO utilityDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void delete(String utilityId);
  
  @Transactional(propagation = Propagation.REQUIRED)
  public void update(UtilityDTO utilityDTO);

  public List<UtilityDTO> findUtilitiesByCommunityId(String communityId);

  public List<UtilityDTO> findUtilitiesByFlatId(String flatId, String paidBy);

  public List<UtilityDTO> findUtilitiesByBuildingId(String buildingId);

  public List<UtilityDTO> findUtilitiesByUserId(String userId);

  public UtilityDTO findUtilitiesByUtilityId(String utilityId);

  public List<UtilityDTO> findPendingUtilities(String userId, String communityId);

}
