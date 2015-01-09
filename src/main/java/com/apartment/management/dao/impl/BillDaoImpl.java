package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.BillDao;
import com.apartment.management.dto.BillDTO;

public class BillDaoImpl extends SimpleJdbcDaoSupport implements BillDao {

  private static final Logger LOG = LoggerFactory.getLogger(UtilityDaoImpl.class);
  
  private static final String INSERT_BILL_QUERY= "INSERT INTO bill(BILL_ID,"
      + "BILL_NO,"
      + "AMOUNT,"
      + "BILL_DATE,"
      + "DUE_DATE,"
      + "SERVICE_FROM,"
      + "SERVICE_TO,"
      + "REMIND_ME,"
      + "DESCRIPTION,"
      + "PAYMENT_DATE,"
      + "RECEIPT_NO,"
      + "UTILITY_ID) "
      + "VALUES(:ID,"
             + ":BILL_NO,"
             + ":AMOUNT,"
             + ":BILL_DATE,"
             + ":DUE_DATE,"
             + ":SERVICE_FROM,"
             + ":SERVICE_TO,"
             + ":REMIND_ME,"
             + ":DESCRIPTION,"
             + ":PAYMENT_DATE,"
                + ":RECEIPT_NO,"
             + ":UTILITY_ID)";

private static final String UPDATE_BILL_QUERY = "UPDATE bill SET BILL_NO=:BILL_NO,"
      + "BILL_DATE=:BILL_DATE,"
      + "AMOUNT=:AMOUNT,"
      + "DUE_DATE=:DUE_DATE,"
      + "REMIND_ME=:REMIND_ME,"
      + "SERVICE_TO=:SERVICE_TO,"
      + "SERVICE_FROM=:SERVICE_FROM,"
      + "DESCRIPTION=:DESCRIPTION,"
      + "PAYMENT_DATE=:PAYMENT_DATE,"
      + "RECEIPT_NO=:RECEIPT_NO"
      + " WHERE BILL_ID=:BILL_ID AND UTILITY_ID=:UTILITY_ID";

  private static final String DELETE_BILL_QUERY = "DELETE FROM bill WHERE BILL_ID=:BILL_ID";

  private static final String FIND_UTILITY_BILL_HISTORY_QUERY = "SELECT *FROM bill WHERE UTILITY_ID=:UTILITY_ID";

  private static final String FIND_COMMUNITY_UTILITY_ID =
    "SELECT UTILITY_ID FROM utility WHERE COMMUNITY_ID=:COMMUNITY_ID";

  private static final String FIND_BUILDING_UTILITY_ID =
    "SELECT UTILITY_ID FROM utility WHERE BUILDING_ID=:BUILDING_ID";

  private static final String FIND_FLAT_UTILITY_ID = "SELECT UTILITY_ID FROM utility WHERE FLAT_ID=:FLAT_ID";

  private static final String FIND_USER_UTILITY_ID = "SELECT UTILITY_ID FROM utility WHERE USER_ID=:USER_ID";

  private static final String FIND_UTILITY_PENDING_BILLS =
    "SELECT *FROM bill WHERE UTILITY_ID=:UTILITY_ID AND RECEIPT_NO IS NULL";
  
  private static final String GET_BILL_SERVICE_TO_FROM_DATE_QUERY = "SELECT BILL_ID,SERVICE_FROM, SERVICE_TO FROM bill "
      + "WHERE SERVICE_TO=(SELECT MAX(SERVICE_TO) FROM bill WHERE UTILITY_ID=:UTILITY_ID)";


  @Override
  public String addBill(final BillDTO billDTO) {
    LOG.info("addBill started for billId for utility::" + billDTO.getUtilityId());
    final String billId = "BI" + RandomStringUtils.randomNumeric(8);
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("ID", billId);
    namedSqlParamSource.addValue("BILL_NO", billDTO.getBillNumber());
    namedSqlParamSource.addValue("BILL_DATE", billDTO.getBillDate());
    namedSqlParamSource.addValue("AMOUNT", billDTO.getAmount());
    namedSqlParamSource.addValue("DUE_DATE", billDTO.getDueDate());
    namedSqlParamSource.addValue("REMIND_ME", billDTO.getRemindMe());
    namedSqlParamSource.addValue("SERVICE_TO", billDTO.getServiceTo());
    namedSqlParamSource.addValue("SERVICE_FROM", billDTO.getServiceFrom());
    namedSqlParamSource.addValue("DESCRIPTION", billDTO.getDescription());
    namedSqlParamSource.addValue("PAYMENT_DATE", billDTO.getPaymentDate());
    namedSqlParamSource.addValue("RECEIPT_NO", billDTO.getReceiptNumber());
    namedSqlParamSource.addValue("UTILITY_ID", billDTO.getUtilityId());
    getSimpleJdbcTemplate().update(INSERT_BILL_QUERY, namedSqlParamSource);
    LOG.info("addBill completed" + billId);
    return billId;
  }

  @Override
  public void deleteBill(final String billId) {
    LOG.info("deleteBill started for billId::" + billId);
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("BILL_ID", billId);
    getSimpleJdbcTemplate().update(DELETE_BILL_QUERY, namedSqlParamSource);
    LOG.info("deleteBill completed for billId::" + billId);
  }

  @Override
  public String updateBill(BillDTO billDTO) {
    LOG.info("updateBill started for billId::" + billDTO.getId());
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("BILL_ID", billDTO.getId());
    namedSqlParamSource.addValue("BILL_NO", billDTO.getBillNumber());
    namedSqlParamSource.addValue("BILL_DATE", billDTO.getBillDate());
    namedSqlParamSource.addValue("AMOUNT", billDTO.getAmount());
    namedSqlParamSource.addValue("DUE_DATE", billDTO.getDueDate());
    namedSqlParamSource.addValue("REMIND_ME", billDTO.getRemindMe());
    namedSqlParamSource.addValue("SERVICE_TO", billDTO.getServiceTo());
    namedSqlParamSource.addValue("SERVICE_FROM", billDTO.getServiceFrom());
    namedSqlParamSource.addValue("DESCRIPTION", billDTO.getDescription());
    namedSqlParamSource.addValue("PAYMENT_DATE", billDTO.getPaymentDate());
    namedSqlParamSource.addValue("RECEIPT_NO", billDTO.getReceiptNumber());
    namedSqlParamSource.addValue("UTILITY_ID", billDTO.getUtilityId());
    getSimpleJdbcTemplate().update(UPDATE_BILL_QUERY, namedSqlParamSource);
    LOG.info("updateBill completed for billId::" + billDTO.getId());
    return billDTO.getId();
  }

  @Override
  public List<BillDTO> findUtilityBillsHistory(final String utilityId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("UTILITY_ID", utilityId);
      return getSimpleJdbcTemplate().query(FIND_UTILITY_BILL_HISTORY_QUERY, getBillRowMapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.info("no bills found for give utilityId:" + utilityId + "::" + er);
      return new ArrayList<BillDTO>();
    }
  }

  @Override
  public List<BillDTO> findCommunityPendingBills(final String communityId) {
    List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
      List<String> utilityIdList = getUtilityIds(namedSqlParamSource, FIND_COMMUNITY_UTILITY_ID);
      pendingbuillList = getPedingBills(utilityIdList);
    } catch (final EmptyResultDataAccessException er) {
      LOG.info("no pending bills found for give communityId:" + communityId + "::" + er);
      return pendingbuillList;
    }
    return pendingbuillList;
  }

  @Override
  public List<BillDTO> findBuildingPendingBills(final String buildingId) {
    List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("BUILDING_ID", buildingId);
      List<String> utilityIdList = getUtilityIds(namedSqlParamSource, FIND_BUILDING_UTILITY_ID);
      pendingbuillList = getPedingBills(utilityIdList);
    } catch (final EmptyResultDataAccessException er) {
      LOG.info("no pending bills found for given buildingId:" + buildingId + "::" + er);
      return pendingbuillList;
    }
    return pendingbuillList;
  }

  @Override
  public List<BillDTO> findFlatPendingBills(final String flatId) {
    List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("FLAT_ID", flatId);
      List<String> utilityIdList = getUtilityIds(namedSqlParamSource, FIND_FLAT_UTILITY_ID);
      pendingbuillList = getPedingBills(utilityIdList);
    } catch (final EmptyResultDataAccessException er) {
      LOG.info("no pending bills found for given flatId:" + flatId + "::" + er);
      return pendingbuillList;
    }
    return pendingbuillList;
  }

  @Override
  public List<BillDTO> findUserPendingBills(final String userId) {
    List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("USER_ID", userId);
      List<String> utilityIdList = getUtilityIds(namedSqlParamSource, FIND_USER_UTILITY_ID);
      pendingbuillList = getPedingBills(utilityIdList);
    } catch (final EmptyResultDataAccessException er) {
      LOG.info("no pending bills found for given flatId:" + userId + "::" + er);
      return pendingbuillList;
    }
    return pendingbuillList;
  }

  private List<BillDTO> getPedingBills(final List<String> utilityIdList) {
    final List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    for (String utilityId : utilityIdList) {
      namedSqlParamSource.addValue("UTILITY_ID", utilityId);
      try {
        pendingbuillList.addAll(getSimpleJdbcTemplate().query(FIND_UTILITY_PENDING_BILLS, getBillRowMapper(),
            namedSqlParamSource));
      } catch (final EmptyResultDataAccessException ex) {
        continue;
      }
    }
    return pendingbuillList;
  }

  private RowMapper<BillDTO> getBillRowMapper() {
    return new RowMapper<BillDTO>() {
      @Override
      public BillDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        BillDTO billDto = new BillDTO();
        billDto.setId(rs.getString("BILL_ID"));
        billDto.setBillNumber(rs.getString("BILL_NO"));
        billDto.setAmount(rs.getString("AMOUNT"));
        billDto.setBillDate(rs.getString("BILL_DATE"));
        billDto.setDueDate(rs.getString("DUE_DATE"));
        billDto.setServiceFrom(rs.getString("SERVICE_FROM"));
        billDto.setServiceTo(rs.getString("SERVICE_TO"));
        billDto.setRemindMe(rs.getString("REMIND_ME"));
        billDto.setDescription(rs.getString("DESCRIPTION"));
        billDto.setPaymentDate(rs.getString("PAYMENT_DATE"));
        billDto.setReceiptNumber(rs.getString("RECEIPT_NO"));
        billDto.setUtilityId(rs.getString("UTILITY_ID"));
        return billDto;
      }
    };
  }

  private List<String> getUtilityIds(final MapSqlParameterSource namedSqlParamSource, final String query) {
    List<String> utilityIdList = new ArrayList<String>();
    utilityIdList = getSimpleJdbcTemplate().query(query, new RowMapper<String>() {
      @Override
      public String mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return rs.getString("UTILITY_ID");
      }
    }, namedSqlParamSource);
    return utilityIdList;
  }

  @Override
  public List<BillDTO> getLastBillLastBillDetails(final String utilityId) {
    LOG.info("BillDaoImpl getLastBillLastBillDetails start.........."+utilityId);
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("UTILITY_ID", utilityId);
      return getSimpleJdbcTemplate().query(GET_BILL_SERVICE_TO_FROM_DATE_QUERY, new RowMapper<BillDTO>() {
        @Override
        public BillDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
          BillDTO billDto = new BillDTO();
          billDto.setId(rs.getString("BILL_ID"));
          billDto.setServiceFrom(rs.getString("SERVICE_FROM"));
          billDto.setServiceTo(rs.getString("SERVICE_TO"));
          return billDto;
        }
      },namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.error("emptu result found...for utilityId"+utilityId+"::"+er);
      return new ArrayList<BillDTO>();   
     }
  }

}
