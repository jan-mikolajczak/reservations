package com.jmiko.reservations.dto;

import lombok.Data;
@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String companyName;
    private String email;
    private String password;
    private String phoneNumber;
    private VendorDTO vendor;
}
