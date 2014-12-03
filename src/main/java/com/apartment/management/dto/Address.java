package com.apartment.management.dto;

public class Address {
	private String address1;
	private String address2;
	private String address3;
	private String country;
	private String city;
	private String state;
	private int postalCode;

	public String getAddress1() {
		return address1;
	}
	public void setAddress1(final String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(final String address3) {
		this.address3 = address3;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(final int postalCode) {
		this.postalCode = postalCode;
	}
	
	
}
