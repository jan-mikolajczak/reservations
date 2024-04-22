package com.jmiko.reservations.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private Long vendorId;
    private String name;
    private String nip;

    public VendorDTO() {
    }

    public VendorDTO(Long vendorId, String name, String nip) {
        this.vendorId = vendorId;
        this.name = name;
        this.nip = nip;
    }
}
