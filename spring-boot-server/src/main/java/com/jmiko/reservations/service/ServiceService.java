package com.jmiko.reservations.service;

import com.jmiko.reservations.dto.ServiceDTO;

import java.util.List;

public interface ServiceService {

    void createService(ServiceDTO serviceDto, Long categoryId);

    List<ServiceDTO> getServicesByVendorId(Long vendorId);

}
