package com.jmiko.reservations.controller;

import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.service.impl.ServiceCategoriesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping
public class ServiceCategoryController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ServiceCategoriesServiceImpl productCategoriesService;


    public ServiceCategoryController(ServiceCategoriesServiceImpl productCategoriesService) {
        this.productCategoriesService = productCategoriesService;
    }

    @GetMapping("/product-categories")
    public ResponseEntity<List<ServiceCategoryDTO>> getProductCategoriesByVendor(@RequestParam Long vendorId) {
        log.info("Request to get categories for vendor ID: {}", vendorId);

        List<ServiceCategoryDTO> productCategoriesByVendorId = productCategoriesService.getServiceCategoriesByVendorId(vendorId);
//        List<ProductCategoryDTO> productCategory = productCategoryRepository.findByVendor_VendorId(vendorId);

        if (productCategoriesByVendorId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } else return new ResponseEntity<>(productCategoriesByVendorId, HttpStatus.OK);
    }
}
