package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.VendorDTO;
import com.jmiko.reservations.model.Vendor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class VendorMapper {

    public Vendor fromVendorDTO(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        BeanUtils.copyProperties(vendorDTO, vendor);
        return vendor;
    }

    public VendorDTO fromVendor(Vendor vendor) {
        VendorDTO vendorDTO = new VendorDTO();
        BeanUtils.copyProperties(vendor, vendorDTO);
        return vendorDTO;
    }
}
