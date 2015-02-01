package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.CommunityDTO;

public interface CommunityDetailsDao {
  @Transactional(propagation = Propagation.REQUIRED)
  public String save(CommunityDTO communityDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void update(CommunityDTO communityDTO);

  public CommunityDTO findCommunityDetailsByUserId(String emailId);

  public List<String> getUserCommunityIds(String emailId);

  public CommunityDTO getCommunityDetailsByCommunityId(String communityId);

  public boolean isCommnityExistedForUser(String emailId);

  public List<CommunityDTO> findCommunitiesByName(String communityName);

  public List<CommunityDTO> findCommunitiesByCity(String communityName, String city);

  public String getCommunityName(String communityId);

}
