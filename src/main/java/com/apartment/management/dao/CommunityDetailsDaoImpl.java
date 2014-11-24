package com.apartment.management.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;

public class CommunityDetailsDaoImpl extends SimpleJdbcDaoSupport implements
		CommunityDetailsDao {

	private DataFieldMaxValueIncrementer idIncrementer;

	private static final String INSERT_COMMUNITY = "INSERT INTO community(COMMUNITYID, "
			+ "COMMUNITYNAME, "
			+ "ADDRESS_LINE1 ,"
			+ "ADDRESS_LINE2, "
			+ "ADDRESS_LINE3, "
			+ "COUNTRY, "
			+ "STATE, "
			+ "CITY, "
			+ "PIN, "
			+ "DESCRIPTION, "
			+ "COMMUNITYTYPE) "
			+ "values(:COMMUNITYID, "
			+ ":COMMUNITYNAME,"
			+ ":ADDRESS_LINE1,"
			+ ":ADDRESS_LINE2,"
			+ ":ADDRESS_LINE3,"
			+ ":COUNTRY,:STATE,:CITY,:PIN,:DESCRIPTION,:COMMUNITYTYPE)";

	@Override
	public long saveCommunityDetails(final CommunityDTO communityDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", idIncrementer.nextLongValue());
		mapSqlParameterSource.addValue("COMMUNITYNAME", communityDTO.getName());
		mapSqlParameterSource.addValue("ADDRESS_LINE1", communityDTO.getAddress1());
		mapSqlParameterSource.addValue("ADDRESS_LINE2", communityDTO.getAddress2());
		mapSqlParameterSource.addValue("ADDRESS_LINE3", communityDTO.getAddress2());
		mapSqlParameterSource.addValue("COUNTRY", communityDTO.getCountry());
		mapSqlParameterSource.addValue("STATE", communityDTO.getState());
		mapSqlParameterSource.addValue("CITY", communityDTO.getCity());
		mapSqlParameterSource.addValue("PIN", communityDTO.getPostalCode());
		mapSqlParameterSource.addValue("DESCRIPTION", communityDTO.getDescription());
		mapSqlParameterSource.addValue("COMMUNITYTYPE", communityDTO.getType());
	    return getSimpleJdbcTemplate().update(INSERT_COMMUNITY, mapSqlParameterSource);
	}

	@Override
	public BuildingDTO saveBuildingDetails(final BuildingDTO buildingDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIdIncrementer(final DataFieldMaxValueIncrementer idIncrementer) {
		this.idIncrementer = idIncrementer;
	}
	
	

}
