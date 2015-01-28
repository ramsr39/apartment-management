package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.CoOccupantDTO;
import com.apartment.management.dto.UserDTO;
import com.apartment.management.dto.UserPrivilegeDTO;

public interface ManageUserDao {
  @Transactional(propagation = Propagation.REQUIRED)
  public String save(UserDTO user);

  @Transactional(propagation = Propagation.REQUIRED)
  public void update(UserDTO user);

  @Transactional(propagation = Propagation.REQUIRED)
  public void delete(String id);

  @Transactional(propagation = Propagation.REQUIRED)
  public String saveCoOccupent(CoOccupantDTO coOccupantDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteCoOccupent(String coOccupentId);

  public List<UserDTO>
      findUsers(String firstName, String lastName, String emailId, String phoneNumber, String uidNumber);

  public UserDTO getUserDetailsById(String userId);

  @Transactional(propagation = Propagation.REQUIRED)
  public String addRole(UserPrivilegeDTO userPrivilegeDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public String updateRole(UserPrivilegeDTO userPrivilegeDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteRole(String managementGroupId);

  public List<UserPrivilegeDTO> getCommunityRoles(String communityId);

  public List<UserPrivilegeDTO> getUserRoles(String userId);

  public boolean isUserResidentInCommunity(String communityId, String userId);

}
