package com.apartment.management.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.apartment.management.dao.ManageUserDao;
import com.apartment.management.dto.UserDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/users")
public class ManageUserService {

	private ManageUserDao manageUserDao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(final String payload) {
		UserDTO userDTO = JsonUtils.parseJsonToObject(payload, UserDTO.class);
		if (null == userDTO) {
			throw new RuntimeException("unable to parse user information");
		}
		long userId = manageUserDao.save(userDTO);
		userDTO.setUserId(userId);
		return Response.ok().entity(JsonUtils.parseObjectToJson(userDTO))
				.build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(final String payload) {
		UserDTO userDTO = JsonUtils.parseJsonToObject(payload, UserDTO.class);
		if (null == userDTO) {
			throw new RuntimeException("unable to parse user information");
		}
		manageUserDao.update(userDTO);
		return Response.ok().entity(JsonUtils.parseObjectToJson(userDTO))
				.build();
	}

	@DELETE
	public Response deleteUser(@QueryParam("userId") final long userId) {
		manageUserDao.delete(userId);
		return Response.ok().build();
	}
	
	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	public String findUsers(@QueryParam("firstName") final String firstName,
			@QueryParam("lastName") final String lastName,
			@QueryParam("emailId") final String emailId,
			@QueryParam("phoneNumber") final String phoneNumber,
			@QueryParam("uidNumber") final String uidNumber) {
		List<UserDTO> userList = new ArrayList<UserDTO>();
		userList = manageUserDao.findUsers(firstName,lastName,emailId,phoneNumber,uidNumber);
		return JsonUtils.parseObjectToJson(userList);
	}
	

	public void setManageUserDao(final ManageUserDao manageUserDao) {
		this.manageUserDao = manageUserDao;
	}

}
