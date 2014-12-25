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

}