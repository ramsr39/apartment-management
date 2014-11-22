package com.apartment.management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.apartment.management.dto.UserDTO;

public class LoginDaoImpl extends SimpleJdbcDaoSupport implements LoginDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoginDaoImpl.class);

	private static String USER_QUERY = "SELECT count(EMAIL) FROM USERINFO WHERE EMAIL=:EMAILID";

	private static String GET_USER_PWD_QUERY = "SELECT PASSWORD FROM USERINFO WHERE EMAIL=:EMAILID";

	private static String GET_USER_INFO_QUERY = "SELECT USERID,USERNAME,FIRSTNAME,LASTNAME,PHONE,EMAIL,USERROLE FROM USERINFO WHERE EMAIL=:EMAILID";

	@Override
	public boolean isUserExist(final String emailId) {
		LOG.info("isUserExist started for the user:::::::::" + emailId);
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		int count = getSimpleJdbcTemplate().queryForInt(USER_QUERY,
				mapSqlParameterSource);
		if (0 == count) {
			return false;
		}
		LOG.info("isUserExist end");
		return true;
	}

	@Override
	public String getPasswordForUser(final String emailId) {
		LOG.info("getPasswordForUser started for the user:::::::::" + emailId);
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", emailId);
		return getSimpleJdbcTemplate().queryForObject(GET_USER_PWD_QUERY,
				String.class, mapSqlParameterSource);
	}

	@Override
	public UserDTO getUserDetails(String userName) {
		LOG.info("getUserDetails started for the user:::::::::" + userName);
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAILID", userName);
		return getSimpleJdbcTemplate().queryForObject(GET_USER_INFO_QUERY,
				new RowMapper<UserDTO>() {
					@Override
					public UserDTO mapRow(final ResultSet rs, final int rowNum)
							throws SQLException {
						UserDTO dto = new UserDTO();
						dto.setUserId(rs.getString("USERID"));
						dto.setEmailId(rs.getString("EMAIL"));
						dto.setRole(rs.getString("USERROLE"));
						dto.setUserName(rs.getString("USERNAME"));
						dto.setPhoneNumber(rs.getInt("PHONE"));
						dto.setFirstName(rs.getString("FIRSTNAME"));
						dto.setLastName(rs.getString("LASTNAME"));
						return dto;
					}
				}, mapSqlParameterSource);
	}

}
