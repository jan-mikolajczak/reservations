package com.jmiko.reservations.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ServiceCategoryDTO {
    private Long id;
    private String categoryName;
    private String description;
    private boolean active;
    private Set<ServiceDTO> serviceDTO;
    private VendorDTO vendorDTO;

    public ServiceCategoryDTO() {
    }

    public ServiceCategoryDTO(Long id, String categoryName, String description, boolean active) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.active = active;
    }
}
