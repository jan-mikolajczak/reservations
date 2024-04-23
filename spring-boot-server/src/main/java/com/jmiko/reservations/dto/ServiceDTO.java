package com.jmiko.reservations.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private String imageUrl;
    private ServiceCategoryDTO serviceCategory;


    public ServiceDTO() {
    }

    public ServiceDTO(Long id, String name, String description, BigDecimal price, Boolean active, String imageUrl, ServiceCategoryDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.imageUrl = imageUrl;
        this.serviceCategory = category;
    }
}
