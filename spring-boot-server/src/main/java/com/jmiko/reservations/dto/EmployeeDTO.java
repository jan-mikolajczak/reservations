package com.jmiko.reservations.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long userId;
    private String name;
    private String companyName;
    private String email;
    private String phoneNumber;
}
