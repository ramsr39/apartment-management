package com.apartment.management.dto;

public final class UserPrivilegeDTO {

  private String id;

  private String role;

  private String startDate;

  private String endDate;

  private boolean isEnterExpenses = false;

  private boolean isApproveOthersExpenses = false;

  private boolean isApporveOwnExpenses = false;

  private boolean isEnterContacts = false;

  private boolean isApproveOthersContacts = false;

  private boolean isApproveOwnContacts = false;

  private boolean isEnterAppointments = false;

  private boolean isApproveOthersAppointments = false;

  private boolean isApproveOwnAppointments = false;

  private boolean isEnterUtilities = false;

  private boolean isApproveOthersUtilities = false;

  private boolean isApproveOwnUtilities = false;

  private boolean isEnterNotices = false;

  private boolean isApproveOthersNotices = false;

  private boolean isApproveOwnNotices = false;
  
  private boolean isEditGeneralInfo = false;

  private String communityId;

  private String createdBy;

  private String updatedBy;

  private UserDTO userDTO = new UserDTO();

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(final String role) {
    this.role = role;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(final String startDate) {
    this.startDate = startDate;
  }

  public final String getEndDate() {
    return endDate;
  }

  public void setEndDate(final String endDate) {
    this.endDate = endDate;
  }

  public boolean isEnterExpenses() {
    return isEnterExpenses;
  }

  public void setEnterExpenses(final boolean isEnterExpenses) {
    this.isEnterExpenses = isEnterExpenses;
  }

  public boolean isApproveOthersExpenses() {
    return isApproveOthersExpenses;
  }

  public void setApproveOthersExpenses(final boolean isApproveOthersExpenses) {
    this.isApproveOthersExpenses = isApproveOthersExpenses;
  }

  public boolean isApporveOwnExpenses() {
    return isApporveOwnExpenses;
  }

  public void setApporveOwnExpenses(final boolean isApporveOwnExpenses) {
    this.isApporveOwnExpenses = isApporveOwnExpenses;
  }

  public boolean isEnterContacts() {
    return isEnterContacts;
  }

  public void setEnterContacts(final boolean isEnterContacts) {
    this.isEnterContacts = isEnterContacts;
  }

  public boolean isApproveOthersContacts() {
    return isApproveOthersContacts;
  }

  public void setApproveOthersContacts(final boolean isApproveOthersContacts) {
    this.isApproveOthersContacts = isApproveOthersContacts;
  }

  public boolean isApproveOwnContacts() {
    return isApproveOwnContacts;
  }

  public void setApproveOwnContacts(final boolean isApproveOwnContacts) {
    this.isApproveOwnContacts = isApproveOwnContacts;
  }

  public boolean isEnterAppointments() {
    return isEnterAppointments;
  }

  public void setEnterAppointments(final boolean isEnterAppointments) {
    this.isEnterAppointments = isEnterAppointments;
  }

  public boolean isApproveOthersAppointments() {
    return isApproveOthersAppointments;
  }

  public void setApproveOthersAppointments(final boolean isApproveOthersAppointments) {
    this.isApproveOthersAppointments = isApproveOthersAppointments;
  }

  public boolean isApproveOwnAppointments() {
    return isApproveOwnAppointments;
  }

  public void setApproveOwnAppointments(final boolean isApproveOwnAppointments) {
    this.isApproveOwnAppointments = isApproveOwnAppointments;
  }

  public boolean isEnterUtilities() {
    return isEnterUtilities;
  }

  public void setEnterUtilities(boolean isEnterUtilities) {
    this.isEnterUtilities = isEnterUtilities;
  }

  public boolean isApproveOthersUtilities() {
    return isApproveOthersUtilities;
  }

  public void setApproveOthersUtilities(boolean isApproveOthersUtilities) {
    this.isApproveOthersUtilities = isApproveOthersUtilities;
  }

  public boolean isApproveOwnUtilities() {
    return isApproveOwnUtilities;
  }

  public void setApproveOwnUtilities(boolean isApproveOwnUtilities) {
    this.isApproveOwnUtilities = isApproveOwnUtilities;
  }

  public boolean isEnterNotices() {
    return isEnterNotices;
  }

  public void setEnterNotices(boolean isEnterNotices) {
    this.isEnterNotices = isEnterNotices;
  }

  public boolean isApproveOthersNotices() {
    return isApproveOthersNotices;
  }

  public void setApproveOthersNotices(boolean isApproveOthersNotices) {
    this.isApproveOthersNotices = isApproveOthersNotices;
  }

  public boolean isApproveOwnNotices() {
    return isApproveOwnNotices;
  }

  public void setApproveOwnNotices(boolean isApproveOwnNotices) {
    this.isApproveOwnNotices = isApproveOwnNotices;
  }

  public String getCommunityId() {
    return communityId;
  }

  public void setCommunityId(final String communityId) {
    this.communityId = communityId;
  }

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(final String updatedBy) {
    this.updatedBy = updatedBy;
  }

public boolean isEditGeneralInfo() {
	return isEditGeneralInfo;
}

public void setEditGeneralInfo(boolean isEditGeneralInfo) {
	this.isEditGeneralInfo = isEditGeneralInfo;
}

}
