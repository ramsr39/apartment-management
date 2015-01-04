package com.apartment.management.services;

import java.util.HashSet;
import java.util.Set;

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

import org.apache.commons.lang3.StringUtils;

import com.apartment.management.dao.ContactDao;
import com.apartment.management.dto.ContactDTO;
import com.apartment.management.utils.JsonUtils;

@Path("/contacts")
public class ContactDetailsService {

  private ContactDao contactDao;

  @POST
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveContactDetails(final String paylaod) {
    final ContactDTO contactDTO = JsonUtils.parseJsonToObject(paylaod, ContactDTO.class);
    if (null == contactDTO) {
      throw new RuntimeException("contacts dto is null");
    }
    final String contactId = contactDao.save(contactDTO);
    contactDTO.setId(contactId);
    return Response.ok().entity(JsonUtils.parseObjectToJson(contactDTO)).build();
  }

  @PUT
  @Path("/update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateContactDetails(final String paylaod) {
    final ContactDTO contactDTO = JsonUtils.parseJsonToObject(paylaod, ContactDTO.class);
    if (null == contactDTO) {
      throw new RuntimeException("contactdto is null");
    }
    contactDao.update(contactDTO);
    return Response.ok().entity(JsonUtils.parseObjectToJson(contactDTO)).build();
  }

  @DELETE
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delteContact(@QueryParam("contactId")
  final String contactId) {
    contactDao.delete(contactId);
    return Response.ok().build();
  }

  @GET
  @Path("/get-contact-details")
  @Produces(MediaType.APPLICATION_JSON)
  public String getContactDetails(@QueryParam("communityId")
  final String communityId, @QueryParam("buildingId")
  final String buildingId, @QueryParam("flatId")
  final String flatId, @QueryParam("userId")
  final String userId) {
    final Set<ContactDTO> contactSet = new HashSet<ContactDTO>();
    if (StringUtils.isNotBlank(communityId)) {
      contactSet.addAll(contactDao.findContactsByCommunityId(communityId));
    }
    if (StringUtils.isNotBlank(buildingId)) {
      contactSet.addAll(contactDao.findContactsByBuildingId(buildingId));
    }
    if (StringUtils.isNotBlank(flatId)) {
      contactSet.addAll(contactDao.findContactsByFlatId(flatId));
    }
    if (StringUtils.isNotBlank(userId)) {
      contactSet.addAll(contactDao.findContactsByUserId(userId));
    }
    return JsonUtils.parseObjectToJson(contactSet);
  }

  @GET
  @Path("/get-public-contact-details")
  @Produces(MediaType.APPLICATION_JSON)
  public String getPublicContactDetails() {
    final Set<ContactDTO> contactSet = new HashSet<ContactDTO>();
    contactSet.addAll(contactDao.getPublicContactDetails());
    return JsonUtils.parseObjectToJson(contactSet);
  }

  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

}
