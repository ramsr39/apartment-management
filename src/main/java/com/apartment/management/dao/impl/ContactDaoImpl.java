package com.apartment.management.dao.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.ContactDao;
import com.apartment.management.dto.ContactDTO;

public class ContactDaoImpl extends SimpleJdbcDaoSupport implements ContactDao{

	private static final String DELETE_CONTACT = "DELETE FROM contact WHERE CONTACT_ID=CONTACT_ID";

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

	private static final String UPDATE_CONTACT_DETAILS_QUERY="UPDATE INTO contact SET "
			   + "PHONE_NO=:PHONE_NO,"
			   + "EMAIL_ID=:EMAIL_ID,"
			   + "NAME=:NAME,"
			   + "WEB_SITE=:WEB_SITE,"
			   + "IS_VISIBLE_TO_PUBLIC=:IS_VISIBLE_TO_PUBLIC,"
			   + "TYPE=:TYPE"
			   + "DESCRIPTION=:DESCRIPTION,"
			   + "ADDRESS_LINE1=:ADDRESS_LINE1,"
			   + "ADDRESS_LINE2=:ADDRESS_LINE2,"
			   + "ADDRESS_LINE3=:ADDRESS_LINE3,"
			   + "CITY=:CITY,"
			   + "STATE=:STATE,"
			   + "COUNTRY=:COUNTRY,"
			   + "PIN=:PIN,"
			   + "UTILITY_ID=:UTILITY_ID,"
			   + "COMMUNITY_ID=:COMMUNITY_ID,"
			   + "BUILDING_ID=:BUILDING_ID,"
			   + "FLAT_ID=:FLAT_ID,"
			   + "USER_ID=:USER_ID WHERE CONTACT_ID=:CONTAT_ID";


	@Override
	public String save(final ContactDTO contactDTO) {
		final String contactId = "CN"+RandomStringUtils.randomNumeric(8);
		final MapSqlParameterSource namedSqlParamSource = prepareNamedParamSource(contactDTO,contactId);
		getSimpleJdbcTemplate().update(INSERT_CONTACT_DETAILS_QUERY, namedSqlParamSource);
		return contactId;
	}

	@Override
	public void update(final ContactDTO contactDTO) {
		final MapSqlParameterSource namedSqlParamSource = prepareNamedParamSource(contactDTO,contactDTO.getId());
		getSimpleJdbcTemplate().update(UPDATE_CONTACT_DETAILS_QUERY, namedSqlParamSource);
	}

	@Override
	public void delete(final String contactId) {
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("CONTACT_ID", contactId);
		getSimpleJdbcTemplate().update(DELETE_CONTACT, namedSqlParamSource);
	}

	private MapSqlParameterSource prepareNamedParamSource(final ContactDTO contactDTO,final String contactId){
		final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
		namedSqlParamSource.addValue("CONTACT_ID", contactId);
		namedSqlParamSource.addValue("PHONE_NO", contactDTO.getPhoneNumber());
		namedSqlParamSource.addValue("EMAIL_ID", contactDTO.getEmailId());
		namedSqlParamSource.addValue("WEB_SITE", contactDTO.getWebSite());
		namedSqlParamSource.addValue("TYPE", contactDTO.getType());
		namedSqlParamSource.addValue("IS_VISIBLE_TO_PUBLIC", contactDTO.getIsVisableToPublic());
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
