package com.apartment.management.dto;

public final class UtilityDTO {
  private String id;

  private String type;

  private String level;

  private String serviceNumber;

  private String serviceProviderName;

  private String remindMe;

  private String paidBy;

  private String approvedStatus;

  private String approvedBy;

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

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(final String level) {
    this.level = level;
  }

  public String getServiceNumber() {
    return serviceNumber;
  }

  public void setServiceNumber(final String serviceNumber) {
    this.serviceNumber = serviceNumber;
  }

  public String getServiceProviderName() {
    return serviceProviderName;
  }

  public void setServiceProviderName(final String serviceProviderName) {
    this.serviceProviderName = serviceProviderName;
  }

  public String getRemindMe() {
    return remindMe;
  }

  public void setRemindMe(final String remindMe) {
    this.remindMe = remindMe;
  }

  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(final String paidBy) {
    this.paidBy = paidBy;
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

  public ContactDTO getContactDTO() {
    return contactDTO;
  }

  public void setContactDTO(final ContactDTO contactDTO) {
    this.contactDTO = contactDTO;
  }

}
