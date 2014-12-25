package com.apartment.management.dao.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.ContactDao;
import com.apartment.management.dao.UtilityDao;
import com.apartment.management.dto.BillDTO;
import com.apartment.management.dto.ContactDTO;
import com.apartment.management.dto.UtilityDTO;

public class UtilityDaoImpl extends SimpleJdbcDaoSupport implements UtilityDao {
	
   private ContactDao contactDao;

	private static final String INSER_UTILITY_QUERY= "INSERT INTO utility(UTILITY_ID,"
			+ "UTILITY_TYPE,"
			+ "LEVEL,"
			+ "SERVICE_NO,"
			+ "SERVICE_PROVIDER_NAME,"
			+ "REMIND_ME,"
			+ "PAID_BY,"
			+ "FLATID,"
			+ "BUILDINGID,"
			+ "COMMUNITYID,"
			+ "USERID) "
			+ "VALUES(:UTILITY_ID,"
			         + ":UTILITY_TYPE,"
			         + ":LEVEL,"
			         + ":SERVICE_NO,"
			         + ":SERVICE_PROVIDER_NAME,"
			         + ":REMIND_ME,"
			         + ":PAID_BY,"
			         + ":FLATID,"
			         + ":BUILDINGID,"
			         + ":COMMUNITYID,"
			         + ":USERID)";
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
	namedSqlParamSource.addValue("FLATID", utilityDTO.getFlatId());
	namedSqlParamSource.addValue("BUILDINGID", utilityDTO.getBuildingId());
	namedSqlParamSource.addValue("COMMUNITYID", utilityDTO.getCommunityId());
	namedSqlParamSource.addValue("USERID", utilityDTO.getUserId());
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

	public void setContactDao(final ContactDao contactDao) {
		this.contactDao = contactDao;
	}

}
