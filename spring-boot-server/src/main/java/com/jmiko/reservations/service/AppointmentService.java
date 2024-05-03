package com.jmiko.reservations.service;

import com.jmiko.reservations.dto.AppointmentDTO;
import com.jmiko.reservations.model.Appointment;
import org.springframework.http.ResponseEntity;

public interface AppointmentService {

    ResponseEntity<Appointment> addAppointment(AppointmentDTO appointmentDTO);

}
