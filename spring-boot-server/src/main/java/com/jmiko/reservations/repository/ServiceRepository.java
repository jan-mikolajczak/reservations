package com.jmiko.reservations.repository;

import com.jmiko.reservations.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByNameContaining(String name);

    Optional<Service> findByCategoryId(Long categoryId);

    List<Service> findAllByCategory_Vendor_VendorId(Long vendorId);

}
