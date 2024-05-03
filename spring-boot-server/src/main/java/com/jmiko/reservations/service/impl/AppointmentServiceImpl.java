package com.jmiko.reservations.service.impl;

import com.jmiko.reservations.dto.AppointmentDTO;
import com.jmiko.reservations.mapper.AppointmentMapper;
import com.jmiko.reservations.model.Appointment;
import com.jmiko.reservations.model.User;
import com.jmiko.reservations.repository.AppointmentRepository;
import com.jmiko.reservations.repository.UserRepository;
import com.jmiko.reservations.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(AppointmentMapper appointmentMapper, AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<Appointment> addAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentMapper.fromAppointmentDTO(appointmentDTO);
        User user = userRepository.findById(appointmentDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + appointmentDTO.getUserId()));
        appointment.setUser(user);

        try {
            Appointment newAppointment = appointmentRepository.save(appointment);
            log.debug("Saved new appointment, start: {}, end: {}", appointmentDTO.getStart(), appointmentDTO.getEnd());
            return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
