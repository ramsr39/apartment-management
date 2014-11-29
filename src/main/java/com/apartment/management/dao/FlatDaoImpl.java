package com.apartment.management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.apartment.management.dto.FlatDTO;

public class FlatDaoImpl extends NamedParameterJdbcDaoSupport implements FlatDao {
	
	private static final String INSERT_FLAT_DETAILS_QUERY="INSERT INTO flatunit("
			+ "UNITNO,"
			+ "UNITSIZE,"
			+ "FLOORNO,"
			+ "TOTAL_BED_ROOMS,"
			+ "TOTAL_BATH_ROOMS,"
			+ "TOTAL_ROOMS,"
			+ "RESIDENTTYPE,"
			+ "BUILDINGID,"
			+ "TOTAL_FOUR_WHEELER_PARKINGS,"
			+ "TOTAL_TWO_WHEELER_PARKINGS,"
			+ "TWO_WHEELER_PARKING,"
			+ "FOUR_WHEELER_PARKING)"
			   + "VALUES("
			   + ":UNITNO,"
			   + ":UNITSIZE,"
			   + ":FLOORNO,"
			   + ":TOTAL_BED_ROOMS,"
			   + ":TOTAL_BATH_ROOMS,"
			   + ":TOTAL_ROOMS,"
			   + ":RESIDENTTYPE,"
			   + ":BUILDINGID,"
			   + ":TOTAL_FOUR_WHEELER_PARKINGS,"
			   + ":TOTAL_TWO_WHEELER_PARKINGS,"
			   + ":TWO_WHEELER_PARKING,"
			   + ":FOUR_WHEELER_PARKING)";
	
	private static final String UPDATE_FLAT_DETAILS_QUERY="UPDATE flatunit SET "
			+ "UNITNO=:UNITNO,"
			+ "UNITSIZE=:UNITSIZE,"
			+ "FLOORNO=:FLOORNO,"
			+ "TOTAL_BED_ROOMS=:TOTAL_BED_ROOMS,"
			+ "TOTAL_BATH_ROOMS=:TOTAL_BATH_ROOMS,"
			+ "TOTAL_ROOMS=:TOTAL_ROOMS,"
			+ "RESIDENTTYPE=:RESIDENTTYPE,"
			+ "BUILDINGID=:BUILDINGID,"
			+ "TOTAL_FOUR_WHEELER_PARKINGS=:TOTAL_FOUR_WHEELER_PARKINGS,"
			+ "TOTAL_TWO_WHEELER_PARKINGS=:TOTAL_TWO_WHEELER_PARKINGS,"
			+ "TWO_WHEELER_PARKING=:TWO_WHEELER_PARKING,"
			+ "FOUR_WHEELER_PARKING=:FOUR_WHEELER_PARKING "
			+"WHERE FLATID=:FLATID";

	private static final String DELETE_FLAT_QUERY = "DELETE FROM flatunit WHERE FLATID=:FLATID";

	private static final String FIND_FLAT_DETAILS = "SELECT * FROM flatunit WHERE BUILDINGID=:BUILDINGID";

	@Override
	public long save(final FlatDTO flatDTO) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("UNITNO", flatDTO.getUnitNumber());
		namedParameterSource.addValue("UNITSIZE", flatDTO.getUnitSize());
		namedParameterSource.addValue("FLOORNO", flatDTO.getFloorNumber());
		namedParameterSource.addValue("TOTAL_BED_ROOMS", flatDTO.getTotalBedRooms());
		namedParameterSource.addValue("TOTAL_BATH_ROOMS", flatDTO.getTotalBathRooms());
		namedParameterSource.addValue("TOTAL_ROOMS", flatDTO.getTotalRooms());
		namedParameterSource.addValue("TWO_WHEELER_PARKING", flatDTO.getTwoWheelerParking());
		namedParameterSource.addValue("TOTAL_TWO_WHEELER_PARKINGS", flatDTO.getTotalTwoWheelerParkings());
		namedParameterSource.addValue("FOUR_WHEELER_PARKING", flatDTO.getFourWheelerParking());
		namedParameterSource.addValue("TOTAL_FOUR_WHEELER_PARKINGS", flatDTO.getTotalFourWheelerParkings());
		namedParameterSource.addValue("RESIDENTTYPE", flatDTO.getResidentType());
		namedParameterSource.addValue("BUILDINGID", flatDTO.getBuildingId());
		getNamedParameterJdbcTemplate().update(INSERT_FLAT_DETAILS_QUERY, namedParameterSource, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public void delete(final long flatId) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("FLATID", flatId);
		getNamedParameterJdbcTemplate().update(DELETE_FLAT_QUERY, namedParameterSource);
	}

	@Override
	public void update(final FlatDTO flatDTO) {
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("FLATID", flatDTO.getId());
		namedParameterSource.addValue("UNITNO", flatDTO.getUnitNumber());
		namedParameterSource.addValue("UNITSIZE", flatDTO.getUnitSize());
		namedParameterSource.addValue("FLOORNO", flatDTO.getFloorNumber());
		namedParameterSource.addValue("TOTAL_BED_ROOMS", flatDTO.getTotalBedRooms());
		namedParameterSource.addValue("TOTAL_BATH_ROOMS", flatDTO.getTotalBathRooms());
		namedParameterSource.addValue("TOTAL_ROOMS", flatDTO.getTotalRooms());
		namedParameterSource.addValue("TWO_WHEELER_PARKING", flatDTO.getTwoWheelerParking());
		namedParameterSource.addValue("TOTAL_TWO_WHEELER_PARKINGS", flatDTO.getTotalTwoWheelerParkings());
		namedParameterSource.addValue("FOUR_WHEELER_PARKING", flatDTO.getFourWheelerParking());
		namedParameterSource.addValue("TOTAL_FOUR_WHEELER_PARKINGS", flatDTO.getTotalFourWheelerParkings());
		namedParameterSource.addValue("RESIDENTTYPE", flatDTO.getResidentType());
		namedParameterSource.addValue("BUILDINGID", flatDTO.getBuildingId());
		getNamedParameterJdbcTemplate().update(UPDATE_FLAT_DETAILS_QUERY, namedParameterSource);
	}

	@Override
	public List<FlatDTO> findFlatDetails(final long buildingId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("BUILDINGID", buildingId);
		return getNamedParameterJdbcTemplate().query(FIND_FLAT_DETAILS, mapSqlParameterSource,
				new RowMapper<FlatDTO>() {
					@Override
					public FlatDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						FlatDTO flatDTO = new FlatDTO();
						flatDTO.setId(rs.getInt("FLATID"));
						flatDTO.setFloorNumber(rs.getString("FLOORNO"));
						flatDTO.setTotalBathRooms(rs.getInt("TOTAL_BATH_ROOMS"));
						flatDTO.setTotalBedRooms(rs.getInt("TOTAL_BED_ROOMS"));
						flatDTO.setTotalRooms(rs.getInt("TOTAL_ROOMS"));
						flatDTO.setUnitNumber(rs.getString("UNITNO"));
						flatDTO.setUnitSize(rs.getInt("UNITSIZE"));
						flatDTO.setTotalTwoWheelerParkings(rs.getInt("TOTAL_TWO_WHEELER_PARKING"));
						flatDTO.setTotalFourWheelerParkings(rs.getInt("TOTAL_FOUR_WHEELER_PARKING"));
						flatDTO.setFourWheelerParking(rs.getString("FOUR_WHEELER_PARKING"));
						flatDTO.setTwoWheelerParking(rs.getString("TWO_WHEELER_PARKING"));
						flatDTO.setResidentType(rs.getString("RESIDENTTYPE"));
						flatDTO.setBuildingId(buildingId);
						return flatDTO;
					}
				});
	}

}


