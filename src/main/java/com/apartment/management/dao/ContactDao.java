package com.apartment.management.dao;

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

}
