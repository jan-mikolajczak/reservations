package com.jmiko.reservations.repository;

import com.jmiko.reservations.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


}
