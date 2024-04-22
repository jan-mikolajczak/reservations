package com.jmiko.reservations.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private String imageUrl;
    private ServiceCategoryDTO serviceCategory;


    public ServiceDTO() {
    }

    public ServiceDTO(String name, String description, BigDecimal price, Boolean active, String imageUrl, ServiceCategoryDTO category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.imageUrl = imageUrl;
        this.serviceCategory = category;
    }
}
