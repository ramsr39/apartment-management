package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.apartment.management.dao.ManageUserDao;
import com.apartment.management.dto.Address;
import com.apartment.management.dto.EmergencyContactInfo;
import com.apartment.management.dto.UserDTO;

public class ManagerUserDaoImpl extends NamedParameterJdbcDaoSupport implements ManageUserDao{
	
	private static final Logger LOG = LoggerFactory.getLogger(ManagerUserDaoImpl.class);
	
	private static final String INSERT_USER_QUERY  = "INSERT INTO userinfo(FIRSTNAME,"
			+ "LASTNAME,"
			+ "EMAILID,"
			+ "PASSWORD,"
			+ "PRIMARY_PH_NO,"
			+ "UID_TYPE,"
			+ "UID_NUMBER,"
			+ "DOB,"
			+ "SECONDERY_PH_NO,"
			+ "SECOUNDERY_EMAIL,"
			+ "BLOOD_GROUP,"
			+ "ADDRESS_LINE1,"
			+ "ADDRESS_LINE2,"
			+ "ADDRESS_LINE3,"
			+ "CITY,"
			+ "STATE,"
			+ "COUNTRY,"
			+ "PIN) "
			 + "values(:FIRSTNAME,"
			 + ":LASTNAME,"
			 + ":EMAILID,"
			 + ":PASSWORD,"
			 + ":PRIMARY_PH_NO,"
			 + ":UID_TYPE,"
			 + ":UID_NUMBER,"
			 + ":DOB,"
			 + "SECONDERY_PH_NO,"
			 + ":SECOUNDERY_EMAIL,"
			 + ":BLOOD_GROUP,"
			 + ":ADDRESS_LINE1,"
			 + ":ADDRESS_LINE2,"
			 + ":ADDRESS_LINE3,"
			 + ":CITY,"
			 + ":STATE,"
			 + ":COUNTRY,"
			 + ":PIN)";
	
	private static final String INSERT_EMERGENCY_CANT_INF_QUERY="INSERT INTO emrg_contact_info(NAME,REL,PHONE_NUM,EMAILID)"
			+ " values(:NAME,:REL,:PHONE_NUM,:EMAILID)";
	
	private static final String UPDATE_EMERGENCY_CANT_INF_QUERY="UPDATE emrg_contact_info SET "
			+ "NAME=:NAME,"
			+ "REL=:REL,"
			+ "PHONE_NUM=:PHONE_NUM "
			+ "WHERE EMAILID=:EMAILID";

	private static final String UPDATE_USER_QUERY = "UPDATE userinfo SET "
			+ "FIRSTNAME=:FIRSTNAME,"
			+ "LASTNAME=:LASTNAME,"
			+ "EMAILID=:EMAILID,"
			+ "PRIMARY_PH_NO=:PRIMARY_PH_NO,"
			+ "UID_TYPE=:UID_TYPE,"
			+ "UID_NUMBER=:UID_NUMBER,"
			+ "DOB=:DOB,"
			+ "SECONDERY_PH_NO=:SECONDERY_PH_NO,"
			+ "SECOUNDERY_EMAIL=:SECOUNDERY_EMAIL,"
			+ "BLOOD_GROUP=:BLOOD_GROUP,"
			+ "ADDRESS_LINE1=:ADDRESS_LINE1,"
			+ "ADDRESS_LINE2=:ADDRESS_LINE2,"
			+ "ADDRESS_LINE3=:ADDRESS_LINE3,"
			+ "CITY=:CITY,"
			+ "STATE=:STATE,"
			+ "COUNTRY=:COUNTRY WHERE USERID=:USERID";

  private static final String DELETE_USER_QUERY="DELETE FROM userinfo WHERE USERID=:USERID";
  
  private String FIND_USERS_FOR_SEARH_QUERY="SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo";
  
  private static final String GET_USER_DETAILS_BY_USERID_QUERY="SELECT * FROM userinfo WHERE USERID=:USERID" ;
	@Override
	public long save(final UserDTO user) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterSource.addValue("FIRSTNAME", user.getFirstName());
		namedParameterSource.addValue("LASTNAME", user.getLastName());
		namedParameterSource.addValue("EMAILID", user.getEmailId());
		namedParameterSource.addValue("PASSWORD", RandomStringUtils.randomAlphanumeric(6));
		namedParameterSource.addValue("PRIMARY_PH_NO", user.getPrimaryPhoneNumber());
		namedParameterSource.addValue("UID_TYPE", user.getUidType());
		namedParameterSource.addValue("UID_NUMBER", user.getUid());
		namedParameterSource.addValue("DOB", user.getDateOfBirth());
		namedParameterSource.addValue("SECONDERY_PH_NO", user.getSecondaryPhoneNumber());
		namedParameterSource.addValue("SECOUNDERY_EMAIL", user.getSecondaryEmail());
		namedParameterSource.addValue("BLOOD_GROUP", user.getBloodGroup());
		namedParameterSource.addValue("ADDRESS_LINE1", user.getAddress().getAddress1());
		namedParameterSource.addValue("ADDRESS_LINE2", user.getAddress().getAddress2());
		namedParameterSource.addValue("ADDRESS_LINE3", user.getAddress().getAddress3());
		namedParameterSource.addValue("CITY", user.getAddress().getCity());
		namedParameterSource.addValue("STATE", user.getAddress().getState());
		namedParameterSource.addValue("COUNTRY", user.getAddress().getCountry());
		namedParameterSource.addValue("PIN", user.getAddress().getPostalCode());
		getNamedParameterJdbcTemplate().update(INSERT_USER_QUERY, namedParameterSource,keyHolder);
		insertEmergencyContactInfo(user.getEmergencyContactInfo(),user.getEmailId());
		return keyHolder.getKey().longValue();
	}

	private void insertEmergencyContactInfo(final EmergencyContactInfo emrgCantInfo,final String emailId) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("NAME", emrgCantInfo.getName());		
		namedParameterSource.addValue("REL", emrgCantInfo.getRelation());
		namedParameterSource.addValue("PHONE_NUM", emrgCantInfo.getPhoneNumber());
		namedParameterSource.addValue("EMAILID", emailId);
		getNamedParameterJdbcTemplate().update(INSERT_EMERGENCY_CANT_INF_QUERY, namedParameterSource);
	}

	@Override
	public void update(final UserDTO user) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterSource.addValue("USERID", user.getUserId());		
		namedParameterSource.addValue("FIRSTNAME", user.getFirstName());
		namedParameterSource.addValue("LASTNAME", user.getLastName());
		namedParameterSource.addValue("EMAILID", user.getEmailId());
		namedParameterSource.addValue("PRIMARY_PH_NO", user.getPrimaryPhoneNumber());
		namedParameterSource.addValue("UID_TYPE", user.getUidType());
		namedParameterSource.addValue("UID_NUMBER", user.getUid());
		namedParameterSource.addValue("DOB", user.getDateOfBirth());
		namedParameterSource.addValue("SECONDERY_PH_NO", user.getSecondaryPhoneNumber());
		namedParameterSource.addValue("SECOUNDERY_EMAIL", user.getSecondaryEmail());
		namedParameterSource.addValue("BLOOD_GROUP", user.getBloodGroup());
		namedParameterSource.addValue("ADDRESS_LINE1", user.getAddress().getAddress1());
		namedParameterSource.addValue("ADDRESS_LINE2", user.getAddress().getAddress2());
		namedParameterSource.addValue("ADDRESS_LINE3", user.getAddress().getAddress3());
		namedParameterSource.addValue("CITY", user.getAddress().getCity());
		namedParameterSource.addValue("STATE", user.getAddress().getState());
		namedParameterSource.addValue("COUNTRY", user.getAddress().getCountry());
		namedParameterSource.addValue("PIN", user.getAddress().getPostalCode());
		getNamedParameterJdbcTemplate().update(UPDATE_USER_QUERY, namedParameterSource,keyHolder);
		updateEmergencyContactInfo(user.getEmergencyContactInfo(),user.getEmailId());
	}

	private void updateEmergencyContactInfo(final EmergencyContactInfo enrgContactInfo,final String emailId) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("NAME",enrgContactInfo.getName());
		namedParameterSource.addValue("REL", enrgContactInfo.getRelation());
		namedParameterSource.addValue("PHONE_NUM", enrgContactInfo.getPhoneNumber());
		namedParameterSource.addValue("EMAILID", emailId);
		getNamedParameterJdbcTemplate().update(UPDATE_EMERGENCY_CANT_INF_QUERY, namedParameterSource);
	}

	@Override
	public void delete(final long userId) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("USERID", userId);
		getNamedParameterJdbcTemplate().update(DELETE_USER_QUERY, namedParameterSource);		
	}

	@Override
	public List<UserDTO> findUsers(final String firstName, final String lastName, final String emailId,
			final String phoneNumber, final String uidNumber) {
		StringBuilder queryBuilder = new StringBuilder(FIND_USERS_FOR_SEARH_QUERY);
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		queryBuilder = buildQuery(firstName, lastName, emailId, phoneNumber, uidNumber,
				queryBuilder);
		namedParameterSource.addValue("FIRSTNAME", firstName);
		namedParameterSource.addValue("LASTNAME", lastName);
		namedParameterSource.addValue("EMAILID", emailId);
		namedParameterSource.addValue("PRIMARY_PH_NO", phoneNumber);
		namedParameterSource.addValue("UID_NUMBER", uidNumber);
		return getNamedParameterJdbcTemplate().query(queryBuilder.toString(), namedParameterSource, new RowMapper<UserDTO>() {
			@Override
			public UserDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(rs.getLong("USERID"));
				userDTO.setFirstName(rs.getString("FIRSTNAME"));
				userDTO.setLastName(rs.getString("LASTNAME"));
				userDTO.setEmailId(rs.getString("EMAILID"));
				userDTO.setPrimaryPhoneNumber(rs.getString("PRIMARY_PH_NO"));
				return userDTO;
			}
		});
	}


	@Override
	public UserDTO getUserDetailsById(final long userId) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("USERID", userId);
		return getNamedParameterJdbcTemplate().queryForObject(GET_USER_DETAILS_BY_USERID_QUERY, namedParameterSource, new RowMapper<UserDTO>() {
			@Override
			public UserDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(rs.getLong("USERID"));
				userDTO.setFirstName(rs.getString("FIRSTNAME"));
				userDTO.setLastName(rs.getString("LASTNAME"));
				userDTO.setEmailId(rs.getString("EMAILID"));
				userDTO.setUid(rs.getString("UID_NUMBER"));
				userDTO.setUidType(rs.getString("UID_TYPE"));
				userDTO.setBloodGroup(rs.getString("BLOOD_GROUP"));
				userDTO.setDateOfBirth(rs.getString("DOB"));
				userDTO.setSecondaryEmail(rs.getString("SECOUNDERY_EMAIL"));
				userDTO.setSecondaryPhoneNumber(rs.getString("SECONDERY_PH_NO"));
				userDTO.setPrimaryPhoneNumber(rs.getString("PRIMARY_PH_NO"));
				Address address = new Address();
				address.setAddress1(rs.getString("ADDRESS_LINE1"));
				address.setAddress2(rs.getString("ADDRESS_LINE2"));
				address.setAddress3(rs.getString("ADDRESS_LINE3"));
				address.setCity(rs.getString("CITY"));
				address.setState(rs.getString("STATE"));
				address.setCountry(rs.getString("COUNTRY"));
				if(rs.getInt("PIN")!=0){
				address.setPostalCode(rs.getInt("PIN"));
				}
				userDTO.setAddress(address);
				try{
				userDTO.setEmergencyContactInfo(getEmeregencyContactInfo(userDTO.getEmailId()));
				}catch(final EmptyResultDataAccessException ex){
					LOG.error("caught the emptu result for user :::"+userId,ex);
					System.out.println("caught the emptu result for user::"+userId+"::"+ex);
				}
				return userDTO;
			}
		});
	}

	private EmergencyContactInfo getEmeregencyContactInfo(final String emailId) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("EMAILID", emailId);
		return getNamedParameterJdbcTemplate().queryForObject("SELECT * FROM emrg_contact_info WHERE EMAILID=:EMAILID", namedParameterSource, new RowMapper<EmergencyContactInfo>() {
			@Override
			public EmergencyContactInfo mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				EmergencyContactInfo contactInfo = new EmergencyContactInfo();
				contactInfo.setName(rs.getString("NAME"));
				contactInfo.setRelation(rs.getString("REL"));
				contactInfo.setPhoneNumber(rs.getString("PHONE_NUM"));
				return contactInfo;
			}
		});
	}
	private StringBuilder buildQuery(final String firstName,
			final String lastName, final String emailId,
			final String phoneNumber, final String uidNumber,
			StringBuilder queryBuilder) {
		if (StringUtils.isNotBlank(firstName)) {
			queryBuilder.append(" WHERE FIRSTNAME=:FIRSTNAME");
			if (StringUtils.isNotBlank(emailId)) {
				queryBuilder.append(" AND EMAILID=:EMAILID");
			}
			if (StringUtils.isNotBlank(lastName)) {
				queryBuilder.append(" AND LASTNAME=:LASTNAME");
			}
			if (StringUtils.isNotBlank(phoneNumber)) {
				queryBuilder.append(" AND PRIMARY_PH_NO=:PRIMARY_PH_NO");
			}
			if (StringUtils.isNotBlank(uidNumber)) {
				queryBuilder.append(" AND UID_NUMBER=:UID_NUMBER");
			}
		} else if (StringUtils.isNotBlank(emailId)) {
			queryBuilder.append(" WHERE EMAILID=:EMAILID");
			if (StringUtils.isNotBlank(lastName)) {
				queryBuilder.append(" AND LASTNAME=:LASTNAME");
			}
			if (StringUtils.isNotBlank(phoneNumber)) {
				queryBuilder.append(" AND PRIMARY_PH_NO=:PRIMARY_PH_NO");
			}
			if (StringUtils.isNotBlank(uidNumber)) {
				queryBuilder.append(" AND UID_NUMBER=:UID_NUMBER");
			}
		} else if (StringUtils.isNotBlank(phoneNumber)) {
			queryBuilder.append(" WHERE PRIMARY_PH_NO=:PRIMARY_PH_NO");
			if (StringUtils.isNotBlank(lastName)) {
				queryBuilder.append(" AND LASTNAME=:LASTNAME");
			}
			if (StringUtils.isNotBlank(uidNumber)) {
				queryBuilder.append(" AND UID_NUMBER=:UID_NUMBER");
			}
		} else if (StringUtils.isNotBlank(uidNumber)) {
			queryBuilder.append(" WHERE UID_NUMBER=:UID_NUMBER");
			if (StringUtils.isNotBlank(lastName)) {
				queryBuilder.append(" AND LASTNAME=:LASTNAME");
			}
		} else if (StringUtils.isNotBlank(lastName)) {
			queryBuilder.append(" WHERE LASTNAME=:LASTNAME");
		}
		return queryBuilder;
	}
}