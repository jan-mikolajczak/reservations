package com.jmiko.reservations.controller;

import com.jmiko.reservations.model.Appointment;
import com.jmiko.reservations.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());


    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment newAppointment = appointmentRepository.save(appointment);
            log.debug("Saved new appointment, start: {}, end: {}", appointment.getStart(), appointment.getEnd());
            return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() {
        try {
            List<Appointment> appointments = appointmentRepository.findAll();
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
