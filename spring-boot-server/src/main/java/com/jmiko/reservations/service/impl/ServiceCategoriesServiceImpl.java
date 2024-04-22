package com.jmiko.reservations.service.impl;

import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.mapper.ServiceCategoryMapper;
import com.jmiko.reservations.model.ServiceCategory;
import com.jmiko.reservations.model.Vendor;
import com.jmiko.reservations.repository.ServiceCategoryRepository;
import com.jmiko.reservations.repository.VendorRepository;
import com.jmiko.reservations.service.ServiceCategoriesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceCategoriesServiceImpl implements ServiceCategoriesService {

    final
    ServiceCategoryRepository serviceCategoryRepository;

    final
    ServiceCategoryMapper serviceCategoryMapper;

    final VendorRepository vendorRepository;

    public ServiceCategoriesServiceImpl(ServiceCategoryRepository serviceCategoryRepository, ServiceCategoryMapper serviceCategoryMapper, VendorRepository vendorRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.serviceCategoryMapper = serviceCategoryMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void createServiceCategory(ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategory serviceCategory = serviceCategoryMapper.fromServiceCategoryDTO(serviceCategoryDTO);
        Vendor vendor = vendorRepository.findByVendorId(serviceCategoryDTO.getVendorDTO().getVendorId()).orElseThrow(() -> new EntityNotFoundException("Vendor with ID " + serviceCategoryDTO.getVendorDTO().getVendorId() + " not found"));
        serviceCategory.setVendor(vendor);
        serviceCategoryRepository.save(serviceCategory);
    }

    @Override
    public List<ServiceCategoryDTO> getServiceCategoriesByVendorId(Long vendorId) {
        return serviceCategoryRepository.findByVendor_VendorId(vendorId).stream().map(serviceCategoryMapper::fromServiceCategory).collect(Collectors.toList());
    }
}
