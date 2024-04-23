package com.jmiko.reservations.service;

import com.jmiko.reservations.dto.RegisterVendorDTO;
import com.jmiko.reservations.dto.VendorDTO;

public interface VendorService {

    void createVendor(VendorDTO vendorDTO);

    void createVendor(RegisterVendorDTO registerVendorDTO);

    boolean isVendorExists(Long id);
}
