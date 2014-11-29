package com.apartment.management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.apartment.management.dto.BuildingDTO;

public class BuildingDaoImpl extends NamedParameterJdbcDaoSupport
		implements BuildingDao {
	
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
	
	private static final String UPDATE_BUILDING_QUERY = "UPDATE building SET "
			+ "BUILDINGNAME=:BUILDINGNAME,"
			+ "NOOFUNITS=:NOOFUNITS,"
			+ "NOOFFLOORS=:NOOFFLOORS,"
			+ "IMAGE_URL=:IMAGE_URL "
			+ "WHERE BUILDINGID=:BUILDINGID"; 
	
	 private static final String GET_BUILDING_DETAILS_QUERY="SELECT BUILDINGID,"
		 		+ "BUILDINGNAME,"
		 		+ "NOOFFLOORS,"
		 		+ "NOOFUNITS,"
		 		+ "IMAGE_URL FROM building "
		 		+ "WHERE COMMUNITYID=:COMMUNITYID";
	 
	 private static final String FIND_BUILDING_DETAILS_BY_BUILDINGID_QUERY="SELECT * FROM building WHERE BUILDINGID=:BUILDINGID";
	 
	
	private static final String DELETE_BUILDING_QUERY = "DELETE FROM building WHERE BUILDINGID=:BUILDINGID"; 

	@Override
	public long save(final BuildingDTO buildingDTO) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", buildingDTO.getCommunityId());
		mapSqlParameterSource.addValue("BUILDINGNAME", buildingDTO.getName());
		mapSqlParameterSource.addValue("NOOFUNITS", buildingDTO.getTotalUnits());
		mapSqlParameterSource.addValue("NOOFFLOORS", buildingDTO.getTotalFloors());
		mapSqlParameterSource.addValue("IMAGE_URL", buildingDTO.getImageUrl());
	    getNamedParameterJdbcTemplate().update(INSERT_BUILDING_QUERY, mapSqlParameterSource,keyHolder);
	    return keyHolder.getKey().longValue();
	}

	@Override
	public void update(final BuildingDTO buildingDTO) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("COMMUNITYID", buildingDTO.getCommunityId());
		mapSqlParameterSource.addValue("BUILDINGNAME", buildingDTO.getName());
		mapSqlParameterSource.addValue("NOOFUNITS", buildingDTO.getTotalUnits());
		mapSqlParameterSource.addValue("NOOFFLOORS", buildingDTO.getTotalFloors());
		mapSqlParameterSource.addValue("IMAGE_URL", buildingDTO.getImageUrl());
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
						buildingDto.setImageUrl(rs.getString("IMAGE_URL"));
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
						buildingDto.setImageUrl(rs.getString("IMAGE_URL"));
						return buildingDto;
					}
				});
	}

}