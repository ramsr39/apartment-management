package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.FlatDTO;

public interface FlatDao {
	@Transactional(propagation = Propagation.REQUIRED)
	public long save(final FlatDTO flatDTO);
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(final long flatId);
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(final FlatDTO flatDTO);
	
	public List<FlatDTO> findFlatDetails(long buildingId);
}
