package com.jmiko.reservations.repository;

import com.jmiko.reservations.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByVendorId(Long vendorId);
}
