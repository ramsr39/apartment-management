package com.apartment.management.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import com.apartment.management.dao.LoginDao;
import com.apartment.management.dto.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/login")
public final class LoginService {

	private LoginDao loginDao;

	@GET
	@Path("/{email}")
	@Produces("applicaton/json")
	public Response verifyUserId(@PathParam("email") final String email) {
		boolean falg = loginDao.isUserExist(email);
		return Response.ok().entity(String.valueOf(falg)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ValidateUser(final String jsonPayload) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = (JsonObject) parser.parse(jsonPayload);
		String userName = jsonObject.get("email").getAsString();
		String receivePwd = jsonObject.get("password").getAsString();
		String existingPwd = loginDao.getPasswordForUser(userName);
		if (StringUtils.isEmpty(receivePwd) || StringUtils.isEmpty(existingPwd)
				|| !receivePwd.equalsIgnoreCase(existingPwd)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		UserDTO userLoginDTO = loginDao.getUserDetails(userName);
		String responsePayload = convertJavaObjectToJson(userLoginDTO);
		return Response.ok().entity(responsePayload).build();

	}

	private String convertJavaObjectToJson(final UserDTO userLoginDTO) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(userLoginDTO);

	}

	public void setLoginDao(final LoginDao loginDao) {
		this.loginDao = loginDao;
	}

}
