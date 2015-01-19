package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.ContactDTO;

public interface ContactDao {

  @Transactional(propagation = Propagation.REQUIRED)
  public String save(final ContactDTO contactDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void update(final ContactDTO contactDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void delete(final String contactId);

  public List<ContactDTO> findContactsByCommunityId(String communityId);

  public List<ContactDTO> findContactsByBuildingId(String buildingId);

  public List<ContactDTO> findContactsByFlatId(String flatId);

  public ContactDTO findContactsByUtilityId(String utilityId);

  public List<ContactDTO> findContactsByUserId(String userId);
  
  public List<ContactDTO> getPublicContactDetails();

  public ContactDTO getContactsByContactId(String id);

  public List<ContactDTO> findPendingContacts(String userId, String communityId);

}
