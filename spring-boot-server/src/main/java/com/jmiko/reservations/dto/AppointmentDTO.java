package com.jmiko.reservations.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private Long userId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;
}
