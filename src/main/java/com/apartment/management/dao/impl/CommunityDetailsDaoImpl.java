package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.CommunityDetailsDao;
import com.apartment.management.dto.CommunityDTO;

public class CommunityDetailsDaoImpl extends SimpleJdbcDaoSupport implements
		CommunityDetailsDao {

	private static final String INSERT_COMMUNITY_QUERY = "INSERT INTO community("
			+ "COMMUNITY_ID,"
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
			 + ":COMMUNITY_ID,"
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
			+ " WHERE COMMUNITY_ID=:COMMUNITY_ID";
	
	 private static final String GET_COMMUNITY_NAME_QUERY = "SELECT COMMUNITYNAME FROM community  WHERE COMMUNITY_ID=:COMMUNITY_ID"; 
	 
	 private static final String FIND_EMAILID_QUERY="SELECT count(EMAILID) FROM community WHERE EMAILID=:EMAILID";
	 
	 private static final String GET_COMMUNITY_DETAILS_QUERY="SELECT COMMUNITY_ID,"
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

	 private static final String GET_COMMUNITY_DETAILS_BY_ID_QUERY="SELECT COMMUNITY_ID,"
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
		 		+ "WHERE COMMUNITY_ID=:COMMUNITY_ID";

	 private static String FIND_COMMUNITIES_BY_CITY_QUERY="SELECT * FROM community "
	 		+ "WHERE CITY=:CITY AND EMAILID=:EMAILID";

	 private static final String FIND_COMMUNITIES_BY_NAME_QUERY="SELECT * FROM community "
		 		+ "WHERE COMMUNITYNAME LIKE :COMMUNITYNAME "
		 		+ "AND EMAILID=:EMAILID";

  @Override
  public String save(final String emailId, final CommunityDTO communityDTO) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    try {
      final String communityId = "C" + RandomStringUtils.randomNumeric(8);
      mapSqlParameterSource.addValue("COMMUNITY_ID", communityId);
      mapSqlParameterSource.addValue("COMMUNITYNAME", communityDTO.getName());
      mapSqlParameterSource.addValue("ADDRESS_LINE1", communityDTO.getAddress1());
      mapSqlParameterSource.addValue("ADDRESS_LINE2", communityDTO.getAddress2());
      mapSqlParameterSource.addValue("ADDRESS_LINE3", communityDTO.getAddress3());
      mapSqlParameterSource.addValue("COUNTRY", communityDTO.getCountry());
      mapSqlParameterSource.addValue("STATE", communityDTO.getState());
      mapSqlParameterSource.addValue("CITY", communityDTO.getCity());
      mapSqlParameterSource.addValue("PIN", communityDTO.getPostalCode());
      mapSqlParameterSource.addValue("DESCRIPTION", communityDTO.getDescription());
      mapSqlParameterSource.addValue("COMMUNITYTYPE", communityDTO.getType());
      mapSqlParameterSource.addValue("EMAILID", emailId);
      getSimpleJdbcTemplate().getNamedParameterJdbcOperations().update(INSERT_COMMUNITY_QUERY, mapSqlParameterSource);
      return communityId;
    } catch (DataAccessException e) {
      throw e;
    }
  }

  @Override
  public void update(final CommunityDTO communityDTO) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    try {
      mapSqlParameterSource.addValue("COMMUNITY_ID", communityDTO.getId());
      mapSqlParameterSource.addValue("COMMUNITYNAME", communityDTO.getName());
      mapSqlParameterSource.addValue("ADDRESS_LINE1", communityDTO.getAddress1());
      mapSqlParameterSource.addValue("ADDRESS_LINE2", communityDTO.getAddress2());
      mapSqlParameterSource.addValue("ADDRESS_LINE3", communityDTO.getAddress3());
      mapSqlParameterSource.addValue("COUNTRY", communityDTO.getCountry());
      mapSqlParameterSource.addValue("STATE", communityDTO.getState());
      mapSqlParameterSource.addValue("CITY", communityDTO.getCity());
      mapSqlParameterSource.addValue("PIN", communityDTO.getPostalCode());
      mapSqlParameterSource.addValue("DESCRIPTION", communityDTO.getDescription());
      mapSqlParameterSource.addValue("COMMUNITYTYPE", communityDTO.getType());
      getSimpleJdbcTemplate().update(UPDATE_COMMUNITY_QUERY, mapSqlParameterSource);
    } catch (DataAccessException e) {
      throw e;
    }
  }

  @Override
  public CommunityDTO findCommunityDetailsByUserId(final String emailId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("EMAILID", emailId);
    return getSimpleJdbcTemplate().queryForObject(GET_COMMUNITY_DETAILS_QUERY, new RowMapper<CommunityDTO>() {
      @Override
      public CommunityDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setId(rs.getString("COMMUNITY_ID"));
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
  public boolean isCommnityExistedForUser(final String emailId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("EMAILID", emailId);
    int count = getSimpleJdbcTemplate().queryForInt(FIND_EMAILID_QUERY, mapSqlParameterSource);
    if (0 == count) {
      return false;
    }
    return true;
  }

  @Override
  public List<CommunityDTO> findCommunitiesByCity(final String emailId, final String communityName, final String city) {
    String finalquery = FIND_COMMUNITIES_BY_CITY_QUERY;
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("EMAILID", emailId);
    if (StringUtils.isNotBlank(communityName)) {
      finalquery = FIND_COMMUNITIES_BY_CITY_QUERY + " AND COMMUNITYNAME LIKE " + "'%" + communityName + "%'";
    }
    mapSqlParameterSource.addValue("CITY", city);
    return getSimpleJdbcTemplate().query(finalquery, new RowMapper<CommunityDTO>() {
      @Override
      public CommunityDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setId(rs.getString("COMMUNITY_ID"));
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
  public List<CommunityDTO> findCommunitiesByName(final String emailId, final String communityName) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("EMAILID", emailId);
    mapSqlParameterSource.addValue("COMMUNITYNAME", "%" + communityName + "%");
    return getSimpleJdbcTemplate().query(FIND_COMMUNITIES_BY_NAME_QUERY, new RowMapper<CommunityDTO>() {
      @Override
      public CommunityDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setId(rs.getString("COMMUNITY_ID"));
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
  public String getCommunityName(String communityId) {
    MapSqlParameterSource paramaSource = new MapSqlParameterSource();
    paramaSource.addValue("COMMUNITY_ID", communityId);
    return getSimpleJdbcTemplate().queryForObject(GET_COMMUNITY_NAME_QUERY, String.class, paramaSource);
  }

  @Override
  public CommunityDTO getCommunityDetailsByCommunityId(final String communityId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("COMMUNITY_ID", communityId);
    return getSimpleJdbcTemplate().queryForObject(GET_COMMUNITY_DETAILS_BY_ID_QUERY, new RowMapper<CommunityDTO>() {
      @Override
      public CommunityDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setId(rs.getString("COMMUNITY_ID"));
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
  public List<String> getUserCommunityIds(final String emailId) {
    try {
      MapSqlParameterSource paramaSource = new MapSqlParameterSource();
      paramaSource.addValue("USER_ID", emailId);
      return getSimpleJdbcTemplate().query("SELECT COMMUNITY_ID FROM management_group WHERE USER_ID=:USER_ID",
          new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
              // TODO Auto-generated method stub
              return rs.getString("COMMUNITY_ID");
            }
          }, paramaSource);
    } catch (final EmptyResultDataAccessException er) {
      return new ArrayList<String>();
    }
  }

}
