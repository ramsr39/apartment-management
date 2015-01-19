package com.apartment.management.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apartment.management.dto.AppointmentDTO;

public interface AppointmentDao {

  @Transactional(propagation = Propagation.REQUIRED)
  public String save(AppointmentDTO appointmentDTO);

  @Transactional(propagation = Propagation.REQUIRED)
  public void delete(String appointmentId);

  @Transactional(propagation = Propagation.REQUIRED)
  public void update(AppointmentDTO appointmentDTO);

  public List<AppointmentDTO> getCommunityAppointments(String communityId);

  public List<AppointmentDTO> getBuildingAppointments(String buildingId);

  public List<AppointmentDTO> getFlatAppointments(String flatId);

  public List<AppointmentDTO> getUserAppointments(String userId);

  public List<AppointmentDTO> findPendingAppointments(String userId, String communityId);

}
