package com.jmiko.reservations.controller;

import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.service.VendorService;
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
@RequestMapping("/service-categories")
public class ServiceCategoryController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ServiceCategoriesServiceImpl productCategoriesService;
    private final VendorService vendorService;


    public ServiceCategoryController(ServiceCategoriesServiceImpl productCategoriesService, VendorService vendorService) {
        this.productCategoriesService = productCategoriesService;
        this.vendorService = vendorService;
    }

    @GetMapping("/by-vendor")
    public ResponseEntity<?> getProductCategoriesByVendor(@RequestParam Long vendorId) {
        log.info("Request to get categories for vendor ID: {}", vendorId);
        if (!vendorService.isVendorExists(vendorId)) {
            log.error("Vendor with ID: {} does not exist", vendorId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor with ID: " + vendorId + " does not exist!");
        }
        try {
            List<ServiceCategoryDTO> productCategoriesByVendorId = productCategoriesService.getServiceCategoriesByVendorId(vendorId);
            return ResponseEntity.ok(productCategoriesByVendorId);
        } catch (Exception e) {
            log.error("Error while fetching product categories for vendor ID: {}", vendorId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping
    public ServiceCategoryDTO saveCategory(@RequestBody ServiceCategoryDTO serviceCategoryDTO) {
        return productCategoriesService.createServiceCategory(serviceCategoryDTO);
    }

}
