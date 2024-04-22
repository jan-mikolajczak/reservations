package com.jmiko.reservations.dto;

import lombok.Data;

@Data
public class RegisterVendorDTO {

    private String companyName;
    private String nip;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;

}
