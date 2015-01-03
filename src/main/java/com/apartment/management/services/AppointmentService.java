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

import com.apartment.management.dao.AppointmentDao;
import com.apartment.management.dao.ContactDao;
import com.apartment.management.dto.AppointmentDTO;
import com.apartment.management.dto.ContactDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/appointment")
public class AppointmentService {
  private AppointmentDao appointmentDao;

  private ContactDao contactDao;

  @POST
  @Path("/save-appointment")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveAppointment(final String paylaod) {
    final AppointmentDTO appointmentDTO = JsonUtils.parseJsonToObject(paylaod, AppointmentDTO.class);
    final String appointmentId = appointmentDao.save(appointmentDTO);
    appointmentDTO.setId(appointmentId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(appointmentDTO)).build();
  }

  @DELETE
  @Path("/{appointmentId}")
  public Response deleteAppointment(@PathParam("appointmentId")
  final String appointmentId) {
    appointmentDao.delete(appointmentId);
    return Response.ok().build();
  }

  @PUT
  @Path("/update-appointment")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateBill(final String paylaod) {
    final AppointmentDTO appointmentDTO = JsonUtils.parseJsonToObject(paylaod, AppointmentDTO.class);
    appointmentDao.update(appointmentDTO);
    return Response.ok().entity(JsonUtils.parseObjectToJson(appointmentDTO)).build();
  }

  @GET
  @Path("/get-appointments")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getAppointments(@QueryParam("communityId")
  final String communityId, @QueryParam("buildingId")
  final String buildingId, @QueryParam("flatId")
  final String flatId, @QueryParam("userId")
  final String userId) {
    List<AppointmentDTO> appointmentsList = new ArrayList<AppointmentDTO>();
    if (StringUtils.isNotBlank(communityId)) {
      List<AppointmentDTO> appointments =
        prepareAppointmentContactDetails(appointmentDao.getCommunityAppointments(communityId));
      appointmentsList.addAll(appointments);
    }
    if (StringUtils.isNotBlank(buildingId)) {
      List<AppointmentDTO> appointments =
        prepareAppointmentContactDetails(appointmentDao.getBuildingAppointments(buildingId));
      appointmentsList.addAll(appointments);
    }
    if (StringUtils.isNotBlank(flatId)) {
      List<AppointmentDTO> appointments = prepareAppointmentContactDetails(appointmentDao.getFlatAppointments(flatId));
      appointmentsList.addAll(appointments);
    }
    if (StringUtils.isNotBlank(userId)) {
      List<AppointmentDTO> appointments = prepareAppointmentContactDetails(appointmentDao.getUserAppointments(userId));
      appointmentsList.addAll(appointments);
    }
    return JsonUtils.parseObjectToJson(appointmentsList);
  }

  private List<AppointmentDTO> prepareAppointmentContactDetails(List<AppointmentDTO> appointmentList) {
    List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
    for (AppointmentDTO appointmentDto : appointmentList) {
      ContactDTO contactDTO = contactDao.getContactsByContactId(appointmentDto.getContactDTO().getId());
      appointmentDto.setContactDTO(contactDTO);
    }
    return appointments;
  }
}
