package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.apartment.management.dao.FlatDao;
import com.apartment.management.dto.CoOccupantDTO;
import com.apartment.management.dto.FlatDTO;
import com.apartment.management.dto.UserDTO;

public class FlatDaoImpl extends NamedParameterJdbcDaoSupport implements FlatDao {

	private static final String FIND_CO_OCCUPANTS_FOR_FLAT_USER_QUERY = "SELECT *FROM co_occupants_info WHERE FLAT_ID=:FLAT_ID AND USER_ID=:USER_ID";

	private static final Logger LOG = LoggerFactory.getLogger(FlatDaoImpl.class);
	
	private static final String INSERT_FLAT_DETAILS_QUERY="INSERT INTO flatunit("
			+ "FLAT_ID,"
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
			   + ":FLAT_ID,"
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
			+ " WHERE FLAT_ID=:FLAT_ID";

	private static final String DELETE_FLAT_QUERY = "DELETE FROM flatunit WHERE FLAT_ID=:FLAT_ID";

	private static final String FIND_FLAT_DETAILS = "SELECT * FROM flatunit WHERE BUILDINGID=:BUILDINGID";
	
	private static final String FIND_FLAT_DETAILS_BY_FLAT_ID = "SELECT * FROM flatunit WHERE FLAT_ID=:FLAT_ID";

	private static final String GET_FLAT_OWNER_QUERY = "SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo WHERE EMAILID=:OWNERID";

	private static final String GET_FLAT_TENANT_QUERY = "SELECT USERID,FIRSTNAME,LASTNAME,EMAILID,PRIMARY_PH_NO FROM userinfo WHERE EMAILID=:TENANTID";
	
	private static final String GET_FLAT_DETAILS_BY_USER = "SELECT FLAT_ID,"
			+ "FLOORNO,"
			+ "UNITNO,"
			+ "OWNER_ID,"
			+ "TENANT_ID,"
			+ "BUILDINGID"
			+ " FROM flatunit"
			+ " WHERE OWNER_ID=:OWNER_ID OR TENANT_ID=:TENANT_ID";

	private static final String FLAT_ID_COUNT_QUERY = "SELECT count(FLAT_ID) from flatunit WHERE BUILDINGID=:BUILDINGID";


	@Override
	public String save(final FlatDTO flatDTO) {
		LOG.info("FlatDaoImpl save::::start");
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		final String flatId = "F"+RandomStringUtils.randomNumeric(8).toUpperCase();
		namedParameterSource.addValue("FLAT_ID", flatId);
		prepareParameterSource(flatDTO, namedParameterSource);
		getNamedParameterJdbcTemplate().update(INSERT_FLAT_DETAILS_QUERY, namedParameterSource);
		LOG.info("FlatDaoImpl save::::end");
		return flatId;
	}

	@Override
	public void update(final FlatDTO flatDTO) {
		LOG.info("FlatDaoImpl update::::start");
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("FLAT_ID", flatDTO.getId());
		prepareParameterSource(flatDTO, namedParameterSource);
		getNamedParameterJdbcTemplate().update(UPDATE_FLAT_DETAILS_QUERY, namedParameterSource);
		LOG.info("FlatDaoImpl update::::end");
	}

	@Override
	public void delete(final String flatId) {
		LOG.info("FlatDaoImpl delete::::start");
		MapSqlParameterSource namedParameterSource = new MapSqlParameterSource();
		namedParameterSource.addValue("FLAT_ID", flatId);
		getNamedParameterJdbcTemplate().update(DELETE_FLAT_QUERY, namedParameterSource);
		LOG.info("FlatDaoImpl delete::::end");
	}

	@Override
	public List<FlatDTO> findFlatDetailsByBuildingId(final String buildingId) {
		LOG.info("FlatDaoImpl findFlatDetails by buildingId::::start");
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("BUILDINGID", buildingId);
		return getNamedParameterJdbcTemplate().query(FIND_FLAT_DETAILS, mapSqlParameterSource,
				new RowMapper<FlatDTO>() {
					@Override
					public FlatDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						LOG.info("FlatDaoImpl findFlatDetails mapRow::::start");
						FlatDTO flatDTO = new FlatDTO();
						flatDTO.setId(rs.getString("FLAT_ID"));
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

	@Override
	public FlatDTO getFlatDetailsByFaltId(final String flatId) {
		LOG.info("FlatDaoImpl getFlatDetailsByFaltId::::start");
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("FLAT_ID", flatId);
		return getNamedParameterJdbcTemplate().queryForObject(FIND_FLAT_DETAILS_BY_FLAT_ID, mapSqlParameterSource,
				new RowMapper<FlatDTO>() {
					@Override
					public FlatDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						LOG.info("FlatDaoImpl getFlatDetailsByFaltId by mapRow::::start");
						FlatDTO flatDTO = new FlatDTO();
						flatDTO.setId(flatId);
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
						flatDTO.setBuildingId(rs.getString("BUILDINGID"));
						if (null != ownerId) {
							flatDTO.setOwnerDetails(getFlatOwnerDetails(ownerId));
						}
						if (null != tenantId) {
							flatDTO.setTenantDetails(getFaltTenantDetails(tenantId));
						}
						LOG.info("FlatDaoImpl getFlatDetailsByFaltId mapRow ::::end");
						return flatDTO;
					}

				});
	}

	
	@Override
	public long getTotalNumberOfFlatsForBuilding(final List<String> buildingIdList) {
		final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		LOG.info("getTotalNumberOfFlatsForBuilding::::start");
		long toatlUnits=0;
		for (String buildingId : buildingIdList) {
			mapSqlParameterSource.addValue("BUILDINGID", buildingId);
			long count = getNamedParameterJdbcTemplate().queryForLong(FLAT_ID_COUNT_QUERY, mapSqlParameterSource);
			toatlUnits=toatlUnits+count;
			LOG.info("toatlUnits::::"+toatlUnits);
		}
		LOG.info("getTotalNumberOfFlatsForBuilding::::end"+toatlUnits);
		return toatlUnits;
	}

	@Override
	public List<FlatDTO> getFlatDetailsByUser(final String emailId) {
		LOG.info("FlatDaoImpl getFlatDetailsByUser by buildingId::::start");
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("OWNER_ID", emailId);
		mapSqlParameterSource.addValue("TENANT_ID", emailId);
		return getNamedParameterJdbcTemplate().query(GET_FLAT_DETAILS_BY_USER, mapSqlParameterSource,
				new RowMapper<FlatDTO>() {
					@Override
					public FlatDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
						LOG.info("FlatDaoImpl getFlatDetailsByUser mapRow::::start");
						FlatDTO flatDTO = new FlatDTO();
						flatDTO.setId(rs.getString("FLAT_ID"));
						flatDTO.setFloorNumber(rs.getString("FLOORNO"));
						flatDTO.setUnitNumber(rs.getString("UNITNO"));
						String ownerId =rs.getString("OWNER_ID");
						String tenantId=rs.getString("TENANT_ID");
						flatDTO.setBuildingId(rs.getString("BUILDINGID"));
						UserDTO ownerDto = new UserDTO();
						ownerDto.setEmailId(ownerId);
						UserDTO tenantDto = new UserDTO();
						ownerDto.setEmailId(tenantId);
						flatDTO.setOwnerDetails(ownerDto);
						flatDTO.setTenantDetails(tenantDto);
						LOG.info("FlatDaoImpl getFlatDetailsByUser mapRow ::::end");
						return flatDTO;
					}

				});	
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

	private UserDTO getFlatOwnerDetails(final String ownerId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("OWNERID", ownerId);
		return getNamedParameterJdbcTemplate().queryForObject(GET_FLAT_OWNER_QUERY, mapSqlParameterSource, new RowMapper<UserDTO>() {
			@Override
			public UserDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
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

	@Override
	public List<CoOccupantDTO> findCoOccupents(final String flatId, final String userId) {
		try{
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("FLAT_ID", flatId);
		mapSqlParameterSource.addValue("USER_ID", userId);
		return getNamedParameterJdbcTemplate().query(FIND_CO_OCCUPANTS_FOR_FLAT_USER_QUERY, mapSqlParameterSource,new RowMapper<CoOccupantDTO>() {
			@Override
			public CoOccupantDTO mapRow(final ResultSet rs,final int rowNum) throws SQLException {
				LOG.info("FlatDaoImpl findCoOccupents mapRow::::start");
				CoOccupantDTO coOccupantDto = new CoOccupantDTO();
				coOccupantDto.setId("CO_OCCUPANT_ID");
				coOccupantDto.setFirstName(rs.getString("FIRST_NAME"));
				coOccupantDto.setLastName(rs.getString("LAST_NAME"));
				coOccupantDto.setEmailId(rs.getString("EMAIL_ID"));
				coOccupantDto.setPhoneNumber(rs.getString("PHONE_NO"));
				coOccupantDto.setRelation(rs.getString("RELATION"));
				coOccupantDto.setDateOfBirth(rs.getString("DATE_OF_BIRTH"));
				coOccupantDto.setFlatId(flatId);
				coOccupantDto.setUserId(userId);
				LOG.info("FlatDaoImpl getTenantDetailsForFalt mapRow::::end");
				return coOccupantDto;
			}
		});
		}catch(final EmptyResultDataAccessException er){
			LOG.info("found empty coocupants for the user::"+userId+"::"+"flatId:"+flatId);
			return new ArrayList<CoOccupantDTO>();
		}
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
				userDTO.setUserId(rs.getString("USERID"));
				userDTO.setFirstName(rs.getString("FIRSTNAME"));
				userDTO.setLastName(rs.getString("LASTNAME"));
				userDTO.setEmailId(rs.getString("EMAILID"));
				userDTO.setPrimaryPhoneNumber(rs.getString("PRIMARY_PH_NO"));
				LOG.info("FlatDaoImpl getTenantDetailsForFalt mapRow::::end");
				return userDTO;
			}
		});
	}
}


