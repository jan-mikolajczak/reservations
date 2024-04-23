package com.jmiko.reservations.service;

import com.jmiko.reservations.dto.ServiceCategoryDTO;

import java.util.List;

public interface ServiceCategoriesService {
    ServiceCategoryDTO createServiceCategory(ServiceCategoryDTO serviceCategoryDTO);

    List<ServiceCategoryDTO> getServiceCategoriesByVendorId(Long vendorId);
}
