package com.apartment.management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.apartment.management.dao.FlatDao;
import com.apartment.management.dto.BuildingDTO;
import com.apartment.management.dto.CoOccupantDTO;
import com.apartment.management.dto.FlatDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/")
public class FlatDetailsService {

  private FlatDao flatDao;

  private BuildingDao buildingDao;

  @POST
  @Path("/building/{buildingId}/flat")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response save(@PathParam("buildingId")
  final String buildingId, final String flatDetilsPayload) {
    assetId(buildingId, "building id should not null or empty");
    FlatDTO flatDTO = JsonUtils.parseJsonToObject(flatDetilsPayload, FlatDTO.class);
    if (null == flatDTO) {
      throw new RuntimeException("FlatDTO should not be null");
    }
    flatDTO.setBuildingId(buildingId);
    String flatId = flatDao.save(flatDTO);
    flatDTO.setId(flatId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(flatDTO)).build();
  }

  @PUT
  @Path("/building/{buildingId}/flat/{flatId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("flatId")
  final String flatId, final String flatDetilsPayload) {
    assetId(flatId, "flatId should not null");
    FlatDTO flatDTO = JsonUtils.parseJsonToObject(flatDetilsPayload, FlatDTO.class);
    if (null == flatDTO) {
      throw new RuntimeException("FlatDTO should not be null");
    }
    flatDTO.setId(flatId);
    flatDao.update(flatDTO);
    return Response.ok().entity(JsonUtils.parseObjectToJson(flatDTO)).build();
  }

  @DELETE
  @Path("/building/{buildingId}/flat/{flatId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("flatId")
  final String flatId) {
    assetId(flatId, "flatId should not null or empty");
    flatDao.delete(flatId);
    return Response.ok().build();
  }

  @GET
  @Path("/flat/{flatId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getFlatDetailsByFlatId(@PathParam("flatId")
  final String flatId) {
    final FlatDTO flatDTO = flatDao.getFlatDetailsByFaltId(flatId);
    final BuildingDTO buildingDTO = buildingDao.getBuildingDetailsByBuildingId(flatDTO.getBuildingId());
    final Map<String, String> communityMap = buildingDao.getBuildingCommunityById(flatDTO.getBuildingId());
    buildingDTO.setId(flatDTO.getBuildingId());
    buildingDTO.setCommunityName(communityMap.get("communityName"));
    flatDTO.setBuildingDTO(buildingDTO);
    return JsonUtils.parseObjectToJson(flatDTO);
  }

  @GET
  @Path("/find-co-occupants")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String findCoOccupants(@QueryParam("flatId")
  final String flatId, @QueryParam("userId")
  final String userId) {
    List<CoOccupantDTO> coOccupantList = flatDao.findCoOccupants(flatId, userId);
    return JsonUtils.parseObjectToJson(coOccupantList);
  }

  @GET
  @Path("/myresidence")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getUserResidenceDetails(@QueryParam("userId")
  final String userId) {
    final List<FlatDTO> flatList = flatDao.getFlatDetailsByUser(userId);
    final List<FlatDTO> filteredFlatList = new ArrayList<FlatDTO>();
    for (FlatDTO flatDTO : flatList) {
      if (flatDTO.getTenantDetails().getUserId() == null) {
        final String buildingName = buildingDao.getBuildingNameById(flatDTO.getBuildingId());
        final Map<String, String> communityMap = buildingDao.getBuildingCommunityById(flatDTO.getBuildingId());
        final BuildingDTO buildingDTO = prepareBuildingDTO(buildingName, communityMap);
        buildingDTO.setId(flatDTO.getBuildingId());
        flatDTO.setBuildingDTO(buildingDTO);
        filteredFlatList.add(flatDTO);
      }
      if (StringUtils.isNotBlank(flatDTO.getTenantDetails().getUserId())
          && flatDTO.getTenantDetails().getUserId().equalsIgnoreCase(userId)) {
        final String buildingName = buildingDao.getBuildingNameById(flatDTO.getBuildingId());
        final Map<String, String> communityMap = buildingDao.getBuildingCommunityById(flatDTO.getBuildingId());
        final BuildingDTO buildingDTO = prepareBuildingDTO(buildingName, communityMap);
        buildingDTO.setId(flatDTO.getBuildingId());
        flatDTO.setBuildingDTO(buildingDTO);
        filteredFlatList.add(flatDTO);
      }
    }
    return JsonUtils.parseObjectToJson(filteredFlatList);
  }
/*public static void main(String[] args) {
  String payload="[{\"id\":\"F91663219\",\"buildingId\":\"B21386320\",\"unitNumber\":\"1\",\"floorNumber\":\"1\",\"unitSize\":0,\"totalBedRooms\":0,\"totalBathRooms\":0,\"totalRooms\":0,\"totalTwoWheelerParkings\":0,\"totalFourWheelerParkings\":0,\"ownerDetails\":{\"emailId\":\"rajur@gmail.com\"},\"tenantDetails\":{},\"buildingDTO\":{\"id\":\"B21386320\",\"communityId\":\"C74468967\",\"name\":\"my building\",\"totalUnits\":0,\"totalFloors\":0,\"communityName\":\"test community1\",\"postalCode\":0,\"ownerDetails\":{},\"flatList\":[]}}]";
  Type listType = new TypeToken<List<FlatDTO>>() {}.getType();
  List<FlatDTO> flatDTOs = new Gson().fromJson(payload, listType);
 // List<FlatDTO> flatDTOs = JsonUtils.parseJsonToObject(payload, List.class);
  new FlatDetailsService().getUserResidenceDetails("ramur@gmail.com",flatDTOs);
}*/
  @GET
  @Path("/myleasedUnits")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getLeasedUnitsDetails(@QueryParam("userId")
  final String userId) {
    final List<FlatDTO> flatList = flatDao.getFlatDetailsByUser(userId);
    final List<FlatDTO> filterFlatList = new ArrayList<FlatDTO>();
    for (FlatDTO flatDTO : flatList) {
      if (null != flatDTO.getOwnerDetails().getUserId()
          && flatDTO.getOwnerDetails().getUserId().equalsIgnoreCase(userId)
          && flatDTO.getTenantDetails().getUserId() != null) {
        final String buildingName = buildingDao.getBuildingNameById(flatDTO.getBuildingId());
        final Map<String, String> communityMap = buildingDao.getBuildingCommunityById(flatDTO.getBuildingId());
        final BuildingDTO buildingDTO = prepareBuildingDTO(buildingName, communityMap);
        buildingDTO.setId(flatDTO.getBuildingId());
        flatDTO.setBuildingDTO(buildingDTO);
        filterFlatList.add(flatDTO);
      }
    }
    return JsonUtils.parseObjectToJson(filterFlatList);
  }

  private BuildingDTO prepareBuildingDTO(final String buildingName, final Map<String, String> communityMap) {
    BuildingDTO buildingDTO = new BuildingDTO();
    buildingDTO.setCommunityId(communityMap.get("communityId"));
    buildingDTO.setCommunityName(communityMap.get("communityName"));
    buildingDTO.setName(buildingName);
    return buildingDTO;
  }
  private void assetId(final String id, final String message) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException(message);
    }
  }

  public void setFlatDao(final FlatDao flatDao) {
    this.flatDao = flatDao;
  }

  public void setBuildingDao(BuildingDao buildingDao) {
    this.buildingDao = buildingDao;
  }

}
