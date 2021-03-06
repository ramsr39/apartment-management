package com.apartment.management.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apartment.management.dao.AppointmentDao;
import com.apartment.management.dao.ContactDao;
import com.apartment.management.dao.ManageUserDao;
import com.apartment.management.dao.UtilityDao;
import com.apartment.management.dto.AppointmentDTO;
import com.apartment.management.dto.CoOccupantDTO;
import com.apartment.management.dto.ContactDTO;
import com.apartment.management.dto.PendingItemsDTO;
import com.apartment.management.dto.UserDTO;
import com.apartment.management.dto.UserPrivilegeDTO;
import com.apartment.management.dto.UtilityDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/users")
public class ManageUserService {

  private static final Logger LOG = LoggerFactory.getLogger(ManageUserService.class);

  private ContactDao contactDao;

  private ManageUserDao manageUserDao;

  private UtilityDao utilityDao;

  private AppointmentDao appointmentDao;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addUser(final String payload) {
    LOG.info("addeUser start----");
    UserDTO userDTO = JsonUtils.parseJsonToObject(payload, UserDTO.class);
    if (null == userDTO) {
      throw new RuntimeException("unable to parse user information");
    }
    final String userId = manageUserDao.save(userDTO);
    userDTO.setUserId(userId);
    LOG.info("addeUser end----" + userId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(userDTO)).build();
  }

  @POST
  @Path("/{userId}/add-co-occupant")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addCoOccupant(@PathParam("userId")
  final String userId, final String payload) {
    LOG.info("addCoOccupant start----" + userId);
    CoOccupantDTO coOccupantDTO = JsonUtils.parseJsonToObject(payload, CoOccupantDTO.class);
    if (null == coOccupantDTO) {
      throw new RuntimeException("unable to parse user information");
    }
    coOccupantDTO.setUserId(userId);
    final String coOccupentId = manageUserDao.saveCoOccupent(coOccupantDTO);
    coOccupantDTO.setId(coOccupentId);
    LOG.info("addCoOccupant end----" + userId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(coOccupantDTO)).build();
  }

  @PUT
  @Path("/{userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUser(final String payload) {
    LOG.info("updateUser start----");
    UserDTO userDTO = JsonUtils.parseJsonToObject(payload, UserDTO.class);
    if (null == userDTO) {
      throw new RuntimeException("unable to parse user information");
    }
    manageUserDao.update(userDTO);
    LOG.info("updateUser start----");
    return Response.ok().entity(JsonUtils.parseObjectToJson(userDTO)).build();
  }

  @DELETE
  public Response deleteUser(@QueryParam("userId")
  final String userId) {
    manageUserDao.delete(userId);
    return Response.ok().build();
  }

  @DELETE
  @Path("/{userId}/delete-co-occupent")
  public Response deleteCoOccupent(@QueryParam("coOccupentId")
  final String coOccupentId) {
    manageUserDao.deleteCoOccupent(coOccupentId);
    return Response.ok().build();
  }

  @GET
  @Path("/search")
  @Consumes(MediaType.APPLICATION_JSON)
  public String findUsers(@QueryParam("firstName")
  final String firstName, @QueryParam("lastName")
  final String lastName, @QueryParam("emailId")
  final String emailId, @QueryParam("phoneNumber")
  final String phoneNumber, @QueryParam("uid")
  final String uid) {
    List<UserDTO> userList = new ArrayList<UserDTO>();
    userList = manageUserDao.findUsers(firstName, lastName, emailId, phoneNumber, uid);
    return JsonUtils.parseObjectToJson(userList);
  }

  @GET
  @Path("/{userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserById(@PathParam("userId")
  final String userId) {
    LOG.info("getUserById method start for user::" + userId);
    UserDTO userDTO = manageUserDao.getUserDetailsById(userId);
    LOG.info("getUserById method end..................");
    return Response.ok().entity(JsonUtils.parseObjectToJson(userDTO)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/add-role")
  public Response addRole(final String payload, @HeaderParam("userId")
  final String userId) {
    LOG.info("addRole started..............." + userId);
    UserPrivilegeDTO userPrivilegeDTO = JsonUtils.parseJsonToObject(payload, UserPrivilegeDTO.class);
    if (null == userPrivilegeDTO) {
      throw new RuntimeException("unable to parse user information");
    }
    userPrivilegeDTO.setCreatedBy(userId);
    final String id = manageUserDao.addRole(userPrivilegeDTO);
    userPrivilegeDTO.setId(id);
    LOG.info("addRole end..............." + userId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(userPrivilegeDTO)).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/update-role")
  public Response updateRole(final String payload, @HeaderParam("userId")
  final String userId) {
    LOG.info("updateRole started...............");
    UserPrivilegeDTO userPrivilegeDTO = JsonUtils.parseJsonToObject(payload, UserPrivilegeDTO.class);
    if (null == userPrivilegeDTO) {
      throw new RuntimeException("unable to parse user information");
    }
    userPrivilegeDTO.setUpdatedBy(userId);
    final String managementGroupId = manageUserDao.updateRole(userPrivilegeDTO);
    LOG.info("successfully updateRole for the id..............." + managementGroupId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(userPrivilegeDTO)).build();
  }

  @PUT
  @Path("/dalete-role/{managementGroupId}")
  public Response deleteRole(final String payload, @HeaderParam("managementGroupId")
  final String managementGroupId) {
    LOG.info("deleteRole started for id........" + managementGroupId);
    manageUserDao.deleteRole(managementGroupId);
    LOG.info("successfully deleted role for the id..............." + managementGroupId);
    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/get-community-roles")
  public String getCommiunityRoles(@QueryParam("communityId")
  final String communityId) {
    LOG.info("getCommiunityRoles started for id........" + communityId);
    List<UserPrivilegeDTO> rolesList = manageUserDao.getCommunityRoles(communityId);
    LOG.info("getCommiunityRoles end.for the id............." + communityId);
    return JsonUtils.parseObjectToJson(rolesList);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/get-user-roles")
  public String getUserRoles(@QueryParam("userId")
  final String userId) {
    LOG.info("getUserRoles started for id........" + userId);
    List<UserPrivilegeDTO> rolesList = manageUserDao.getUserRoles(userId);
    LOG.info("getUserRoles end.for the id............." + userId);
    return JsonUtils.parseObjectToJson(rolesList);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/check-user-residency-in-community")
  public String isUserResidentInCommunity(@QueryParam("communityId")
  final String communityId, @QueryParam("userId")
  final String userId) {
    LOG.info("isUserResidentInCommunity started for id........" + communityId);
    boolean flag = manageUserDao.isUserResidentInCommunity(communityId, userId);
    LOG.info("isUserResidentInCommunity end.for the id............." + communityId);
    return String.valueOf(flag);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/get-user-pending-items")
  public String getUserPendingItems(@QueryParam("userId")
  final String userId) {
    LOG.info("getUserPendingItems started for id........" + userId);
    List<UserPrivilegeDTO> userPrivilegList = manageUserDao.getUserRoles(userId);
    PendingItemsDTO pendingItemsDTO = new PendingItemsDTO();
    for (UserPrivilegeDTO userPrivilegeDTO : userPrivilegList) {
      if (userPrivilegeDTO.isApproveOthersUtilities()) {
        final List<UtilityDTO> pendingUtilities =
          utilityDao.findPendingUtilities(userId, userPrivilegeDTO.getCommunityId());
        pendingItemsDTO.getPendingUtilities().addAll(pendingUtilities);
      }
      if (userPrivilegeDTO.isApproveOthersContacts()) {
        final List<ContactDTO> pendingContacts =
          contactDao.findPendingContacts(userId, userPrivilegeDTO.getCommunityId());
        pendingItemsDTO.getPendingContacts().addAll(pendingContacts);
      }
      if (userPrivilegeDTO.isApproveOthersAppointments()) {
        final List<AppointmentDTO> pendingAppointments =
          appointmentDao.findPendingAppointments(userId, userPrivilegeDTO.getCommunityId());
        pendingItemsDTO.getPendingAppointments().addAll(prepareAppointmentContactDetails(pendingAppointments));
      }
      if (userPrivilegeDTO.isApproveOwnNotices()) {

      }
      if (userPrivilegeDTO.isApproveOthersExpenses()) {

      }
    }
    LOG.info("getUserPendingItems end.for the id............." + userId);
    return JsonUtils.parseObjectToJson(pendingItemsDTO);
  }

  private List<AppointmentDTO> prepareAppointmentContactDetails(final List<AppointmentDTO> appointmentList) {
    LOG.info("prepareAppointmentContactDetails start...............");
    List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
    for (AppointmentDTO appointmentDto : appointmentList) {
      ContactDTO contactDTO = contactDao.getContactsByContactId(appointmentDto.getContactDTO().getId());
      appointmentDto.setContactDTO(contactDTO);
      appointments.add(appointmentDto);
    }
    LOG.info("prepareAppointmentContactDetails end..............."+appointments.size());
    return appointments;
  }

  public void setManageUserDao(final ManageUserDao manageUserDao) {
    this.manageUserDao = manageUserDao;
  }

  public void setContactDao(final ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  public void setUtilityDao(final UtilityDao utilityDao) {
    this.utilityDao = utilityDao;
  }

  public void setAppointmentDao(final AppointmentDao appointmentDao) {
    this.appointmentDao = appointmentDao;
  }

}
