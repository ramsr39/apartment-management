package com.apartment.management.dto;

import java.util.ArrayList;
import java.util.List;

public class PendingItemsDTO {

  private List<UtilityDTO> pendingUtilities = new ArrayList<UtilityDTO>();

  private List<AppointmentDTO> pendingAppointments = new ArrayList<AppointmentDTO>();

  private List<ContactDTO> pendingContacts = new ArrayList<ContactDTO>();

  public List<UtilityDTO> getPendingUtilities() {
    return pendingUtilities;
  }

  public void setPendingUtilities(final List<UtilityDTO> pendingUtilities) {
    this.pendingUtilities = pendingUtilities;
  }

  public List<AppointmentDTO> getPendingAppointments() {
    return pendingAppointments;
  }

  public void setPendingAppointments(final List<AppointmentDTO> pendingAppointments) {
    this.pendingAppointments = pendingAppointments;
  }

  public List<ContactDTO> getPendingContacts() {
    return pendingContacts;
  }

  public void setPendingContacts(final List<ContactDTO> pendingContacts) {
    this.pendingContacts = pendingContacts;
  }

}
