package com.apartment.management.dto;

public class BillDTO {
	private String id;
	private String billNumber;
	private String billDate;
	private String ammount;
	private String dueDate;
	private String description;
	private String remindMe;
	private String serviceFrom;
	private String serviceTo;
	private String paymentDate;
	private String receiptNUmber;
	private String utilityId;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(final String billDate) {
		this.billDate = billDate;
	}

	public String getAmmount() {
		return ammount;
	}

	public void setAmmount(final String ammount) {
		this.ammount = ammount;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(final String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getRemindMe() {
		return remindMe;
	}

	public void setRemindMe(final String remindMe) {
		this.remindMe = remindMe;
	}

	public String getUtilityId() {
		return utilityId;
	}

	public void setUtilityId(final String utilityId) {
		this.utilityId = utilityId;
	}

	public String getServiceFrom() {
		return serviceFrom;
	}

	public void setServiceFrom(final String serviceFrom) {
		this.serviceFrom = serviceFrom;
	}

	public String getServiceTo() {
		return serviceTo;
	}

	public void setServiceTo(final String serviceTo) {
		this.serviceTo = serviceTo;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(final String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getReceiptNUmber() {
		return receiptNUmber;
	}

	public void setReceiptNUmber(final String receiptNUmber) {
		this.receiptNUmber = receiptNUmber;
	}

}
