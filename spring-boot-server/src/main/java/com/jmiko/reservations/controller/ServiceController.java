package com.jmiko.reservations.controller;

import com.jmiko.reservations.dto.ServiceDTO;
import com.jmiko.reservations.mapper.ServiceMapper;
import com.jmiko.reservations.repository.ServiceRepository;
import com.jmiko.reservations.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/services")
public class ServiceController {

	private final ServiceRepository serviceRepository;
	private final ServiceService serviceService;
	private final ServiceMapper serviceMapper;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public ServiceController(ServiceRepository serviceRepository, ServiceService serviceService, ServiceMapper serviceMapper) {
		this.serviceRepository = serviceRepository;
        this.serviceService = serviceService;
        this.serviceMapper = serviceMapper;
    }

	@GetMapping("/all")
	public ResponseEntity<List<ServiceDTO>> getAllServices(@RequestParam(required = false) String name) {
		log.debug("Get all products");
		try {
			List<ServiceDTO> products = new ArrayList<>();

			if (name == null)
                products.addAll(serviceRepository.findAll().stream().map(serviceMapper::fromService).toList());
			else
                products.addAll(serviceRepository.findByNameContaining(name).stream().map(serviceMapper::fromService).toList());

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/byVendorId")
	public ResponseEntity<List<ServiceDTO>> getProductsByVendor(@RequestParam(required = false) Long vendorId) {
		List<ServiceDTO> productsByVendorId = serviceService.getServicesByVendorId(vendorId);
		if (productsByVendorId.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		} else return new ResponseEntity<>(productsByVendorId, HttpStatus.OK);
	}

}
