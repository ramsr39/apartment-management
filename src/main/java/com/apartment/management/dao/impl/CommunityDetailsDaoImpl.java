package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.apartment.management.dao.CommunityDetailsDao;
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
	
	 private static final String GET_COMMUNITY_NAME_QUERY = "SELECT COMMUNITYNAME FROM community  WHERE COMMUNITYID=:COMMUNITYID"; 
	 
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

	 private static final String GET_COMMUNITY_DETAILS_BY_ID_QUERY="SELECT COMMUNITYID,"
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
		 		+ "WHERE COMMUNITYID=:COMMUNITYID";

	 private static String FIND_COMMUNITIES_BY_CITY_QUERY="SELECT * FROM community "
	 		+ "WHERE CITY=:CITY AND EMAILID=:EMAILID";

	 private static final String FIND_COMMUNITIES_BY_NAME_QUERY="SELECT * FROM community "
		 		+ "WHERE COMMUNITYNAME LIKE :COMMUNITYNAME "
		 		+ "AND EMAILID=:EMAILID";

	@Override
	public long save(final String emailId, final CommunityDTO communityDTO) {
		KeyHolder holder = new GeneratedKeyHolder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		try{
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
		getSimpleJdbcTemplate().getNamedParameterJdbcOperations().update(
					INSERT_COMMUNITY_QUERY, mapSqlParameterSource, holder);
	    return holder.getKey().longValue();
		}catch(DataAccessException e){
		throw e;
		}
	}

	@Override
	public long update(final CommunityDTO communityDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		try{
	    mapSqlParameterSource.addValue("COMMUNITYID", communityDTO.getId());
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
	    return getSimpleJdbcTemplate().update(UPDATE_COMMUNITY_QUERY, mapSqlParameterSource);
		}catch(DataAccessException e){
		throw e;
		}
	}

	@Override
	public CommunityDTO findCommunityDetailsByUserId(final String emailId) {
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
	public boolean isCommnityExistedForUser(final String emailId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		int count = getSimpleJdbcTemplate().queryForInt(FIND_EMAILID_QUERY,
				mapSqlParameterSource);
		if (0 == count) {
			return false;
		}
		return true;
	}

	@Override
	public List<CommunityDTO> findCommunitiesByCity(final String emailId,final String communityName,final String city) {
		String finalquery = FIND_COMMUNITIES_BY_CITY_QUERY;
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		if(StringUtils.isNotBlank(communityName)){
			finalquery = FIND_COMMUNITIES_BY_CITY_QUERY + " AND COMMUNITYNAME LIKE " + "'%"+communityName+"%'";
		}
		mapSqlParameterSource.addValue("CITY", city);
		return getSimpleJdbcTemplate().query(finalquery, new RowMapper<CommunityDTO>() {
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
	public List<CommunityDTO> findCommunitiesByName(final String emailId,final String communityName) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		mapSqlParameterSource.addValue("COMMUNITYNAME", "%"+communityName+"%");
		return getSimpleJdbcTemplate().query(FIND_COMMUNITIES_BY_NAME_QUERY, new RowMapper<CommunityDTO>() {
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
	public String getCommunityName(long communityId) {
		MapSqlParameterSource paramaSource = new MapSqlParameterSource();
		paramaSource.addValue("COMMUNITYID", communityId);
		return getSimpleJdbcTemplate().queryForObject(GET_COMMUNITY_NAME_QUERY, String.class, paramaSource);
	}

	@Override
	public CommunityDTO getCommunityDetailsByCommunityId(long communityId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", communityId);
		return getSimpleJdbcTemplate().queryForObject(GET_COMMUNITY_DETAILS_BY_ID_QUERY, new RowMapper<CommunityDTO>() {
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


	/*public void setIdIncrementer(final DataFieldMaxValueIncrementer idIncrementer) {
		this.idIncrementer = idIncrementer;
	}
	
	*/

}
