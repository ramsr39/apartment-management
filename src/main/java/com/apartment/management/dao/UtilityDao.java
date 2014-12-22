package com.apartment.management.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.BillDTO;
import com.apartment.management.dto.UtilityDTO;

public interface UtilityDao {

	@Transactional(propagation = Propagation.REQUIRED)
	public UtilityDTO save(UtilityDTO utilityDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String utilityId);

	@Transactional(propagation = Propagation.REQUIRED)
	public String addBill(BillDTO billDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBill(String billId);
}
