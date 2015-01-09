package com.apartment.management.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.apartment.management.dao.BuildingDao;
import com.apartment.management.dao.CommunityDetailsDao;
import com.apartment.management.dao.FlatDao;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.FlatDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/community/{communityId}/building")
public class BuildingDetailsService {

  private BuildingDao buildingDao;

  private FlatDao flatDao;

  private CommunityDetailsDao communityDetailsDao;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response save(@PathParam("communityId")
  final String communityId, final String buildingDetailsPayload) {
    assertId(communityId, "building id should not null or empty");
    BuildingDTO buildingDTO = JsonUtils.parseJsonToObject(buildingDetailsPayload, BuildingDTO.class);
    if (null == buildingDTO) {
      throw new RuntimeException("BuildingDTO should not be null");
    }
    buildingDTO.setCommunityId(communityId);
    String buildingId = buildingDao.save(buildingDTO);
    buildingDTO.setId(buildingId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(buildingDTO)).build();
  }

  @PUT
  @Path("/{buildingId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("buildingId")
  final String buildingId, final String flatDetilsPayload) {
    assertId(buildingId, "flatId should not null");
    BuildingDTO buildingDTO = JsonUtils.parseJsonToObject(flatDetilsPayload, BuildingDTO.class);
    if (null == buildingDTO) {
      throw new RuntimeException("FlatDTO should not be null");
    }
    buildingDTO.setId(buildingId);
    buildingDao.update(buildingDTO);
    return Response.ok().entity(JsonUtils.parseObjectToJson(buildingDTO)).build();
  }

  @DELETE
  @Path("/{buildingId}")
  public Response delete(@PathParam("buildingId")
  final String buildingId) {
    assertId(buildingId, "flatId should not null or empty");
    buildingDao.delete(buildingId);
    return Response.ok().build();
  }

  @GET
  @Path("/find-building-details")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findBuildingDetails(@QueryParam("buildingId")
  final String buildingId) {
    BuildingDTO buildingDTO = buildingDao.getBuildingDetailsByBuildingId(buildingId);
    final String communityName = communityDetailsDao.getCommunityName(buildingDTO.getCommunityId());
    buildingDTO.setCommunityName(communityName);
    final List<FlatDTO> flatsList = flatDao.findFlatDetailsByBuildingId(buildingId);
    buildingDTO.setFlatList(flatsList);
    final String responseEntity = JsonUtils.parseObjectToJson(buildingDTO);
    return Response.ok().entity(responseEntity).build();
  }

  public void setCommunityDetailsDao(final CommunityDetailsDao communityDetailsDao) {
    this.communityDetailsDao = communityDetailsDao;
  }

  private void assertId(final String id, final String message) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException(message);
    }
  }

  public void setBuildingDao(final BuildingDao buildingDao) {
    this.buildingDao = buildingDao;
  }

  public void setFlatDao(final FlatDao flatDao) {
    this.flatDao = flatDao;
  }

}
