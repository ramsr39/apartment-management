package com.apartment.management.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apartment.management.dao.BuildingDao;
import com.apartment.management.dao.CommunityDetailsDao;
import com.apartment.management.dao.FlatDao;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CommunityDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/community")
public class CommunityDetailsService {

  private static final Logger LOG = LoggerFactory.getLogger(CommunityDetailsService.class);

  private CommunityDetailsDao communityDetailsDao;

  private BuildingDao buildingDao;

  private FlatDao flatDao;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveCommunityDetails(@HeaderParam("user_id")
  final String emailId, final String payload) {
    final CommunityDTO communityDTO = JsonUtils.parseJsonToObject(payload, CommunityDTO.class);
    final String communityId = communityDetailsDao.save(emailId, communityDTO);
    communityDTO.setId(communityId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(communityDTO)).build();
  }

  @PUT
  @Path("/{communityId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateCommunityDetails(final String payload) {
    final CommunityDTO communityDTO = JsonUtils.parseJsonToObject(payload, CommunityDTO.class);
    communityDetailsDao.update(communityDTO);
    return Response.ok().entity(JsonUtils.parseObjectToJson(communityDTO)).build();
  }

  @GET
  @Path("/find-community-details")
  @Produces(MediaType.APPLICATION_JSON)
  public String findCommunityDetailsByUserId(@HeaderParam("user_id")
  final String emailId) {
    CommunityDTO communityDTO = new CommunityDTO();
    if (!communityDetailsDao.isCommnityExistedForUser(emailId)) {
      return JsonUtils.parseObjectToJson(communityDTO);
    }
    communityDTO = communityDetailsDao.findCommunityDetailsByUserId(emailId);
    List<BuildingDTO> buildingList = buildingDao.findBuildingDetailsByCommunityId(communityDTO.getId());
    List<String> buildingIdList = getBuildingIds(buildingList);
    long totalFlats = flatDao.getTotalNumberOfFlatsForBuilding(buildingIdList);
    communityDTO.setBuildingList(buildingList);
    communityDTO.setTotalFlats(totalFlats);
    return JsonUtils.parseObjectToJson(communityDTO);
  }

  @GET
  @Path("/find-user-community-details")
  @Produces(MediaType.APPLICATION_JSON)
  public String findUserCommunityDetailsByUserId(@QueryParam("userId")
  final String userId) {
    List<CommunityDTO> userCommunityList = new ArrayList<CommunityDTO>();
    List<String> communityIdList = communityDetailsDao.getUserCommunityIds(userId);
    for(String communityId:communityIdList){
      CommunityDTO communityDTO = new CommunityDTO();
      communityDTO = communityDetailsDao.getCommunityDetailsByCommunityId(communityId);
      List<BuildingDTO> buildingList = buildingDao.findBuildingDetailsByCommunityId(communityId);
      communityDTO.setBuildingList(buildingList);
      List<String> buildingIdList = getBuildingIds(buildingList);
      long totalFlats = flatDao.getTotalNumberOfFlatsForBuilding(buildingIdList);
      communityDTO.setBuildingList(buildingList);
      communityDTO.setTotalFlats(totalFlats);
      userCommunityList.add(communityDTO);
    }
    return JsonUtils.parseObjectToJson(userCommunityList);
  }

  @GET
  @Path("/{communityId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findCommunityDetailsByCommunityId(@PathParam("communityId")
  final String communityId) {
    CommunityDTO communityDTO = new CommunityDTO();
    communityDTO = communityDetailsDao.getCommunityDetailsByCommunityId(communityId);
    List<BuildingDTO> buildingList = buildingDao.findBuildingDetailsByCommunityId(communityId);
    communityDTO.setBuildingList(buildingList);
    List<String> buildingIdList = getBuildingIds(buildingList);
    long totalFlats = flatDao.getTotalNumberOfFlatsForBuilding(buildingIdList);
    communityDTO.setBuildingList(buildingList);
    communityDTO.setTotalFlats(totalFlats);
    return JsonUtils.parseObjectToJson(communityDTO);
  }

  @GET
  @Path("/search")
  @Produces(MediaType.APPLICATION_JSON)
  public String findCommunitiesByNameAndCity(@QueryParam("communityName")
  final String communityName, @QueryParam("city")
  final String city, @HeaderParam("user_id")
  final String emailId) {

    List<CommunityDTO> communitiesList = new ArrayList<CommunityDTO>();
    if (StringUtils.isNotBlank(city)) {
      communitiesList = communityDetailsDao.findCommunitiesByCity(emailId, communityName, city);
      return JsonUtils.parseObjectToJson(communitiesList);
    }
    communitiesList = communityDetailsDao.findCommunitiesByName(emailId, communityName);
    return JsonUtils.parseObjectToJson(communitiesList);
  }

  private List<String> getBuildingIds(final List<BuildingDTO> buildingList) {
    LOG.info("buiding list size:::" + buildingList.size());
    List<String> buildingIdList = new ArrayList<String>();
    for (BuildingDTO building : buildingList) {
      LOG.info("buidingId:::" + building.getId());
      buildingIdList.add(building.getId());
    }
    return buildingIdList;
  }

  public void setCommunityDetailsDao(final CommunityDetailsDao communityDetailsDao) {
    this.communityDetailsDao = communityDetailsDao;
  }

  public void setBuildingDao(final BuildingDao buildingDao) {
    this.buildingDao = buildingDao;
  }

  public void setFlatDao(final FlatDao flatDao) {
    this.flatDao = flatDao;
  }

}
