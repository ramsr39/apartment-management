package com.apartment.management.dao;

import java.util.List;

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

	public List<UtilityDTO> findUtilitiesByCommunityId(String communityId);

	public List<UtilityDTO> findUtilitiesByFlatId(String flatId);

	public List<UtilityDTO> findUtilitiesByBuildingId(String buildingId);

	public List<UtilityDTO> findUtilitiesByUserId(String userId);

	public UtilityDTO findUtilitiesByUtilityId(String utilityId);

	public List<BillDTO> findUtilityBillsHistory(String utilityId);

	public String updateBill(BillDTO billDTO);

	public List<BillDTO> findCommunityPendingBills(final String communityId);

	public List<BillDTO> findBuildingPendingBills(final String buildingId);

	public List<BillDTO> findFlatPendingBills(final String flatId);

	public List<BillDTO> findUserPendingBills(final String userId);

}
