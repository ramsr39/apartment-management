package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.CommunityDTO;

public interface CommunityDetailsDao {
	@Transactional(propagation = Propagation.REQUIRED)
	public long save(String emailId,CommunityDTO communityDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public long update(CommunityDTO communityDTO);

	public CommunityDTO findCommunityDetailsByUserId(String emailId);

	public CommunityDTO getCommunityDetailsByCommunityId(long communityId);

	public boolean isCommnityExistedForUser(String emailId);

	public List<CommunityDTO> findCommunitiesByName(String emailId, String communityName);

	public List<CommunityDTO> findCommunitiesByCity(String emailId,
			String communityName, String city);

	public String getCommunityName(long communityId);

}
