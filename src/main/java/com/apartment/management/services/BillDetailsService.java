package com.apartment.management.services;

import java.util.ArrayList;
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

import com.apartment.management.dao.BillDao;
import com.apartment.management.dao.UtilityDao;
import com.apartment.management.dto.BillDTO;
import com.apartment.management.dto.UtilityDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/bill")
public class BillDetailsService {

  private BillDao billDao;

  private UtilityDao utilityDao;

  @POST
  @Path("/add-bill")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addBill(final String paylaod) {
    final BillDTO billDTO = JsonUtils.parseJsonToObject(paylaod, BillDTO.class);
    final String billId = billDao.addBill(billDTO);
    billDTO.setId(billId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(billDTO)).build();
  }

  @DELETE
  @Path("/{billId}")
  public Response deleteBill(@PathParam("billId")
  final String billId) {
    billDao.deleteBill(billId);
    return Response.ok().build();
  }

  @PUT
  @Path("/update-bill")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateBill(final String paylaod) {
    final BillDTO billDTO = JsonUtils.parseJsonToObject(paylaod, BillDTO.class);
    final String billId = billDao.updateBill(billDTO);
    billDTO.setId(billId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(billDTO)).build();
  }

  @GET
  @Path("/get-utility-bill-history")
  @Produces(MediaType.APPLICATION_JSON)
  public String findUtilityBillsHistory(@QueryParam("utilityId")
  final String utilityId) {
    return JsonUtils.parseObjectToJson(billDao.findUtilityBillsHistory(utilityId));
  }

  @GET
  @Path("/find-pending-bills")
  @Produces(MediaType.APPLICATION_JSON)
  public String findPendingBills(@QueryParam("communityId")
  final String communityId, @QueryParam("buildingId")
  final String buildingId, @QueryParam("flatId")
  final String flatId, @QueryParam("userId")
  final String userId) {
    final List<BillDTO> pendingBIllList = new ArrayList<BillDTO>();
    if (StringUtils.isNotBlank(communityId)) {
      List<BillDTO> communityPendingBillList = billDao.findCommunityPendingBills(communityId);
      for (BillDTO billDTO : communityPendingBillList) {
        UtilityDTO utilityDTO = utilityDao.findUtilitiesByUtilityId(billDTO.getUtilityId());
        billDTO.setServiceProviderType(utilityDTO.getType());
        billDTO.setServiceProviderName(utilityDTO.getServiceProviderName());
        pendingBIllList.add(billDTO);
      }
    }
    if (StringUtils.isNotBlank(buildingId)) {
      List<BillDTO> buildingPendigBillList = billDao.findBuildingPendingBills(buildingId);
      for (BillDTO billDTO : buildingPendigBillList) {
        UtilityDTO utilityDTO = utilityDao.findUtilitiesByUtilityId(billDTO.getUtilityId());
        billDTO.setServiceProviderType(utilityDTO.getType());
        billDTO.setServiceProviderName(utilityDTO.getServiceProviderName());
        pendingBIllList.add(billDTO);
      }
    }
    if (StringUtils.isNotBlank(flatId)) {
      List<BillDTO> flatPendigBillList = billDao.findFlatPendingBills(flatId);
      for (BillDTO billDTO : flatPendigBillList) {
        UtilityDTO utilityDTO = utilityDao.findUtilitiesByUtilityId(billDTO.getUtilityId());
        billDTO.setServiceProviderType(utilityDTO.getType());
        billDTO.setServiceProviderName(utilityDTO.getServiceProviderName());
        pendingBIllList.add(billDTO);
      }
    }
    if (StringUtils.isNotBlank(userId)) {
      List<BillDTO> userPendigBillList = billDao.findUserPendingBills(userId);
      for (BillDTO billDTO : userPendigBillList) {
        UtilityDTO utilityDTO = utilityDao.findUtilitiesByUtilityId(billDTO.getUtilityId());
        billDTO.setServiceProviderType(utilityDTO.getType());
        billDTO.setServiceProviderName(utilityDTO.getServiceProviderName());
        pendingBIllList.add(billDTO);
      }
    }
    return JsonUtils.parseObjectToJson(pendingBIllList);
  }

  public void setBillDao(final BillDao billDao) {
    this.billDao = billDao;
  }

  public void setUtilityDao(final UtilityDao utilityDao) {
    this.utilityDao = utilityDao;
  }

}
