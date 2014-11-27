package com.apartment.management.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.apartment.management.dao.CommunityDetailsDao;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/user")
public class CommunityDetailsService {
	
	private CommunityDetailsDao communityDetailsDao;

	@POST
	@Path("/{emailId}/save-community")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCommunityDetails(final String payload,@PathParam("emailId") final String emailId) {
		final CommunityDTO communityDTO =JsonUtils.parseJsonToObject(payload,CommunityDTO.class);
		long communityId = communityDetailsDao.saveCommunityDetails(emailId,communityDTO);
		communityDTO.setId(communityId);
		return Response.ok().entity(JsonUtils.convertJavaObjectToJson(communityDTO))
				.build();
	}
	
	@PUT
	@Path("/{emailId}/update-community")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCommunityDetails(final String payload) {
		final CommunityDTO communityDTO =JsonUtils.parseJsonToObject(payload,CommunityDTO.class);
		long communityId = communityDetailsDao.updateCommunityDetails(communityDTO);
		communityDTO.setId(communityId);
		return Response.ok().entity(JsonUtils.convertJavaObjectToJson(communityDTO))
				.build();
	}

	@POST
	@Path("/save-building")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBuildDetails(final String payload) {
		final BuildingDTO buildingDTO = JsonUtils.parseJsonToObject(payload,BuildingDTO.class);
		long buildingId = communityDetailsDao.saveBuildingDetails(buildingDTO);
		buildingDTO.setId(buildingId);
		return Response.ok().entity(JsonUtils.convertJavaObjectToJson(buildingDTO))
				.build();
	}

	
	@PUT
	@Path("/{buildingName}/update-building")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBuildDetails(final String payload,@PathParam("buildingName") final String buildingName) {
		final BuildingDTO buildingDTO = JsonUtils.parseJsonToObject(payload,BuildingDTO.class);
		long buildingId = communityDetailsDao.updateBuildingDetails(buildingDTO,buildingName);
		buildingDTO.setId(buildingId);
		return Response.ok().entity(JsonUtils.convertJavaObjectToJson(buildingDTO))
				.build();
	}

	@GET
	@Path("/{emailId}/find-community-details")
	@Produces(MediaType.APPLICATION_JSON)
	public String findCommunityDetails(@PathParam("emailId") final String emailId){
		CommunityDTO communityDTO = new CommunityDTO();
		if(!communityDetailsDao.isCommnityExistedForUser(emailId)){
		 return JsonUtils.convertJavaObjectToJson(communityDTO);
		}
	    communityDTO = communityDetailsDao.findCommunityDetails(emailId);
		List<BuildingDTO> buildingList = communityDetailsDao.findBuildingDetails(communityDTO.getId());
		communityDTO.setBuildingList(buildingList);
		return JsonUtils.convertJavaObjectToJson(communityDTO);
	}
	
	
	public void setCommunityDetailsDao(
			final CommunityDetailsDao communityDetailsDao) {
		this.communityDetailsDao = communityDetailsDao;
	}

}
