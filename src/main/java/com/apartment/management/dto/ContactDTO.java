package com.apartment.management.dto;

public final class ContactDTO {

  private String id;

  private String emailId;

  private String phoneNumber;

  private String description;

  private String type;

  private String isVisibleToPublic;

  private String webSite;

  private String name;

  private String approvedStatus;

  private Address address = new Address();

  private String utilityId;

  private String communityId;

  private String buildingId;

  private String flatId;

  private String userId;

  private String approvedBy;

  private String createdBy;

  private String updatedBy;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(final String emailId) {
    this.emailId = emailId;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getIsVisibleToPublic() {
    return isVisibleToPublic;
  }

  public void setIsVisibleToPublic(final String isVisibleToPublic) {
    this.isVisibleToPublic = isVisibleToPublic;
  }

  public String getWebSite() {
    return webSite;
  }

  public void setWebSite(final String webSite) {
    this.webSite = webSite;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getApprovedStatus() {
    return approvedStatus;
  }

  public void setApprovedStatus(final String approvedStatus) {
    this.approvedStatus = approvedStatus;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public String getUtilityId() {
    return utilityId;
  }

  public void setUtilityId(final String utilityId) {
    this.utilityId = utilityId;
  }

  public String getCommunityId() {
    return communityId;
  }

  public void setCommunityId(final String communityId) {
    this.communityId = communityId;
  }

  public String getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(final String buildingId) {
    this.buildingId = buildingId;
  }

  public String getFlatId() {
    return flatId;
  }

  public void setFlatId(final String flatId) {
    this.flatId = flatId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(final String approvedBy) {
    this.approvedBy = approvedBy;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ContactDTO other = (ContactDTO) obj;
    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
      return false;
    return true;
  }
  /*
   * public static void main(String[] args) { ContactDTO contactDTO1 = new ContactDTO();
   * contactDTO1.setPhoneNumber("2345"); ContactDTO contactDTO2 = new ContactDTO(); contactDTO2.setPhoneNumber("2345");
   * ContactDTO contactDTO3 = new ContactDTO(); contactDTO3.setPhoneNumber("23456"); Set<ContactDTO> contactDTOs = new
   * HashSet<ContactDTO>(); contactDTOs.add(contactDTO1); contactDTOs.add(contactDTO2); contactDTOs.add(contactDTO3);
   * System.out.println(contactDTOs); }
   */

}
