package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.BillDTO;

public interface BillDao {

  @Transactional(propagation = Propagation.REQUIRED)
  public String addBill(BillDTO billDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteBill(String billId);

  @Transactional(propagation = Propagation.REQUIRED)
  public String updateBill(BillDTO billDTO);

  public List<BillDTO> findUtilityBillsHistory(String utilityId);

  public List<BillDTO> findCommunityPendingBills(final String communityId);

  public List<BillDTO> findBuildingPendingBills(final String buildingId);

  public List<BillDTO> findFlatPendingBills(final String flatId);

  public List<BillDTO> findUserPendingBills(final String userId);

}
