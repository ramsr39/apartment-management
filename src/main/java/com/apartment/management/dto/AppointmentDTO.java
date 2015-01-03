package com.apartment.management.dto;

import org.joda.time.DateTime;

public class AppointmentDTO {

  private String id;

  private String status;

  private DateTime appointmentDate;

  private DateTime remindMe;

  private String flatId;

  private String buildingId;

  private String communityId;

  private String userId;

  private ContactDTO contactDTO = new ContactDTO();

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public DateTime getAppointmentDate() {
    return appointmentDate;
  }

  public void setAppointmentDate(final DateTime appointmentDate) {
    this.appointmentDate = appointmentDate;
  }

  public DateTime getRemindMe() {
    return remindMe;
  }

  public void setRemindMe(final DateTime remindMe) {
    this.remindMe = remindMe;
  }

  public ContactDTO getContactDTO() {
    return contactDTO;
  }

  public void setContactDTO(final ContactDTO contactDTO) {
    this.contactDTO = contactDTO;
  }

  public String getFlatId() {
    return flatId;
  }

  public void setFlatId(final String flatId) {
    this.flatId = flatId;
  }

  public String getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(final String buildingId) {
    this.buildingId = buildingId;
  }

  public String getCommunityId() {
    return communityId;
  }

  public void setCommunityId(final String communityId) {
    this.communityId = communityId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

}
