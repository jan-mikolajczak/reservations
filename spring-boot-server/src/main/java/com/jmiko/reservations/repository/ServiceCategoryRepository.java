package com.jmiko.reservations.repository;

import com.jmiko.reservations.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

    List<ServiceCategory> findByVendor_VendorId(Long id);

    @Query("SELECT DISTINCT sc FROM ServiceCategory sc LEFT JOIN FETCH sc.services WHERE sc.vendor.vendorId = :vendorId")
    List<ServiceCategory> findAllWithProducts(@Param("vendorId")Long vendorId);

}
