package com.apartment.management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
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
			+ "COMMUNITYTYPE,EMAILID) "
			+ "VALUES("
			+ ":COMMUNITYNAME,"
			+ ":ADDRESS_LINE1,"
			+ ":ADDRESS_LINE2,"
			+ ":ADDRESS_LINE3,"
			+ ":COUNTRY,:STATE,"
			+ ":CITY,"
			+ ":PIN,"
			+ ":DESCRIPTION,"
			+ ":COMMUNITYTYPE,"
			+ ":EMAILID)";
	
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
	 
	 private static final String FIND_EMAILID_QUERY="SELECT count(EMAILID) FROM community WHERE EMAILID=:EMAILID";
	 
	 private static final String GET_COMMUNITY_DETAILS_QUERY="SELECT COMMUNITYID,"
	 		+ "COMMUNITYNAME,"
	 		+ "ADDRESS_LINE1,"
	 		+ "ADDRESS_LINE2,"
	 		+ "ADDRESS_LINE3,"
	 		+ "CITY,"
	 		+ "STATE,"
	 		+ "COUNTRY,"
	 		+ "PIN,"
	 		+ "DESCRIPTION,"
	 		+ "COMMUNITYTYPE"
	 		+ " FROM community "
	 		+ "WHERE EMAILID=:EMAILID";
	 
	 private static final String GET_BUILDING_DETAILS_QUERY="SELECT BUILDINGID,"
	 		+ "BUILDINGNAME,"
	 		+ "NOOFFLOORS,"
	 		+ "NOOFUNITS,"
	 		+ "IMAGE_URL FROM building "
	 		+ "WHERE COMMUNITYID=:COMMUNITYID";

	@Override
	public long saveCommunityDetails(final String emailId,
			final CommunityDTO communityDTO) {
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
		mapSqlParameterSource.addValue("EMAILID", emailId);
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
	    getSimpleJdbcTemplate().update(INSERT_BUILDING_QUERY, mapSqlParameterSource);
	    return getSimpleJdbcTemplate().queryForLong("SELECT LAST_INSERT_ID();");
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

	@Override
	public CommunityDTO findCommunityDetails(final String emailId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		return getSimpleJdbcTemplate().queryForObject(GET_COMMUNITY_DETAILS_QUERY, new RowMapper<CommunityDTO>() {
			@Override
			public CommunityDTO mapRow(final ResultSet rs, final int rowNum)
					throws SQLException {
			CommunityDTO communityDTO = new CommunityDTO();
			communityDTO.setId(rs.getInt("COMMUNITYID"));
			communityDTO.setName(rs.getString("COMMUNITYNAME"));
			communityDTO.setType(rs.getString("COMMUNITYTYPE"));
			communityDTO.setAddress1(rs.getString("ADDRESS_LINE1"));
			communityDTO.setAddress2(rs.getString("ADDRESS_LINE2"));
			communityDTO.setAddress3(rs.getString("ADDRESS_LINE3"));
			communityDTO.setCity(rs.getString("CITY"));
			communityDTO.setState(rs.getString("STATE"));
			communityDTO.setCountry(rs.getString("COUNTRY"));
			communityDTO.setPostalCode(rs.getInt("PIN"));
			communityDTO.setDescription(rs.getString("DESCRIPTION"));
			return communityDTO;
			}
		}, mapSqlParameterSource);
	}
	@Override
	public List<BuildingDTO> findBuildingDetails(final long communityId){
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", communityId);
		return getSimpleJdbcTemplate().query(GET_BUILDING_DETAILS_QUERY, new RowMapper<BuildingDTO>() {
			@Override
			public BuildingDTO mapRow(final ResultSet rs, final int rowNum)
					throws SQLException {
				BuildingDTO buildingDto = new BuildingDTO();
				buildingDto.setId(rs.getInt("BUILDINGID"));
				buildingDto.setName(rs.getString("BUILDINGNAME"));
				buildingDto.setTotalFloors(rs.getInt("NOOFFLOORS"));
				buildingDto.setTotalUnits(rs.getInt("NOOFUNITS"));
				buildingDto.setImageUrl(rs.getString("IMAGE_URL"));
			return buildingDto;
			}
		}, mapSqlParameterSource);

	}

	@Override
	public boolean isCommnityExistedForUser(String emailId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		int count = getSimpleJdbcTemplate().queryForInt(FIND_EMAILID_QUERY,
				mapSqlParameterSource);
		if (0 == count) {
			return false;
		}
		return true;
	}

	/*public void setIdIncrementer(final DataFieldMaxValueIncrementer idIncrementer) {
		this.idIncrementer = idIncrementer;
	}
	
	*/

}
