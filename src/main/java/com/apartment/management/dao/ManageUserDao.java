package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.CoOccupantDTO;
import com.apartment.management.dto.UserDTO;

public interface ManageUserDao {
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(UserDTO user);

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserDTO user);

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id);

	@Transactional(propagation = Propagation.REQUIRED)
	public String saveCoOccupent(CoOccupantDTO coOccupantDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCoOccupent(String coOccupentId);

	public List<UserDTO> findUsers(String firstName, String lastName, String emailId,
			String phoneNumber, String uidNumber);

	public UserDTO getUserDetailsById(long userId);

}
