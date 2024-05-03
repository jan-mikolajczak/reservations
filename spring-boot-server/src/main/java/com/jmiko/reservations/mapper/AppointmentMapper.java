package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.AppointmentDTO;
import com.jmiko.reservations.model.Appointment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AppointmentMapper {

    public Appointment fromAppointmentDTO(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointment);
        return appointment;
    }
}
