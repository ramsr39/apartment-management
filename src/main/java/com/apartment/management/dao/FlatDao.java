package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.FlatDTO;

public interface FlatDao {
	@Transactional(propagation = Propagation.REQUIRED)
	public String save(final FlatDTO flatDTO);

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(final String flatId);

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(final FlatDTO flatDTO);

	public List<FlatDTO> findFlatDetailsByBuildingId(String buildingId);

	public long getTotalNumberOfFlatsForBuilding(List<String> buildingId);

	public FlatDTO getFlatDetailsByFaltId(String flatId);

	public List<FlatDTO> getFlatDetailsByUser(String emailId);

}
