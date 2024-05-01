package com.jmiko.reservations.runner;


import com.jmiko.reservations.config.UserRole;
import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.dto.ServiceDTO;
import com.jmiko.reservations.dto.UserDTO;
import com.jmiko.reservations.dto.VendorDTO;
import com.jmiko.reservations.mapper.VendorMapper;
import com.jmiko.reservations.repository.VendorRepository;
import com.jmiko.reservations.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {

    private final ServiceService serviceService;

    private final ServiceCategoriesService serviceCategoriesService;

    private final RoleService roleService;

    private final VendorService vendorService;

    private final UserService userService;

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    public MyRunner(ServiceService serviceService, ServiceCategoriesService serviceCategoriesService, RoleService roleService, VendorService vendorService, UserService userService, VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.serviceService = serviceService;
        this.serviceCategoriesService = serviceCategoriesService;
        this.roleService = roleService;
        this.vendorService = vendorService;
        this.userService = userService;
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        createVendors();
        createCategories();
        createServices();
        createRoles();
        createManager();
        createTestUser();
    }


    private void createRoles() {
        Arrays.asList(UserRole.USER.name(), UserRole.EMPLOYEE.name(), UserRole.MANAGER.name(), UserRole.ADMIN.name()).forEach(
                role -> roleService.createRole(role));
    }

    private void createManager() {
        UserDTO userDTO = new UserDTO();
        userDTO.setCompanyName("Company name");
        userDTO.setPassword("password");
        userDTO.setPhoneNumber("123456789");
        userDTO.setEmail("manager@test.pl");
        userDTO.setName("Manager Manager");
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorId(1L);
        userDTO.setVendor(vendorDTO);
        userService.createUser(userDTO);
        userService.assignRoleToUser(userDTO, UserRole.MANAGER);
    }

    private void createTestUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setCompanyName("Company name");
        userDTO.setPassword("password");
        userDTO.setPhoneNumber("123456789");
        userDTO.setEmail("employee@test.pl");
        userDTO.setName("Employee Kowalski");
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorId(1L);
        userDTO.setVendor(vendorDTO);
        userService.createUser(userDTO);
        userService.assignRoleToUser(userDTO, UserRole.EMPLOYEE);
    }


/*    protected void createVendors() {
        List<Vendor> vendors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setName("Vendor name " + i);
            vendorDTO.setNip("12312312" + i);
            Vendor vendor = vendorMapper.fromVendorDTO(vendorDTO);
            vendors.add(vendor);
        }
        List<Vendor> vendors1 = vendorRepository.saveAll(vendors);

    }*/
    protected void createVendors() {
        for (int i = 0; i < 10; i++) {
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setName("Vendor name " + i);
            vendorDTO.setNip("12312312" + i);
            vendorService.createVendor(vendorDTO);
        }
    }

    private void createCategories() {
        for (int i = 0; i < 10; i++) {
            ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();
            serviceCategoryDTO.setCategoryName("Category name " + i);
            serviceCategoryDTO.setDescription("Catgeory description " + i);
            serviceCategoryDTO.setActive(true);
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setName("Fryzjer");
            vendorDTO.setVendorId(1L);
            serviceCategoryDTO.setVendorDTO(vendorDTO);
            serviceCategoriesService.createServiceCategory(serviceCategoryDTO);

        }
    }

    private void createServices() {
        for (Long j = 1L; j < 10L; j++) {
            for (int i = 0; i < 10; i++) {
                ServiceDTO serviceDto = new ServiceDTO();
                serviceDto.setName("Product name " + i);
                serviceDto.setDescription("Product description " + i);
                serviceDto.setPrice(new BigDecimal("99.99"));
                serviceDto.setActive(true);
                serviceDto.setImageUrl("www.google.pl");
                serviceService.createService(serviceDto, j);
            }
        }
    }

}
