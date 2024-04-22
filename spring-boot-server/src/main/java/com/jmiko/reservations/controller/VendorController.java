package com.jmiko.reservations.controller;

import com.jmiko.reservations.dto.RegisterVendorDTO;
import com.jmiko.reservations.service.VendorService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/register-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody RegisterVendorDTO registerVendorDTO) {
        try {
            vendorService.createVendor(registerVendorDTO);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("The email address is already in use");
            }
            throw e;
        }
    }

}
