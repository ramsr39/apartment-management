package com.apartment.management.dto;

public final class ContactDTO {
  private String id;

  private String emailId;

  private String phoneNumber;

  private String description;

  private String type;

  private String isVisableToPublic;

  private String webSite;

  private String name;

  private Address address = new Address();

  private String utilityId;

  private String communityId;

  private String buildingId;

  private String flatId;

  public String userId;

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

  public String getIsVisableToPublic() {
    return isVisableToPublic;
  }

  public void setIsVisableToPublic(final String isVisableToPublic) {
    this.isVisableToPublic = isVisableToPublic;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
