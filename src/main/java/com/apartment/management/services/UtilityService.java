package com.apartment.management.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.apartment.management.dao.UtilityDao;
import com.apartment.management.dto.BillDTO;
import com.apartment.management.dto.UtilityDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/utility")
public class UtilityService {

	private UtilityDao utilityDao;

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUtility(final String paylaod) {
		UtilityDTO utilityDTO = JsonUtils.parseJsonToObject(paylaod,
				UtilityDTO.class);
		utilityDTO = utilityDao.save(utilityDTO);
		return Response.ok().entity(JsonUtils.parseObjectToJson(utilityDTO)).build();
	}

	@POST
	@Path("/add-bill")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBill(final String paylaod) {
		final BillDTO billDTO = JsonUtils.parseJsonToObject(paylaod,
				BillDTO.class);
		final String billId = utilityDao.addBill(billDTO);
		billDTO.setId(billId);
		return Response.ok().entity(JsonUtils.parseObjectToJson(billDTO)).build();
	}

	@DELETE
	@Path("/{utilityId}")
	public Response deleteUtility(@PathParam("utilityId") final String utilityId) {
		utilityDao.delete(utilityId);
		return Response.ok().build();
	}

	@GET
	@Path("/find-community-utilities")
	@Produces(MediaType.APPLICATION_JSON)
   public String findUtilitiesByCommunityId(@QueryParam("communityId") final String communityId){
		return JsonUtils.parseObjectToJson(utilityDao.findUtilitiesByCommunityId(communityId));
	}

	@GET
	@Path("/find-building-utilities")
	@Produces(MediaType.APPLICATION_JSON)
   public String findUtilitiesByBuildingId(@QueryParam("buildingId") final String buildingId){
		return JsonUtils.parseObjectToJson(utilityDao.findUtilitiesByBuildingId(buildingId));
	}

	@GET
	@Path("/find-flat-utilities")
	@Produces(MediaType.APPLICATION_JSON)
   public String findUtilitiesByFlatId(@QueryParam("flatId") final String flatId){
		return JsonUtils.parseObjectToJson(utilityDao.findUtilitiesByFlatId(flatId));
	}

	@GET
	@Path("/{userId}/find-user-utilities")
	@Produces(MediaType.APPLICATION_JSON)
   public String findUtilitiesByUserId(@QueryParam("userId") final String userId){
		return JsonUtils.parseObjectToJson(utilityDao.findUtilitiesByUserId(userId));
	}

	@GET
	@Path("/find-utilities")
	@Produces(MediaType.APPLICATION_JSON)
    public String findUtilitiesByUtilityId(@QueryParam("utilityId") final String utilityId){
		return JsonUtils.parseObjectToJson(utilityDao.findUtilitiesByUtilityId(utilityId));
	}
	
	@GET
	@Path("/{utilityId}/get-utility-bill-history")
	@Produces(MediaType.APPLICATION_JSON)
    public String getUtilityBillsHistory(@PathParam("utilityId") final String utilityId){
		return JsonUtils.parseObjectToJson(utilityDao.getUtilityBillsHistory(utilityId));
	}

	public void setUtilityDao(final UtilityDao utilityDao) {
		this.utilityDao = utilityDao;
	}

}
