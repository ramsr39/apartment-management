package com.apartment.management.dto;

public final class CoOccupantDTO {
  private String id;

  private String firstName;

  private String lastName;

  private String emailId;

  private String phoneNumber;

  private String relation;

  private String dateOfBirth;

  private String userId;

  private String flatId;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
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

  public String getRelation() {
    return relation;
  }

  public void setRelation(final String relation) {
    this.relation = relation;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(final String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getFlatId() {
    return flatId;
  }

  public void setFlatId(final String flatId) {
    this.flatId = flatId;
  }

}
