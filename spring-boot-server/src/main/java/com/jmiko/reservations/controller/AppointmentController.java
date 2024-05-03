package com.jmiko.reservations.controller;

import com.jmiko.reservations.dto.AppointmentDTO;
import com.jmiko.reservations.model.Appointment;
import com.jmiko.reservations.repository.AppointmentRepository;
import com.jmiko.reservations.repository.UserRepository;
import com.jmiko.reservations.service.AppointmentService;
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
    private final AppointmentService appointmentService;
    private final Logger log = LoggerFactory.getLogger(getClass());


    public AppointmentController(AppointmentRepository appointmentRepository, AppointmentService appointmentService, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
    }


    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.addAppointment(appointmentDTO);
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
