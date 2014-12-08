package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.apartment.management.dao.BuildingDao;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.UserDTO;

public class BuildingDaoImpl extends NamedParameterJdbcDaoSupport
		implements BuildingDao {
	
	private static final String INSERT_BUILDING_QUERY = "INSERT INTO building("
			+ "COMMUNITYID,"
			+ "BUILDINGNAME,"
			+ "NOOFUNITS,"
			+ "NOOFFLOORS,"
			+ "COUNTRY,"
			+ "STATE,"
			+ "CITY,"
		    + "PIN,"
			+ "OWNER_ID,"
			+ "ADDRESS_LINE1,"
			+ "ADDRESS_LINE2,"
			+ "ADDRESS_LINE3,"
			+ "IMAGE_URL) "
			+ "VALUES("
			+ ":COMMUNITYID,"
			+ ":BUILDINGNAME,"
			+ ":NOOFUNITS,"
			+ ":NOOFFLOORS,"
			+ ":COUNTRY,"
			+ ":STATE,"
			+ ":CITY,"
			+ ":PIN,"
			+ ":OWNER_ID,"
			+ ":ADDRESS_LINE1,"
			+ ":ADDRESS_LINE2,"
			+ ":ADDRESS_LINE3,"
			+ ":IMAGE_URL)";
	
	private static final String UPDATE_BUILDING_QUERY = "UPDATE building SET "
			+ "BUILDINGNAME=:BUILDINGNAME,"
			+ "NOOFUNITS=:NOOFUNITS,"
			+ "NOOFFLOORS=:NOOFFLOORS,"
			+ "IMAGE_URL=:IMAGE_URL,"
			+ "ADDRESS_LINE1=:ADDRESS_LINE1,"
			+ "ADDRESS_LINE2=:ADDRESS_LINE2,"
			+ "ADDRESS_LINE3=:ADDRESS_LINE3,"
			+ "CITY=:CITY,"
			+ "STATE=:STATE,"
			+ "COUNTRY=:COUNTRY,"
			+ "PIN=:PIN,"
			+ "OWNER_ID=:OWNER_ID"
			+ " WHERE BUILDINGID=:BUILDINGID"; 
	
	private static final String GET_BUILDING_DETAILS_QUERY="SELECT * FROM building WHERE COMMUNITYID=:COMMUNITYID";

	private static final String FIND_BUILDING_DETAILS_BY_BUILDINGID_QUERY="SELECT * FROM building WHERE BUILDINGID=:BUILDINGID";

	private static final String DELETE_BUILDING_QUERY = "DELETE FROM building WHERE BUILDINGID=:BUILDINGID";

	private static final String GET_BUILDING_OWNER_QUERY = "SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo WHERE EMAILID=:OWNEREID";
	
	private static final Logger LOG = LoggerFactory.getLogger(BuildingDaoImpl.class); 

	@Override
	public long save(final BuildingDTO buildingDTO) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource mapSqlParameterSource = prepareParamenterSource(buildingDTO);
	    getNamedParameterJdbcTemplate().update(INSERT_BUILDING_QUERY, mapSqlParameterSource,keyHolder);
	    return keyHolder.getKey().longValue();
	}

	@Override
	public void update(final BuildingDTO buildingDTO) {
		MapSqlParameterSource mapSqlParameterSource = prepareParamenterSource(buildingDTO);
		mapSqlParameterSource.addValue("BUILDINGID", buildingDTO.getId());
	    getNamedParameterJdbcTemplate().update(UPDATE_BUILDING_QUERY, mapSqlParameterSource);
	}

	@Override
	public void delete(final long buiildingId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("BUILDINGID", buiildingId);
		getNamedParameterJdbcTemplate().update(DELETE_BUILDING_QUERY, mapSqlParameterSource);
	}

	@Override
	public List<BuildingDTO> findBuildingDetailsByCommunityId(final long communityId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", communityId);
		return getNamedParameterJdbcTemplate().query(GET_BUILDING_DETAILS_QUERY, mapSqlParameterSource,
				new RowMapper<BuildingDTO>() {
					@Override
					public BuildingDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						BuildingDTO buildingDto = new BuildingDTO();
						buildingDto.setId(rs.getInt("BUILDINGID"));
						buildingDto.setName(rs.getString("BUILDINGNAME"));
						buildingDto.setTotalFloors(rs.getInt("NOOFFLOORS"));
						buildingDto.setTotalUnits(rs.getInt("NOOFUNITS"));
						buildingDto.setAddress1(rs.getString("ADDRESS_LINE1"));
						buildingDto.setAddress2(rs.getString("ADDRESS_LINE2"));
						buildingDto.setAddress3(rs.getString("ADDRESS_LINE3"));
						buildingDto.setCity(rs.getString("CITY"));
						buildingDto.setState(rs.getString("STATE"));
						buildingDto.setCountry(rs.getString("COUNTRY"));
						buildingDto.setPostalCode(rs.getInt("PIN"));
						buildingDto.setImageUrl(rs.getString("IMAGE_URL"));
						String ownerId = rs.getString("OWNER_ID");
						if (null != ownerId) {
							buildingDto.setOwnerDetails(getBuildingOwnerDetails(ownerId));
						}
						return buildingDto;
					}
				});
	}

	@Override
	public BuildingDTO getBuildingDetailsByBuildingId(final long buildingId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("BUILDINGID", buildingId);
		return getNamedParameterJdbcTemplate().queryForObject(FIND_BUILDING_DETAILS_BY_BUILDINGID_QUERY, mapSqlParameterSource,
				new RowMapper<BuildingDTO>() {
					@Override
					public BuildingDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						BuildingDTO buildingDto = new BuildingDTO();
						buildingDto.setId(rs.getInt("BUILDINGID"));
						buildingDto.setCommunityId(rs.getInt("COMMUNITYID"));
						buildingDto.setName(rs.getString("BUILDINGNAME"));
						buildingDto.setTotalFloors(rs.getInt("NOOFFLOORS"));
						buildingDto.setTotalUnits(rs.getInt("NOOFUNITS"));
						buildingDto.setAddress1(rs.getString("ADDRESS_LINE1"));
						buildingDto.setAddress2(rs.getString("ADDRESS_LINE2"));
						buildingDto.setAddress3(rs.getString("ADDRESS_LINE3"));
						buildingDto.setCity(rs.getString("CITY"));
						buildingDto.setState(rs.getString("STATE"));
						buildingDto.setCountry(rs.getString("COUNTRY"));
						buildingDto.setPostalCode(rs.getInt("PIN"));
						buildingDto.setImageUrl(rs.getString("IMAGE_URL"));
						return buildingDto;
					}
				});
	}

	private MapSqlParameterSource prepareParamenterSource(final BuildingDTO buildingDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", buildingDTO.getCommunityId());
		mapSqlParameterSource.addValue("BUILDINGNAME", buildingDTO.getName());
		mapSqlParameterSource.addValue("NOOFUNITS", buildingDTO.getTotalUnits());
		mapSqlParameterSource.addValue("NOOFFLOORS", buildingDTO.getTotalFloors());
		mapSqlParameterSource.addValue("ADDRESS_LINE1", buildingDTO.getAddress1());
		mapSqlParameterSource.addValue("ADDRESS_LINE2", buildingDTO.getAddress2());
		mapSqlParameterSource.addValue("ADDRESS_LINE3", buildingDTO.getAddress2());
		mapSqlParameterSource.addValue("COUNTRY", buildingDTO.getCountry());
		mapSqlParameterSource.addValue("STATE", buildingDTO.getState());
		mapSqlParameterSource.addValue("CITY", buildingDTO.getCity());
		mapSqlParameterSource.addValue("PIN", buildingDTO.getPostalCode());
		mapSqlParameterSource.addValue("IMAGE_URL", buildingDTO.getImageUrl());
		mapSqlParameterSource.addValue("OWNERID", buildingDTO.getOwnerDetails().getEmailId());
		return mapSqlParameterSource;
	}

	private UserDTO getBuildingOwnerDetails(final String ownerId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("OWNERID", ownerId);
		return getNamedParameterJdbcTemplate().queryForObject(GET_BUILDING_OWNER_QUERY, mapSqlParameterSource, new RowMapper<UserDTO>() {
			@Override
			public UserDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				LOG.info("FlatDaoImpl getOwnerDetailsForFalt mapRow ::::start");
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(rs.getLong("USERID"));
				userDTO.setFirstName(rs.getString("FIRSTNAME"));
				userDTO.setLastName(rs.getString("LASTNAME"));
				userDTO.setEmailId(rs.getString("EMAILID"));
				userDTO.setPrimaryPhoneNumber(rs.getString("PRIMARY_PH_NO"));
				LOG.info("FlatDaoImpl getOwnerDetailsForFalt mapRow ::::end");
				return userDTO;
			}
		});
	}

}