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

import com.apartment.management.dao.FlatDao;
import com.apartment.management.dto.FlatDTO;
import com.apartment.management.dto.UserDTO;

public class FlatDaoImpl extends NamedParameterJdbcDaoSupport implements FlatDao {
	
	private static final String FLATID_COUNT_QUERY = "SELECT count(FLATID) from flatunit WHERE BUILDINGID=:BUILDINGID";

	private static final Logger LOG = LoggerFactory.getLogger(FlatDaoImpl.class);
	
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
			+ "FOUR_WHEELER_PARKING,DESCRIPTION,OWNER_ID,TENANT_ID)"
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
			   + ":FOUR_WHEELER_PARKING,:DESCRIPTION,:OWNER_ID,:TENANT_ID)";
	
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
			+ "FOUR_WHEELER_PARKING=:FOUR_WHEELER_PARKING,"
			+ "DESCRIPTION=:DESCRIPTION,"
			+ "OWNER_ID=:OWNER_ID,"
			+ "TENANT_ID=:TENANT_ID"
			+ " WHERE FLATID=:FLATID";

	private static final String DELETE_FLAT_QUERY = "DELETE FROM flatunit WHERE FLATID=:FLATID";

	private static final String FIND_FLAT_DETAILS = "SELECT * FROM flatunit WHERE BUILDINGID=:BUILDINGID";

	private static final String GET_FLAT_OWNER_QUERY = "SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo WHERE EMAILID=:OWNEREID";

	private static final String GET_FLAT_TENANT_QUERY = "SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo WHERE EMAILID=:TENANTID";

	@Override
	public long save(final FlatDTO flatDTO) {
		LOG.info("FlatDaoImpl save::::start");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		prepareParameterSource(flatDTO, namedParameterSource);
		getNamedParameterJdbcTemplate().update(INSERT_FLAT_DETAILS_QUERY, namedParameterSource, keyHolder);
		LOG.info("FlatDaoImpl save::::end");
		return keyHolder.getKey().longValue();
	}

	@Override
	public void update(final FlatDTO flatDTO) {
		LOG.info("FlatDaoImpl update::::start");
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("FLATID", flatDTO.getId());
		prepareParameterSource(flatDTO, namedParameterSource);
		getNamedParameterJdbcTemplate().update(UPDATE_FLAT_DETAILS_QUERY, namedParameterSource);
		LOG.info("FlatDaoImpl update::::end");
	}

	private void prepareParameterSource(final FlatDTO flatDTO,
			MapSqlParameterSource namedParameterSource) {
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
		namedParameterSource.addValue("DESCRIPTION", flatDTO.getDescription());
		namedParameterSource.addValue("OWNER_ID", flatDTO.getOwnerDetails().getEmailId());
		namedParameterSource.addValue("TENANT_ID", flatDTO.getTenantDetails().getEmailId());
	}

	@Override
	public void delete(final long flatId) {
		LOG.info("FlatDaoImpl delete::::start");
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("FLATID", flatId);
		getNamedParameterJdbcTemplate().update(DELETE_FLAT_QUERY, namedParameterSource);
		LOG.info("FlatDaoImpl delete::::end");
	}

	@Override
	public List<FlatDTO> findFlatDetails(final long buildingId) {
		LOG.info("FlatDaoImpl findFlatDetails by buildingId::::start");
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("BUILDINGID", buildingId);
		return getNamedParameterJdbcTemplate().query(FIND_FLAT_DETAILS, mapSqlParameterSource,
				new RowMapper<FlatDTO>() {
					@Override
					public FlatDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						LOG.info("FlatDaoImpl findFlatDetails mapRow::::start");
						FlatDTO flatDTO = new FlatDTO();
						flatDTO.setId(rs.getInt("FLATID"));
						flatDTO.setFloorNumber(rs.getString("FLOORNO"));
						flatDTO.setTotalBathRooms(rs.getInt("TOTAL_BATH_ROOMS"));
						flatDTO.setTotalBedRooms(rs.getInt("TOTAL_BED_ROOMS"));
						flatDTO.setTotalRooms(rs.getInt("TOTAL_ROOMS"));
						flatDTO.setUnitNumber(rs.getString("UNITNO"));
						flatDTO.setUnitSize(rs.getInt("UNITSIZE"));
						flatDTO.setTotalTwoWheelerParkings(rs.getInt("TOTAL_TWO_WHEELER_PARKINGS"));
						flatDTO.setTotalFourWheelerParkings(rs.getInt("TOTAL_FOUR_WHEELER_PARKINGS"));
						flatDTO.setFourWheelerParking(rs.getString("FOUR_WHEELER_PARKING"));
						flatDTO.setTwoWheelerParking(rs.getString("TWO_WHEELER_PARKING"));
						flatDTO.setResidentType(rs.getString("RESIDENTTYPE"));
						flatDTO.setDescription(rs.getString("DESCRIPTION"));
						String ownerId =rs.getString("OWNER_ID");
						String tenantId=rs.getString("TENANT_ID");
						flatDTO.setBuildingId(buildingId);
						if (null != ownerId) {
							flatDTO.setOwnerDetails(getFlatOwnerDetails(ownerId));
						}
						if (null != tenantId) {
							flatDTO.setTenantDetails(getFaltTenantDetails(tenantId));
						}
						LOG.info("FlatDaoImpl findFlatDetails mapRow ::::end");
						return flatDTO;
					}

				});
	}

	private UserDTO getFlatOwnerDetails(final String ownerId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("OWNERID", ownerId);
		return getNamedParameterJdbcTemplate().queryForObject(GET_FLAT_OWNER_QUERY, mapSqlParameterSource, new RowMapper<UserDTO>() {
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

	private UserDTO getFaltTenantDetails(final String ownerId) {
		LOG.info("FlatDaoImpl getTenantDetailsForFalt ::::start");
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("TENANTID", ownerId);
		return getNamedParameterJdbcTemplate().queryForObject(GET_FLAT_TENANT_QUERY, mapSqlParameterSource, new RowMapper<UserDTO>() {
			@Override
			public UserDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				LOG.info("FlatDaoImpl getTenantDetailsForFalt mapRow::::start");
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(rs.getLong("USERID"));
				userDTO.setFirstName(rs.getString("FIRSTNAME"));
				userDTO.setLastName(rs.getString("LASTNAME"));
				userDTO.setEmailId(rs.getString("EMAILID"));
				userDTO.setPrimaryPhoneNumber(rs.getString("PRIMARY_PH_NO"));
				LOG.info("FlatDaoImpl getTenantDetailsForFalt mapRow::::end");
				return userDTO;
			}
		});
	}

	@Override
	public long getTotalNumberOfFlatsForBuilding(final List<Long> buildingIdList) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		long toatlUnits=0;
		for (Long buildingId : buildingIdList) {
			mapSqlParameterSource.addValue("BUILDINGID", buildingId);
			long count = getNamedParameterJdbcTemplate().queryForLong(FLATID_COUNT_QUERY, mapSqlParameterSource);
			toatlUnits=toatlUnits+count;
		}
		return toatlUnits;
	}
}


