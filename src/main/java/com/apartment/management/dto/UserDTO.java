package com.apartment.management.dto;

public class UserDTO {
	private String userId;
	private String emailId;
	private String role;
	private String firstName;
	private String lastName;
	private String primaryPhoneNumber;
	private String secondaryPhoneNumber;
	private String secondaryEmail;
	private String uid;
	private String uidType;
	private String bloodGroup;
	private String dateOfBirth;
	private EmergencyContactInfo emergencyContactInfo;
	private Address address;
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
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

	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}

	public void setPrimaryPhoneNumber(final String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}

	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}

	public void setSecondaryPhoneNumber(final String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(final String uid) {
		this.uid = uid;
	}

	public String getUidType() {
		return uidType;
	}

	public void setUidType(final String uidType) {
		this.uidType = uidType;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(final String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public EmergencyContactInfo getEmergencyContactInfo() {
		if (null == emergencyContactInfo) {
			return new EmergencyContactInfo();
		}
		return emergencyContactInfo;
	}

	public void setEmergencyContactInfo(
			final EmergencyContactInfo emergencyContactInfo) {
		this.emergencyContactInfo = emergencyContactInfo;
	}

	public Address getAddress() {
		if (null == address) {
			return new Address();
		}
		return address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
