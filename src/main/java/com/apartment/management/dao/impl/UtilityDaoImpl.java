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

	private static final String DELETE_BILL_QUERY = "DELETE FROM bill WHERE ID=:ID";

	private static final String DELETE_UTILITY_QUERY = "DELETE FROM utility WHERE UTILITY_ID=:UTILITY_ID";

	private static final String GET_UTILITY_BILL_HISTORY_QUERY = "SELECT *FROM bill WHERE UTILITY_ID=:UTILITY_ID";

	private static final String GET_UTILITIES_BY_FLAT_ID_QUERY = "SELECT *FROM utility WHERE FLAT_ID=:FALT_ID";

	private static final String GET_UTILITIES_BY_BUILDING_ID_QUERY = "SELECT *FROM utility WHERE BUILDING_ID=:BUILDING_ID";

	private static final String GET_UTILITIES_BY_USER_ID_QUERY = "SELECT *FROM utility WHERE USER_ID=:USER_ID";

	private static final String GET_UTILITIES_BY_UTILITY_ID_QUERY = "SELECT *FROM utility WHERE UTILITY_ID=:UTILITY_ID";

	private static final String GET_UTILITIES_BY_COMMUNITY_ID = "SELECT *FROM utility WHERE COMMUNITY_ID=:COMMUNITY_ID";

	@Override
	public UtilityDTO save(final UtilityDTO utilityDTO) {
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
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		final String billId = "BI"+RandomStringUtils.randomNumeric(8);
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
		return billId;
	}

	@Override
	public void deleteBill(final String billId) {
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("ID", billId);
		getSimpleJdbcTemplate().update(DELETE_BILL_QUERY, namedSqlParamSource);	
	}


	@Override
	public List<UtilityDTO> findUtilitiesByCommunityId(final String communityId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
		return getSimpleJdbcTemplate().query(GET_UTILITIES_BY_COMMUNITY_ID, getUtilityRowmapper(),namedSqlParamSource);
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
		return getSimpleJdbcTemplate().query(GET_UTILITIES_BY_FLAT_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
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
		return getSimpleJdbcTemplate().query(GET_UTILITIES_BY_BUILDING_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
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
		return getSimpleJdbcTemplate().query(GET_UTILITIES_BY_USER_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no utilities found with userId:"+userId+"::"+er);
			return new ArrayList<UtilityDTO>();
		}
	}


	@Override
	public List<UtilityDTO> findUtilitiesByUtilityId(final String utilityId) {
	  try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("UTILITY_ID", utilityId);
		return getSimpleJdbcTemplate().query(GET_UTILITIES_BY_UTILITY_ID_QUERY, getUtilityRowmapper(),namedSqlParamSource);
	  }catch(final EmptyResultDataAccessException er){
		  LOG.info("no utilities found with userId:"+utilityId+"::"+er);
			return new ArrayList<UtilityDTO>();
		}
	}

	@Override
	public List<BillDTO> getUtilityBillsHistory(final String utilityId) {
		try{
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("UTILITY_ID", utilityId);
		return getSimpleJdbcTemplate().query(GET_UTILITY_BILL_HISTORY_QUERY,new RowMapper<BillDTO>() {
			@Override
			public BillDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				BillDTO billDto = new BillDTO();
				billDto.setId(rs.getString("ID"));
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
				billDto.setUtilityId(utilityId);
				return billDto;
			}
		},namedSqlParamSource);
		}catch(final EmptyResultDataAccessException er){
			LOG.info("no bills found for give utilityId:"+utilityId+"::"+er);
			return new ArrayList<BillDTO>();
		}
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
				return utilityDTO;
			}
		};
	}


	public void setContactDao(final ContactDao contactDao) {
		this.contactDao = contactDao;
	}

}
