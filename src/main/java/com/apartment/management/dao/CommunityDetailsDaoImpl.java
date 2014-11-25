package com.apartment.management.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;

public class CommunityDetailsDaoImpl extends SimpleJdbcDaoSupport implements
		CommunityDetailsDao {

	private static final String INSERT_COMMUNITY_QUERY = "INSERT INTO community("
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
			+ "VALUES("
			+ ":COMMUNITYNAME,"
			+ ":ADDRESS_LINE1,"
			+ ":ADDRESS_LINE2,"
			+ ":ADDRESS_LINE3,"
			+ ":COUNTRY,:STATE,"
			+ ":CITY,"
			+ ":PIN,"
			+ ":DESCRIPTION,"
			+ ":COMMUNITYTYPE)";
	
	private static final String INSERT_BUILDING_QUERY = "INSERT INTO building("
			+ "COMMUNITYID,"
			+ "BUILDINGNAME,"
			+ "NOOFUNITS,"
			+ "NOOFFLOORS,"
			+ "IMAGE_URL) "
			+ "VALUES("
			+ ":COMMUNITYID,"
			+ ":BUILDINGNAME,"
			+ ":NOOFUNITS,"
			+ ":NOOFFLOORS,"
			+ ":IMAGE_URL)";
	
	private static final String UPDATE_COMMUNITY_QUERY = "UPDATE community SET "
			+ "COMMUNITYNAME=:COMMUNITYNAME,"
			+ "ADDRESS_LINE1=:ADDRESS_LINE1,"
			+ "ADDRESS_LINE2=:ADDRESS_LINE2,"
			+ "ADDRESS_LINE3=:ADDRESS_LINE3, "
			+ "CITY=:CITY,"
			+ "STATE=:STATE,"
			+ "COUNTRY=:COUNTRY,"
			+ "PIN=:PIN,"
			+ "DESCRIPTION=:DESCRIPTION,"
			+ "COMMUNITYTYPE=:COMMUNITYTYPE"
			+ " WHERE COMMUNITYID=:COMMUNITYID";
	
	private static final String UPDATE_BUILDING_QUERY = "UPDATE building SET "
			+ "BUILDINGNAME=:BUILDINGNAME,"
			+ "NOOFUNITS=:NOOFUNITS,"
			+ "NOOFFLOORS=:NOOFFLOORS,"
			+ "IMAGE_URL=:IMAGE_URL "
			+ "WHERE BUILDINGNAME=:OLDBUILDINGNAME"; 
		
	 private static final String GET_COMMUNITYID_QUERY="SELECT COMMUNITYID FROM community WHERE COMMUNITYNAME=:COMMUNITYNAME";

	@Override
	public long saveCommunityDetails(final CommunityDTO communityDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		//mapSqlParameterSource.addValue("COMMUNITYID", idIncrementer.nextLongValue());
		try{
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
	    getSimpleJdbcTemplate().update(INSERT_COMMUNITY_QUERY, mapSqlParameterSource);
	    return getSimpleJdbcTemplate().queryForLong("SELECT LAST_INSERT_ID();");
		}catch(DataAccessException e){
		throw e;
		}
	}

	@Override
	public long saveBuildingDetails(final BuildingDTO buildingDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		//mapSqlParameterSource.addValue("BUILDINGID", idIncrementer.nextLongValue());
		mapSqlParameterSource.addValue("COMMUNITYID", getCommunityId(buildingDTO.getCommunityName()));
		mapSqlParameterSource.addValue("BUILDINGNAME", buildingDTO.getName());
		mapSqlParameterSource.addValue("NOOFUNITS", buildingDTO.getTotalUnits());
		mapSqlParameterSource.addValue("NOOFFLOORS", buildingDTO.getTotalFloors());
		mapSqlParameterSource.addValue("IMAGE_URL", buildingDTO.getImageUrl());
	    return getSimpleJdbcTemplate().update(INSERT_BUILDING_QUERY, mapSqlParameterSource);
	}

	private long getCommunityId(final String communityName) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYNAME", communityName);
		return getSimpleJdbcTemplate().queryForLong(GET_COMMUNITYID_QUERY, mapSqlParameterSource);
	}

	@Override
	public long updateCommunityDetails(final CommunityDTO communityDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		try{
	    mapSqlParameterSource.addValue("COMMUNITYID", communityDTO.getId());
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
	    return getSimpleJdbcTemplate().update(UPDATE_COMMUNITY_QUERY, mapSqlParameterSource);
		}catch(DataAccessException e){
		throw e;
		}
	}

	@Override
	public long updateBuildingDetails(final BuildingDTO buildingDTO,final String oldBuildingName) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		//mapSqlParameterSource.addValue("BUILDINGID", idIncrementer.nextLongValue());
		mapSqlParameterSource.addValue("COMMUNITYID", getCommunityId(buildingDTO.getCommunityName()));
		mapSqlParameterSource.addValue("BUILDINGNAME", buildingDTO.getName());
		mapSqlParameterSource.addValue("OLDBUILDINGNAME", oldBuildingName);
		mapSqlParameterSource.addValue("NOOFUNITS", buildingDTO.getTotalUnits());
		mapSqlParameterSource.addValue("NOOFFLOORS", buildingDTO.getTotalFloors());
		mapSqlParameterSource.addValue("IMAGE_URL", buildingDTO.getImageUrl());
	    return getSimpleJdbcTemplate().update(UPDATE_BUILDING_QUERY, mapSqlParameterSource);
	}

	/*public void setIdIncrementer(final DataFieldMaxValueIncrementer idIncrementer) {
		this.idIncrementer = idIncrementer;
	}
	
	*/

}
