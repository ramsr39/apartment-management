package com.apartment.management.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.apartment.management.dao.CommunityDetailsDao;
import com.apartment.management.dto.CommunityDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommunityDetailsService {
	
	private CommunityDetailsDao communityDetailsDao;
	

	@POST
	@Path("/{user}/save-community")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCommunityDetails(final String payload) {
		final CommunityDTO communityDTO = parseJsonToObject(payload);
		long communityId = communityDetailsDao.saveCommunityDetails(communityDTO);
		communityDTO.setId(communityId);
		return Response.ok().entity(convertJavaObjectToJson(communityDTO))
				.build();
	}

	@POST
	@Path("/{user}/save-building")
	public Response saveBuildDetails(final String payload) {

		return null;
	}

	private String convertJavaObjectToJson(final CommunityDTO communityDTO) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(communityDTO);

	}

	private CommunityDTO parseJsonToObject(final String json) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, CommunityDTO.class);
	}

	public void setCommunityDetailsDao(
			final CommunityDetailsDao communityDetailsDao) {
		this.communityDetailsDao = communityDetailsDao;
	}

}
