package com.apartment.management.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dao.AppointmentDao;
import com.apartment.management.dto.AppointmentDTO;
import com.apartment.management.dto.ContactDTO;

public class AppointmentDaoImpl extends SimpleJdbcDaoSupport implements AppointmentDao {

  private static final String GET_APPOINTMENT_BASE_QUERY = "SELECT * FROM appointment";

  private static final String INSERT_APPOINTMENT_QUERY =
    "INSERT INTO appointment(APT_ID,APT_DATE,REMIND_ME,CONTANCT_ID,FLAT_ID,BUILDING_ID,COMMUNITY_ID,USER_ID) "
        + "values(:APT_ID,:APT_DATE,:REMIND_ME,:CONTANCT_ID,:FLAT_ID,:BUILDING_ID,:COMMUNITY_ID,:USER_ID)";

  private static final String UPDATE_APPOINTMENT_QUERY =
    "UPDATE appointment SET APT_DATE=:APT_DATE,REMIND_ME=:REMIND_ME,APT_STATUS=:APT_STATUS WHERE APT_ID=:APT_ID";

  private static final String DELETE_APPOINTMENT_QUERY = "DELETE FROM appointment WHERE APT_ID=:APT_ID";

  @Override
  public String save(final AppointmentDTO appointmentDTO) {
    final String appointmentId = "APT" + RandomStringUtils.randomNumeric(8);
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("APT_ID", appointmentId);
    namedSqlParamSource.addValue("APT_DATE", withUtc(appointmentDTO.getAppointmentDate().getMillis()));
    namedSqlParamSource.addValue("REMIND_ME", withUtc(appointmentDTO.getRemindMe().getMillis()));
    namedSqlParamSource.addValue("CONTANCT_ID", appointmentDTO.getContactDTO().getId());
    namedSqlParamSource.addValue("FLAT_ID", appointmentDTO.getFlatId());
    namedSqlParamSource.addValue("BUILDING_ID", appointmentDTO.getBuildingId());
    namedSqlParamSource.addValue("COMMUNITY_ID", appointmentDTO.getCommunityId());
    namedSqlParamSource.addValue("USER_ID", appointmentDTO.getUserId());
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
    namedSqlParamSource.addValue("APT_DATE", withUtc(appointmentDTO.getAppointmentDate().getMillis()));
    namedSqlParamSource.addValue("REMIND_ME", withUtc(appointmentDTO.getRemindMe().getMillis()));
    namedSqlParamSource.addValue("APT_STATUS", appointmentDTO.getStatus());
    namedSqlParamSource.addValue("CONTANCT_ID", appointmentDTO.getContactDTO().getId());
    getSimpleJdbcTemplate().update(UPDATE_APPOINTMENT_QUERY, namedSqlParamSource);
  }

  public static Calendar withUtc(final long timeMillis) {
    return withTimeZone(timeMillis, TimeZone.getTimeZone("UTC"));
  }

  public static Calendar withTimeZone(final long timestampInMillis, final TimeZone timeZone) {
    Calendar newCalendar = Calendar.getInstance(timeZone);
    newCalendar.setTimeInMillis(timestampInMillis);
    return newCalendar;
  }

  @Override
  public List<AppointmentDTO> getCommunityAppointments(final String communityId) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("COMMUNITY_ID", communityId);
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ").append("WHERE COMMUNITY_ID=:COMMUNITY_ID");
    return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
  }

  @Override
  public List<AppointmentDTO> getBuildingAppointments(final String buildingId) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("BUILDING_ID", buildingId);
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ").append("WHERE BUILDING_ID=:BUILDING_ID");
    return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
  }

  @Override
  public List<AppointmentDTO> getFlatAppointments(final String flatId) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("FLAT_ID", flatId);
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ").append("WHERE FLAT_ID=:FLAT_ID");
    return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
  }

  @Override
  public List<AppointmentDTO> getUserAppointments(final String userId) {
    final MapSqlParameterSource namedSqlParamSource = new MapSqlParameterSource();
    namedSqlParamSource.addValue("USER_ID", userId);
    final StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append(GET_APPOINTMENT_BASE_QUERY).append(" ").append("WHERE USER_ID=:USER_ID");
    return getSimpleJdbcTemplate().query(queryBuilder.toString(), getAppointmentRowmapper(), namedSqlParamSource);
  }

  private RowMapper<AppointmentDTO> getAppointmentRowmapper() {
    return new RowMapper<AppointmentDTO>() {
      @Override
      public AppointmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(rs.getString("APT_ID"));
        appointmentDTO.setStatus(rs.getString("APT_STATUS"));
        appointmentDTO.setAppointmentDate(new DateTime(rs.getTimestamp("APT_DATE")));
        appointmentDTO.setRemindMe(new DateTime(rs.getTimestamp("REMIND_ME")));
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(rs.getString("CONTACT_ID"));
        appointmentDTO.setContactDTO(contactDTO);
        return appointmentDTO;
      }
    };
  }

}
