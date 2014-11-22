package com.apartment.management.dao;

import com.apartment.management.dto.UserDTO;


public interface LoginDao {
	public boolean isUserExist(String emailId);
	public String getPasswordForUser(String emailId);
	public UserDTO getUserDetails(String userName);
}
