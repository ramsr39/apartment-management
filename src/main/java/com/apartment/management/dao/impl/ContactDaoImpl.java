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
import com.apartment.management.dto.Address;
import com.apartment.management.dto.ContactDTO;

public class ContactDaoImpl extends SimpleJdbcDaoSupport implements ContactDao{

    private static final Logger LOG=LoggerFactory.getLogger(ContactDaoImpl.class);
    
	private static final String INSERT_CONTACT_DETAILS_QUERY="INSERT INTO contact(CONTACT_ID,"
			+ "PHONE_NO,"
			+ "EMAIL_ID,"
			+ "NAME,"
			+ "WEB_SITE,"
			+ "IS_VISIBLE_TO_PUBLIC,"
			+ "TYPE,"
			+ "DESCRIPTION,"
			+ "ADDRESS_LINE1,"
			+ "ADDRESS_LINE2,"
			+ "ADDRESS_LINE3,"
			+ "CITY,STATE,"
			+ "COUNTRY,"
			+ "PIN,"
			+ "UTILITY_ID,"
			+ "COMMUNITY_ID,"
			+ "BUILDING_ID,"
			+ "FLAT_ID,"
			+ "USER_ID)"
			+" VALUES(:CONTACT_ID,"
			   + ":PHONE_NO,"
			   + ":EMAIL_ID,"
			   + ":NAME,"
			   + ":WEB_SITE,"
			   + ":IS_VISIBLE_TO_PUBLIC,"
			   + ":TYPE,"
			   + ":DESCRIPTION,"
			   + ":ADDRESS_LINE1,"
			   + ":ADDRESS_LINE2,"
			   + ":ADDRESS_LINE3,"
			   + ":CITY,"
			   + ":STATE,"
			   + ":COUNTRY,"
			   + ":PIN,"
			   + ":UTILITY_ID,"
			   + ":COMMUNITY_ID,"
			   + ":BUILDING_ID,"
			   + ":FLAT_ID,"
			   + ":USER_ID)";

	private static final String UPDATE_CONTACT_DETAILS_QUERY="UPDATE contact SET "
			   + "PHONE_NO=:PHONE_NO,"
			   + "EMAIL_ID=:EMAIL_ID,"
			   + "NAME=:NAME,"
			   + "WEB_SITE=:WEB_SITE,"
			   + "IS_VISIBLE_TO_PUBLIC=:IS_VISIBLE_TO_PUBLIC,"
			   + "TYPE=:TYPE,"
			   + "DESCRIPTION=:DESCRIPTION,"
			   + "ADDRESS_LINE1=:ADDRESS_LINE1,"
			   + "ADDRESS_LINE2=:ADDRESS_LINE2,"
			   + "ADDRESS_LINE3=:ADDRESS_LINE3,"
			   + "CITY=:CITY,"
			   + "STATE=:STATE,"
			   + "COUNTRY=:COUNTRY,"
			   + "PIN=:PIN,"
			   + "USER_ID=:USER_ID,"
			   + "FLAT_ID=:FLAT_ID"
			   + " WHERE CONTACT_ID=:CONTACT_ID";

  private static final String FIND_CONTACT_BASE_QUERY = "SELECT *FROM contact";

  private static final String DELETE_CONTACT = "DELETE FROM contact WHERE CONTACT_ID=:CONTACT_ID";

  @Override
  public String save(final ContactDTO contactDTO) {
    LOG.info("save contact start::");
    final String contactId = "CN" + RandomStringUtils.randomNumeric(8);
    final MapSqlParameterSource namedSqlParamSource = prepareNamedParamSource(contactDTO, contactId);
    getSimpleJdbcTemplate().update(INSERT_CONTACT_DETAILS_QUERY, namedSqlParamSource);
    LOG.info("save contact end::"+contactDTO.getId());
    return contactId;
  }

  @Override
  public void update(final ContactDTO contactDTO) {
    LOG.info("update contact start::"+contactDTO.getId());
    final MapSqlParameterSource namedSqlParamSource = prepareNamedParamSource(contactDTO, contactDTO.getId());
    getSimpleJdbcTemplate().update(UPDATE_CONTACT_DETAILS_QUERY, namedSqlParamSource);
    LOG.info("update contact end::"+contactDTO.getId());
  }

  @Override
  public void delete(final String contactId) {
    LOG.info("delete contact start::"+contactId);
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("CONTACT_ID", contactId);
    getSimpleJdbcTemplate().update(DELETE_CONTACT, namedSqlParamSource);
    LOG.info("delete contact end::"+contactId);
    
  }

  @Override
  public List<ContactDTO> findContactsByCommunityId(final String communityId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE COMMUNITY_ID=:COMMUNITY_ID");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getContactsRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException ex) {
      LOG.info("no contacts registerd for given communityId:" + communityId + ":" + ex);
      return new ArrayList<ContactDTO>();
    }
  }

  @Override
  public List<ContactDTO> findContactsByBuildingId(final String buildingId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("BUILDING_ID", buildingId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE BUILDING_ID=:BUILDING_ID");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getContactsRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException ex) {
      LOG.info("no contacts registerd for given buildingId:" + buildingId + ":" + ex);
      return new ArrayList<ContactDTO>();
    }
  }

  @Override
  public List<ContactDTO> findContactsByFlatId(final String flatId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("FLAT_ID", flatId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE FLAT_ID=:FLAT_ID");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getContactsRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException ex) {
      LOG.info("no contacts registerd for given flatId:" + flatId + ":" + ex);
      return new ArrayList<ContactDTO>();
    }
  }

  @Override
  public List<ContactDTO> findContactsByUserId(final String userId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("USER_ID", userId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE USER_ID=:USER_ID");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getContactsRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException ex) {
      LOG.info("no contacts registerd for given userId:" + userId + ":" + ex);
      return new ArrayList<ContactDTO>();
    }
  }

  @Override
  public ContactDTO findContactsByUtilityId(final String utilityId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("UTILITY_ID", utilityId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE UTILITY_ID=:UTILITY_ID");
      return getSimpleJdbcTemplate().queryForObject(queryBuilder.toString(), getContactsRowmapper(),
          namedSqlParamSource);
    } catch (final EmptyResultDataAccessException ex) {
      return new ContactDTO();
    }
  }

  @Override
  public List<ContactDTO> getPublicContactDetails() {
    try {
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE IS_VISIBLE_TO_PUBLIC=").append("'true'");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getContactsRowmapper());
    } catch (final EmptyResultDataAccessException ex) {
      return new ArrayList<ContactDTO>();
    }
  }

  @Override
  public ContactDTO getContactsByContactId(final String contactId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("CONTACT_ID", contactId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(FIND_CONTACT_BASE_QUERY).append(" ").append("WHERE CONTACT_ID=:CONTACT_ID");
      return getSimpleJdbcTemplate().queryForObject(queryBuilder.toString(), getContactsRowmapper(),namedSqlParamSource);
    } catch (final EmptyResultDataAccessException ex) {
      return new ContactDTO();
    }
  }


  private RowMapper<ContactDTO> getContactsRowmapper() {
    return new RowMapper<ContactDTO>() {
      @Override
      public ContactDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(rs.getString("CONTACT_ID"));
        contactDTO.setType(rs.getString("TYPE"));
        contactDTO.setEmailId(rs.getString("EMAIL_ID"));
        contactDTO.setPhoneNumber(rs.getString("PHONE_NO"));
        contactDTO.setDescription(rs.getString("DESCRIPTION"));
        contactDTO.setWebSite(rs.getString("WEB_SITE"));
        contactDTO.setName(rs.getString("NAME"));
        contactDTO.setCommunityId(rs.getString("COMMUNITY_ID"));
        contactDTO.setBuildingId(rs.getString("BUILDING_ID"));
        contactDTO.setFlatId(rs.getString("FLAT_ID"));
        contactDTO.setUserId(rs.getString("USER_ID"));
        final Address address = new Address();
        address.setAddress1(rs.getString("ADDRESS_LINE1"));
        address.setAddress2(rs.getString("ADDRESS_LINE2"));
        address.setAddress3(rs.getString("ADDRESS_LINE3"));
        address.setCity(rs.getString("CITY"));
        address.setState(rs.getString("STATE"));
        address.setCountry(rs.getString("COUNTRY"));
        address.setPostalCode(rs.getInt("PIN"));
        contactDTO.setAddress(address);
        return contactDTO;
      }
    };
  }

  private MapSqlParameterSource prepareNamedParamSource(final ContactDTO contactDTO, final String contactId) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("CONTACT_ID", contactId);
    namedSqlParamSource.addValue("PHONE_NO", contactDTO.getPhoneNumber());
    namedSqlParamSource.addValue("EMAIL_ID", contactDTO.getEmailId());
    namedSqlParamSource.addValue("WEB_SITE", contactDTO.getWebSite());
    namedSqlParamSource.addValue("TYPE", contactDTO.getType());
    namedSqlParamSource.addValue("IS_VISIBLE_TO_PUBLIC", contactDTO.getIsVisibleToPublic());
    namedSqlParamSource.addValue("NAME", contactDTO.getName());
    namedSqlParamSource.addValue("DESCRIPTION", contactDTO.getDescription());
    namedSqlParamSource.addValue("ADDRESS_LINE1", contactDTO.getAddress().getAddress1());
    namedSqlParamSource.addValue("ADDRESS_LINE2", contactDTO.getAddress().getAddress2());
    namedSqlParamSource.addValue("ADDRESS_LINE3", contactDTO.getAddress().getAddress3());
    namedSqlParamSource.addValue("COUNTRY", contactDTO.getAddress().getCountry());
    namedSqlParamSource.addValue("STATE", contactDTO.getAddress().getState());
    namedSqlParamSource.addValue("CITY", contactDTO.getAddress().getCity());
    namedSqlParamSource.addValue("PIN", contactDTO.getAddress().getPostalCode());
    namedSqlParamSource.addValue("UTILITY_ID", contactDTO.getUtilityId());
    namedSqlParamSource.addValue("COMMUNITY_ID", contactDTO.getCommunityId());
    namedSqlParamSource.addValue("BUILDING_ID", contactDTO.getBuildingId());
    namedSqlParamSource.addValue("FLAT_ID", contactDTO.getFlatId());
    namedSqlParamSource.addValue("USER_ID", contactDTO.getUserId());
    return namedSqlParamSource;
  }

}
