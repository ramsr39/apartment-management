package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.AppointmentDao;
import com.apartment.management.dto.AppointmentDTO;
import com.apartment.management.dto.ContactDTO;

public class AppointmentDaoImpl extends SimpleJdbcDaoSupport implements AppointmentDao {

  private static final org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(AppointmentDaoImpl.class);

  private static final String GET_APPOINTMENT_BASE_QUERY = "SELECT * FROM appointment";

  private static final String INSERT_APPOINTMENT_QUERY =
    "INSERT INTO appointment(APT_ID,APT_DATE,REMIND_ME,DESCRIPTION,IS_VISIBLE_TO_OWNER,IS_VISIBLE_TO_TENANT,APPROVED_STATUS,APPROVED_BY,CONTACT_ID,FLAT_ID,BUILDING_ID,COMMUNITY_ID,USER_ID,CREATED_BY) "
        + "values(:APT_ID,:APT_DATE,:REMIND_ME,:DESCRIPTION,:IS_VISIBLE_TO_OWNER,:IS_VISIBLE_TO_TENANT,:APPROVED_STATUS,:APPROVED_BY,:CONTACT_ID,:FLAT_ID,:BUILDING_ID,:COMMUNITY_ID,:USER_ID,:CREATED_BY)";

  private static final String UPDATE_APPOINTMENT_QUERY =
    "UPDATE appointment SET APT_DATE=:APT_DATE,REMIND_ME=:REMIND_ME,"
    + "APT_STATUS=:APT_STATUS,"
    + "DESCRIPTION=:DESCRIPTION,"
    + "IS_VISIBLE_TO_OWNER=:IS_VISIBLE_TO_OWNER,"
    + "IS_VISIBLE_TO_TENANT=:IS_VISIBLE_TO_TENANT,"
    + "APPROVED_STATUS=:APPROVED_STATUS,"
    + "APPROVED_BY=:APPROVED_BY"
    + "UPDATED_BY=:UPDATED_BY,"
    + " WHERE APT_ID=:APT_ID";

  private static final String DELETE_APPOINTMENT_QUERY = "DELETE FROM appointment WHERE APT_ID=:APT_ID";

  @Override
  public String save(final AppointmentDTO appointmentDTO) {
    LOG.info("appointment save start...................");
    final String appointmentId = "APT" + RandomStringUtils.randomNumeric(8);
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("APT_ID", appointmentId);
    namedSqlParamSource.addValue("DESCRIPTION", appointmentDTO.getDescription());
    if (StringUtils.isNotBlank(appointmentDTO.getIsVisibleToTenant())) {
      namedSqlParamSource.addValue("IS_VISIBLE_TO_TENANT", appointmentDTO.getIsVisibleToTenant());
    } else {
      namedSqlParamSource.addValue("IS_VISIBLE_TO_TENANT", "false");
    }
    if (StringUtils.isNotBlank(appointmentDTO.getIsVisibleToOwner())) {
      namedSqlParamSource.addValue("IS_VISIBLE_TO_OWNER", appointmentDTO.getIsVisibleToOwner());
    } else {
      namedSqlParamSource.addValue("IS_VISIBLE_TO_OWNER", "false");
    }
    namedSqlParamSource.addValue("APPROVED_STATUS", appointmentDTO.getApprovedStatus());
    namedSqlParamSource.addValue("APPROVED_BY", appointmentDTO.getApprovedBy());
    namedSqlParamSource.addValue("APT_DATE", getUtcTime(appointmentDTO.getAppointmentDate().getMillis()));
    namedSqlParamSource.addValue("REMIND_ME", getUtcTime(appointmentDTO.getRemindMe().getMillis()));
    namedSqlParamSource.addValue("CONTACT_ID", appointmentDTO.getContactDTO().getId());
    namedSqlParamSource.addValue("FLAT_ID", appointmentDTO.getFlatId());
    namedSqlParamSource.addValue("BUILDING_ID", appointmentDTO.getBuildingId());
    namedSqlParamSource.addValue("COMMUNITY_ID", appointmentDTO.getCommunityId());
    namedSqlParamSource.addValue("USER_ID", appointmentDTO.getUserId());
    namedSqlParamSource.addValue("CREATED_BY", appointmentDTO.getCreatedBy());
    getSimpleJdbcTemplate().update(INSERT_APPOINTMENT_QUERY, namedSqlParamSource);
    return appointmentId;
  }

  @Override
  public void delete(final String appointmentId) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("APT_ID", appointmentId);
    getSimpleJdbcTemplate().update(DELETE_APPOINTMENT_QUERY, namedSqlParamSource);
  }

  @Override
  public void update(final AppointmentDTO appointmentDTO) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("APT_ID", appointmentDTO.getId());
    namedSqlParamSource.addValue("DESCRIPTION", appointmentDTO.getDescription());
    namedSqlParamSource.addValue("IS_VISIBLE_TO_TENANT", appointmentDTO.getIsVisibleToTenant());
    namedSqlParamSource.addValue("IS_VISIBLE_TO_OWNER", appointmentDTO.getIsVisibleToOwner());
    namedSqlParamSource.addValue("APT_DATE", getUtcTime(appointmentDTO.getAppointmentDate().getMillis()));
    namedSqlParamSource.addValue("REMIND_ME", getUtcTime(appointmentDTO.getRemindMe().getMillis()));
    namedSqlParamSource.addValue("APT_STATUS", appointmentDTO.getStatus());
    namedSqlParamSource.addValue("APPROVED_BY", appointmentDTO.getApprovedBy());
    namedSqlParamSource.addValue("APPROVED_STATUS", appointmentDTO.getApprovedStatus());
    namedSqlParamSource.addValue("CONTANCT_ID", appointmentDTO.getContactDTO().getId());
    namedSqlParamSource.addValue("UPDATED_BY", appointmentDTO.getUpdatedBy());
    getSimpleJdbcTemplate().update(UPDATE_APPOINTMENT_QUERY, namedSqlParamSource);
  }

  public static Calendar withUtc(final long timeMillis) {
    return withTimeZone(timeMillis, TimeZone.getTimeZone("UTC"));
  }

  public static Calendar getUtcTime(final long timestampInMillis) {
    TimeZone tz = TimeZone.getDefault();
    int offsetInMillis = tz.getOffset(timestampInMillis);
    Calendar newCalendar = Calendar.getInstance(tz);
    newCalendar.setTimeInMillis(timestampInMillis - offsetInMillis);
    return newCalendar;
  }

  public static Calendar withTimeZone(final long timestampInMillis, final TimeZone timeZone) {
    Calendar newCalendar = Calendar.getInstance(timeZone);
    newCalendar.setTimeInMillis(timestampInMillis);
    return newCalendar;
  }

  @Override
  public List<AppointmentDTO> getCommunityAppointments(final String communityId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ")
          .append("WHERE COMMUNITY_ID=:COMMUNITY_ID AND APT_STATUS='OPEN'");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.warn("no appointments found for community:" + communityId);
      return new ArrayList<AppointmentDTO>();
    }
  }

  @Override
  public List<AppointmentDTO> getBuildingAppointments(final String buildingId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("BUILDING_ID", buildingId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ")
          .append("WHERE BUILDING_ID=:BUILDING_ID AND APT_STATUS='OPEN'");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.warn("no appointments found for building:" + buildingId);
      return new ArrayList<AppointmentDTO>();
    }
  }

  @Override
  public List<AppointmentDTO> getFlatAppointments(final String flatId) {
    try {
      LOG.info("flat appointments:::" + flatId);
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("FLAT_ID", flatId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ")
          .append("WHERE FLAT_ID=:FLAT_ID AND APT_STATUS='OPEN'");
      LOG.info(queryBuilder.toString());
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.warn("no appointments found for flat:" + flatId);
      return new ArrayList<AppointmentDTO>();
    }
  }

  @Override
  public List<AppointmentDTO> getUserAppointments(final String userId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("USER_ID", userId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ").append("WHERE USER_ID=:USER_ID AND APT_STATUS=OPEN");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.warn("no appointments found for user:" + userId);
      return new ArrayList<AppointmentDTO>();
    }
  }

  @Override
  public List<AppointmentDTO> findPendingAppointments(final String userId, final String communityId) {
    try {
      final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
      namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
      final StringBuilder queryBuilder = new StringBuilder();
      queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ")
          .append("WHERE APPROVED_STATUS='PENDING' AND COMMUNITY_ID=:COMMUNITY_ID");
      return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
    } catch (final EmptyResultDataAccessException er) {
      LOG.warn("no pending appointments found for user:" + userId+"and for community:"+communityId);
      return new ArrayList<AppointmentDTO>();
    }
  }

  private RowMapper<AppointmentDTO> getAppointmentRowmapper() {
    LOG.info("appointment rowmapper starting.........");
    return new RowMapper<AppointmentDTO>() {
      @Override
      public AppointmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(rs.getString("APT_ID"));
        appointmentDTO.setStatus(rs.getString("APT_STATUS"));
        appointmentDTO.setIsVisibleToOwner(rs.getString("IS_VISIBLE_TO_OWNER"));
        appointmentDTO.setIsVisibleToTenant(rs.getString("IS_VISIBLE_TO_TENANT"));
        appointmentDTO.setApprovedStatus(rs.getString("APPROVED_STATUS"));
        appointmentDTO.setApprovedBy(rs.getString("APPROVED_BY"));
        appointmentDTO.setAppointmentDate(new DateTime(rs.getTimestamp("APT_DATE").getTime()));
        appointmentDTO.setDescription(rs.getString("DESCRIPTION"));
        appointmentDTO.setCreatedBy(rs.getString("CREATED_BY"));
        appointmentDTO.setRemindMe(new DateTime(rs.getTimestamp("REMIND_ME").getTime()));
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(rs.getString("CONTACT_ID"));
        appointmentDTO.setContactDTO(contactDTO);
        return appointmentDTO;
      }
    };
  }

}
