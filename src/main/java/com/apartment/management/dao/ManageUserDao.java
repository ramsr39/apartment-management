package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.UserDTO;

public interface ManageUserDao {
	@Transactional(propagation = Propagation.REQUIRED)
	public long save(UserDTO user);

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserDTO user);

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id);

	public List<UserDTO> findUsers(String firstName, String lastName, String emailId,
			String phoneNumber, String uidNumber);
}
