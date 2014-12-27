package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.ContactDao;
import com.apartment.management.dao.UtilityDao;
import com.apartment.management.dto.BillDTO;
import com.apartment.management.dto.ContactDTO;
import com.apartment.management.dto.UtilityDTO;

public class UtilityDaoImpl extends SimpleJdbcDaoSupport implements UtilityDao {

	private static final Logger LOG = LoggerFactory.getLogger(UtilityDaoImpl.class);

	private ContactDao contactDao;

	private static final String INSER_UTILITY_QUERY= "INSERT INTO utility(UTILITY_ID,"
			+ "UTILITY_TYPE,"
			+ "LEVEL,"
			+ "SERVICE_NO,"
			+ "SERVICE_PROVIDER_NAME,"
			+ "REMIND_ME,"
			+ "PAID_BY,"
			+ "FLAT_ID,"
			+ "BUILDING_ID,"
			+ "COMMUNITY_ID,"
			+ "USER_ID) "
			+ "VALUES(:UTILITY_ID,"
			         + ":UTILITY_TYPE,"
			         + ":LEVEL,"
			         + ":SERVICE_NO,"
			         + ":SERVICE_PROVIDER_NAME,"
			         + ":REMIND_ME,"
			         + ":PAID_BY,"
			         + ":FLAT_ID,"
			         + ":BUILDING_ID,"
			         + ":COMMUNITY_ID,"
			         + ":USER_ID)";

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

	private static final String DELETE_BILL_QUERY = "DELETE FROM bill WHERE ID=:ID";

	private static final String DELETE_UTILITY_QUERY = "DELETE FROM utility WHERE UTILITY_ID=:UTILITY_ID";

	private static final String FIND_UTILITY_BILL_HISTORY_QUERY = "SELECT *FROM bill WHERE UTILITY_ID=:UTILITY_ID";

	private static final String FIND_UTILITIES_BY_FLAT_ID_QUERY = "SELECT *FROM utility WHERE FLAT_ID=:FLAT_ID";

	private static final String FIND_UTILITIES_BY_BUILDING_ID_QUERY = "SELECT *FROM utility WHERE BUILDING_ID=:BUILDING_ID";

	private static final String FIND_UTILITIES_BY_USER_ID_QUERY = "SELECT *FROM utility WHERE USER_ID=:USER_ID";

	private static final String FIND_UTILITIES_BY_UTILITY_ID_QUERY = "SELECT *FROM utility WHERE UTILITY_ID=:UTILITY_ID";

	private static final String FIND_UTILITIES_BY_COMMUNITY_ID = "SELECT *FROM utility WHERE COMMUNITY_ID=:COMMUNITY_ID";
	
	private static final String FIND_COMMUNITY_UTILITY_ID = "SELECT UTILITY_ID FROM utility WHERE COMMUNITY_ID=:COMMUNITY_ID";

	private static final String FIND_BUILDING_UTILITY_ID = "SELECT UTILITY_ID FROM utility WHERE BUILDING_ID=:BUILDING_ID";

	private static final String FIND_FLAT_UTILITY_ID = "SELECT UTILITY_ID FROM utility WHERE FLAT_ID=:FLAT_ID";

	private static final String FIND_USER_UTILITY_ID = "SELECT UTILITY_ID FROM utility WHERE USER_ID=:USER_ID";
	
	private static final String FIND_UTILITY_PENDING_BILLS="SELECT *FROM bill WHERE UTILITY_ID=:UTILITY_ID AND RECEIPT_NO IS NULL";


	@Override
	public UtilityDTO save(final UtilityDTO utilityDTO) {
		LOG.info("save utility started.............");
	final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
	final String utilityId = "UT"+RandomStringUtils.randomNumeric(8);
	namedSqlParamSource.addValue("UTILITY_ID", utilityId);
	namedSqlParamSource.addValue("UTILITY_TYPE", utilityDTO.getType());
	namedSqlParamSource.addValue("LEVEL", utilityDTO.getLevel());
	namedSqlParamSource.addValue("SERVICE_NO", utilityDTO.getServiceNumber());
	namedSqlParamSource.addValue("SERVICE_PROVIDER_NAME", utilityDTO.getServiceProviderName());
	namedSqlParamSource.addValue("REMIND_ME", utilityDTO.getRemindMe());
	namedSqlParamSource.addValue("PAID_BY", utilityDTO.getPaidBy());
	namedSqlParamSource.addValue("FLAT_ID", utilityDTO.getFlatId());
	namedSqlParamSource.addValue("BUILDING_ID", utilityDTO.getBuildingId());
	namedSqlParamSource.addValue("COMMUNITY_ID", utilityDTO.getCommunityId());
	namedSqlParamSource.addValue("USER_ID", utilityDTO.getUserId());
	getSimpleJdbcTemplate().update(INSER_UTILITY_QUERY, namedSqlParamSource);
	ContactDTO contactDTO = utilityDTO.getContactDTO();
	final String contactId = contactDao.save(contactDTO);
	contactDTO.setId(contactId);
	utilityDTO.setContactDTO(contactDTO);
	utilityDTO.setId(utilityId);
	LOG.info("save utility end::"+utilityId);
	return utilityDTO;
	}

	
	@Override
	public void delete(final String utilityId) {
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("UTILITY_ID", utilityId);
		getSimpleJdbcTemplate().update(DELETE_UTILITY_QUERY, namedSqlParamSource);
	}

	@Override
	public String addBill(final BillDTO billDTO) {
		LOG.info("addBill started for billId for utility::"+billDTO.getUtilityId());
		final String billId = "BI"+RandomStringUtils.randomNumeric(8);
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
		LOG.info("addBill completed"+billId);
		return billId;
	}

	@Override
	public String updateBill(BillDTO billDTO) {
		LOG.info("updateBill started for billId::"+billDTO.getId());
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
		LOG.info("updateBill completed for billId::"+billDTO.getId());
		return billDTO.getId();
	}

	@Override
	public void deleteBill(final String billId) {
		LOG.info("deleteBill started for billId::"+billId);
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("ID", billId);
		getSimpleJdbcTemplate().update(DELETE_BILL_QUERY, namedSqlParamSource);	
		LOG.info("deleteBill completed for billId::"+billId);
	}


	@Override
	public List<UtilityDTO> findUtilitiesByCommunityId(final String communityId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
		return getSimpleJdbcTemplate().query(FIND_UTILITIES_BY_COMMUNITY_ID, getUtilityRowmapper(),namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no utilities found with communityId:"+communityId+"::"+er);
			return new ArrayList<UtilityDTO>();
		}
	}

	@Override
	public List<UtilityDTO> findUtilitiesByFlatId(final String flatId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("FLAT_ID", flatId);
		return getSimpleJdbcTemplate().query(FIND_UTILITIES_BY_FLAT_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no utilities found with flatId:"+flatId+"::"+er);
			return new ArrayList<UtilityDTO>();
		}
	}


	@Override
	public List<UtilityDTO> findUtilitiesByBuildingId(final String buildingId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("BUILDING_ID", buildingId);
		return getSimpleJdbcTemplate().query(FIND_UTILITIES_BY_BUILDING_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no utilities found with buildingId:"+buildingId+"::"+er);
			return new ArrayList<UtilityDTO>();
		}
	}


	@Override
	public List<UtilityDTO> findUtilitiesByUserId(final String userId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("USER_ID", userId);
		return getSimpleJdbcTemplate().query(FIND_UTILITIES_BY_USER_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no utilities found with userId:"+userId+"::"+er);
			return new ArrayList<UtilityDTO>();
		}
	}


	@Override
	public UtilityDTO findUtilitiesByUtilityId(final String utilityId) {
	  try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("UTILITY_ID", utilityId);
		return getSimpleJdbcTemplate().queryForObject(FIND_UTILITIES_BY_UTILITY_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
	  }catch(final EmptyResultDataAccessException er){
		  LOG.info("no utilities found with userId:"+utilityId+"::"+er);
			return new UtilityDTO();
		}
	}

	@Override
	public List<BillDTO> findUtilityBillsHistory(final String utilityId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("UTILITY_ID", utilityId);
		return getSimpleJdbcTemplate().query(FIND_UTILITY_BILL_HISTORY_QUERY,getBillRowMapper(),namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no bills found for give utilityId:"+utilityId+"::"+er);
			return new ArrayList<BillDTO>();
		}
	}


	@Override
	public List<BillDTO> findCommunityPendingBills(final String communityId) {
		final List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
		try{
			final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
			namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
			List<String> utilityIdList = getUtilityId(namedSqlParamSource,FIND_COMMUNITY_UTILITY_ID);
			for(String utilityId:utilityIdList){
			  namedSqlParamSource.addValue("UTILITY_ID", utilityId);
			 pendingbuillList.addAll(getSimpleJdbcTemplate().query(FIND_UTILITY_PENDING_BILLS,getBillRowMapper(),namedSqlParamSource));
			 return pendingbuillList;
			}
			}catch(final EmptyResultDataAccessException er){
				LOG.info("no pending bills found for give communityId:"+communityId+"::"+er);
				return pendingbuillList;
			}
		return pendingbuillList;
	}

	@Override
	public List<BillDTO> findBuildingPendingBills(final String buildingId) {
		final List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
		try{
			final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
			namedSqlParamSource.addValue("BILDING_ID", buildingId);
			List<String> utilityIdList = getUtilityId(namedSqlParamSource, FIND_BUILDING_UTILITY_ID);
			for(String utilityId:utilityIdList){
			  namedSqlParamSource.addValue("UTILITY_ID", utilityId);
			 pendingbuillList.addAll(getSimpleJdbcTemplate().query(FIND_UTILITY_PENDING_BILLS,getBillRowMapper(),namedSqlParamSource));
			 return pendingbuillList;
			}
			}catch(final EmptyResultDataAccessException er){
				LOG.info("no pending bills found for given buildingId:"+buildingId+"::"+er);
				return pendingbuillList;
			}
		return pendingbuillList;
	}

	@Override
	public List<BillDTO> findFlatPendingBills(final String flatId) {
		final List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
		try{
			final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
			namedSqlParamSource.addValue("FLAT_ID", flatId);
			List<String> utilityIdList = getUtilityId(namedSqlParamSource, FIND_FLAT_UTILITY_ID);
			for(String utilityId:utilityIdList){
			  namedSqlParamSource.addValue("UTILITY_ID", utilityId);
			 pendingbuillList.addAll(getSimpleJdbcTemplate().query(FIND_UTILITY_PENDING_BILLS,getBillRowMapper(),namedSqlParamSource));
			 return pendingbuillList;
			}
			}catch(final EmptyResultDataAccessException er){
				LOG.info("no pending bills found for given flatId:"+flatId+"::"+er);
				return pendingbuillList;
			}
		return pendingbuillList;
	}

	@Override
	public List<BillDTO> findUserPendingBills(final String userId) {
		final List<BillDTO> pendingbuillList = new ArrayList<BillDTO>();
		try{
			final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
			namedSqlParamSource.addValue("USER_ID", userId);
			List<String> utilityIdList = getUtilityId(namedSqlParamSource, FIND_USER_UTILITY_ID);
			for(String utilityId:utilityIdList){
			  namedSqlParamSource.addValue("UTILITY_ID", utilityId);
			 pendingbuillList.addAll(getSimpleJdbcTemplate().query(FIND_UTILITY_PENDING_BILLS,getBillRowMapper(),namedSqlParamSource));
			 return pendingbuillList;
			}
			}catch(final EmptyResultDataAccessException er){
				LOG.info("no pending bills found for given flatId:"+userId+"::"+er);
				return pendingbuillList;
			}
		return pendingbuillList;
	}

	private RowMapper<BillDTO> getBillRowMapper() {
		return new RowMapper<BillDTO>() {
			@Override
			public BillDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
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

	private RowMapper<UtilityDTO> getUtilityRowmapper() {
		return new RowMapper<UtilityDTO>() {
			@Override
			public UtilityDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				UtilityDTO utilityDTO = new UtilityDTO();
				utilityDTO.setId(rs.getString("UTILITY_ID"));
				utilityDTO.setType(rs.getString("UTILITY_TYPE"));
				utilityDTO.setLevel(rs.getString("LEVEL"));
				utilityDTO.setServiceNumber(rs.getString("SERVICE_NO"));
				utilityDTO.setServiceProviderName(rs.getString("SERVICE_PROVIDER_NAME"));
				utilityDTO.setRemindMe(rs.getString("REMIND_ME"));
				utilityDTO.setPaidBy(rs.getString("PAID_BY"));
				utilityDTO.setCommunityId(rs.getString("COMMUNITY_ID"));
				utilityDTO.setBuildingId(rs.getString("BUILDING_ID"));
				utilityDTO.setFlatId(rs.getString("FLAT_ID"));
				utilityDTO.setUserId(rs.getString("USER_ID"));
				if(StringUtils.isNotBlank(utilityDTO.getId())){
				utilityDTO.setContactDTO(contactDao.findContactsByUtilityId(utilityDTO.getId()));
				}
				return utilityDTO;
			}
		};
	}

	private List<String> getUtilityId(final MapSqlParameterSource namedSqlParamSource,final String query) {
		List<String> utilityIdList = new ArrayList<String>();
		utilityIdList = getSimpleJdbcTemplate().query(query, new RowMapper<String>() {
			@Override
			public String mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				return rs.getString("UTILITY_ID");
			}
		},namedSqlParamSource);
		return utilityIdList;
	}

	public void setContactDao(final ContactDao contactDao) {
		this.contactDao = contactDao;
	}
}
