package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.apartment.management.dao.BuildingDao;
import com.apartment.management.dao.CommunityDetailsDao;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.UserDTO;

public class BuildingDaoImpl extends NamedParameterJdbcDaoSupport
		implements BuildingDao {
	
	private static final String GET_COMMUNITYID_FOR_BUILDING_QUERY = "SELECT COMMUNITY_ID FROM building WHERE BUILDING_ID=:BUILDING_ID";

	private CommunityDetailsDao communityDetailsDao;
	
	private static final String GET_BUILDINGNAME_BY_ID = "SELECT BUILDINGNAME FROM building WHERE BUILDING_ID=:BUILDING_ID";

	private static final String INSERT_BUILDING_QUERY = "INSERT INTO building("
			+ "BUILDING_ID,"
			+ "COMMUNITY_ID,"
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
			+ ":BUILDING_ID,"
			+ ":COMMUNITY_ID,"
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
			+ " WHERE BUILDING_ID=:BUILDING_ID"; 
	
  private static final String GET_BUILDING_DETAILS_QUERY = "SELECT * FROM building WHERE COMMUNITY_ID=:COMMUNITY_ID";

  private static final String FIND_BUILDING_DETAILS_BY_BUILDINGID_QUERY =
    "SELECT * FROM building WHERE BUILDING_ID=:BUILDING_ID";

  private static final String DELETE_BUILDING_QUERY = "DELETE FROM building WHERE BUILDING_ID=:BUILDING_ID";

  private static final String GET_BUILDING_OWNER_QUERY =
    "SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo WHERE USERID=:OWNERID";

  private static final Logger LOG = LoggerFactory.getLogger(BuildingDaoImpl.class);

  @Override
  public String save(final BuildingDTO buildingDTO) {
    final MapSqlParameterSource mapSqlParameterSource = prepareParamenterSource(buildingDTO);
    final String buildingId = "B" + RandomStringUtils.randomNumeric(8);
    mapSqlParameterSource.addValue("BUILDING_ID", buildingId);
    getNamedParameterJdbcTemplate().update(INSERT_BUILDING_QUERY, mapSqlParameterSource);
    return buildingId;
  }

  @Override
  public void update(final BuildingDTO buildingDTO) {
    final MapSqlParameterSource mapSqlParameterSource = prepareParamenterSource(buildingDTO);
    mapSqlParameterSource.addValue("BUILDING_ID", buildingDTO.getId());
    getNamedParameterJdbcTemplate().update(UPDATE_BUILDING_QUERY, mapSqlParameterSource);
  }

  @Override
  public void delete(final String buiildingId) {
    final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("BUILDING_ID", buiildingId);
    getNamedParameterJdbcTemplate().update(DELETE_BUILDING_QUERY, mapSqlParameterSource);
  }

  @Override
  public List<BuildingDTO> findBuildingDetailsByCommunityId(final String communityId) {
    final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("COMMUNITY_ID", communityId);
    return getNamedParameterJdbcTemplate().query(GET_BUILDING_DETAILS_QUERY, mapSqlParameterSource,
        new RowMapper<BuildingDTO>() {
          @Override
          public BuildingDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            BuildingDTO buildingDto = new BuildingDTO();
            buildingDto.setId(rs.getString("BUILDING_ID"));
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
  public BuildingDTO getBuildingDetailsByBuildingId(final String buildingId) {
    final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("BUILDING_ID", buildingId);
    return getNamedParameterJdbcTemplate().queryForObject(FIND_BUILDING_DETAILS_BY_BUILDINGID_QUERY,
        mapSqlParameterSource, new RowMapper<BuildingDTO>() {
          @Override
          public BuildingDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            BuildingDTO buildingDto = new BuildingDTO();
            buildingDto.setId(rs.getString("BUILDING_ID"));
            buildingDto.setCommunityId(rs.getString("COMMUNITY_ID"));
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
  public String getBuildingNameById(final String buildingId) {
    final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("BUILDING_ID", buildingId);
    return getNamedParameterJdbcTemplate().queryForObject(GET_BUILDINGNAME_BY_ID, mapSqlParameterSource, String.class);
  }

  @Override
  public Map<String, String> getBuildingCommunityById(final String buildingId) {
    final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    final Map<String, String> communityMap = new HashMap<String, String>();
    mapSqlParameterSource.addValue("BUILDING_ID", buildingId);
    final String communityId =
      getNamedParameterJdbcTemplate().queryForObject(GET_COMMUNITYID_FOR_BUILDING_QUERY, mapSqlParameterSource,
          String.class);
    final String communityName = communityDetailsDao.getCommunityName(communityId);
    communityMap.put("communityId", String.valueOf(communityId));
    communityMap.put("communityName", communityName);
    return communityMap;
  }

  private MapSqlParameterSource prepareParamenterSource(final BuildingDTO buildingDTO) {
    final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("COMMUNITY_ID", buildingDTO.getCommunityId());
    mapSqlParameterSource.addValue("BUILDINGNAME", buildingDTO.getName());
    mapSqlParameterSource.addValue("NOOFUNITS", buildingDTO.getTotalUnits());
    mapSqlParameterSource.addValue("NOOFFLOORS", buildingDTO.getTotalFloors());
    mapSqlParameterSource.addValue("ADDRESS_LINE1", buildingDTO.getAddress1());
    mapSqlParameterSource.addValue("ADDRESS_LINE2", buildingDTO.getAddress2());
    mapSqlParameterSource.addValue("ADDRESS_LINE3", buildingDTO.getAddress3());
    mapSqlParameterSource.addValue("COUNTRY", buildingDTO.getCountry());
    mapSqlParameterSource.addValue("STATE", buildingDTO.getState());
    mapSqlParameterSource.addValue("CITY", buildingDTO.getCity());
    mapSqlParameterSource.addValue("PIN", buildingDTO.getPostalCode());
    mapSqlParameterSource.addValue("IMAGE_URL", buildingDTO.getImageUrl());
    mapSqlParameterSource.addValue("OWNER_ID", buildingDTO.getOwnerDetails().getUserId());
    return mapSqlParameterSource;
  }

  private UserDTO getBuildingOwnerDetails(final String ownerId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("OWNERID", ownerId);
    return getNamedParameterJdbcTemplate().queryForObject(GET_BUILDING_OWNER_QUERY, mapSqlParameterSource,
        new RowMapper<UserDTO>() {
          @Override
          public UserDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            LOG.info("FlatDaoImpl getOwnerDetailsForFalt mapRow ::::start");
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(rs.getString("USERID"));
            userDTO.setFirstName(rs.getString("FIRSTNAME"));
            userDTO.setLastName(rs.getString("LASTNAME"));
            userDTO.setEmailId(rs.getString("EMAILID"));
            userDTO.setPrimaryPhoneNumber(rs.getString("PRIMARY_PH_NO"));
            LOG.info("FlatDaoImpl getOwnerDetailsForFalt mapRow ::::end");
            return userDTO;
          }
        });
  }

  public void setCommunityDetailsDao(final CommunityDetailsDao communityDetailsDao) {
    this.communityDetailsDao = communityDetailsDao;
  }

}