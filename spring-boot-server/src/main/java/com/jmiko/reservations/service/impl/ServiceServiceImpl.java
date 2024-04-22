package com.jmiko.reservations.service.impl;

import com.jmiko.reservations.dto.ServiceDTO;
import com.jmiko.reservations.mapper.ServiceMapper;
import com.jmiko.reservations.model.Service;
import com.jmiko.reservations.model.ServiceCategory;
import com.jmiko.reservations.repository.ServiceRepository;
import com.jmiko.reservations.service.ServiceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceMapper serviceMapper, ServiceRepository serviceRepository) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void createService(ServiceDTO serviceDto, Long categoryId) {
        Service service = serviceMapper.fromServiceDTO(serviceDto);
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setId(categoryId);
        service.setCategory(serviceCategory);
        Service savedService = serviceRepository.save(service);

        serviceMapper.fromService(savedService);
    }

    @Override
    public List<ServiceDTO> getServicesByVendorId(Long vendorId) {
        return serviceRepository.findAllByCategory_Vendor_VendorId(vendorId).stream().map(serviceMapper::fromService).collect(Collectors.toList());

    }
}
