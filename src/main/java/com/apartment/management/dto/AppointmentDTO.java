package com.apartment.management.dto;

import org.joda.time.DateTime;

public class AppointmentDTO {

  private String id;

  private String status;

  private DateTime appointmentDate;

  private DateTime remindMe;

  private String approvedStatus;

  private String approvedBy;

  private String description;

  private String flatId;

  private String buildingId;

  private String communityId;

  private String userId;

  private String isVisibleToOwner;

  private String isVisibleToTenant;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getApprovedStatus() {
    return approvedStatus;
  }

  public void setApprovedStatus(final String approvedStatus) {
    this.approvedStatus = approvedStatus;
  }

  public String getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(final String approvedBy) {
    this.approvedBy = approvedBy;
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

  public String getIsVisibleToOwner() {
    return isVisibleToOwner;
  }

  public void setIsVisibleToOwner(final String isVisibleToOwner) {
    this.isVisibleToOwner = isVisibleToOwner;
  }

  public String getIsVisibleToTenant() {
    return isVisibleToTenant;
  }

  public void setIsVisibleToTenant(final String isVisibleToTenant) {
    this.isVisibleToTenant = isVisibleToTenant;
  }

}
