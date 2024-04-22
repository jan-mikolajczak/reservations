package com.jmiko.reservations.service.impl;

import com.jmiko.reservations.config.UserRole;
import com.jmiko.reservations.dto.RegisterVendorDTO;
import com.jmiko.reservations.dto.VendorDTO;
import com.jmiko.reservations.mapper.VendorMapper;
import com.jmiko.reservations.model.Role;
import com.jmiko.reservations.model.User;
import com.jmiko.reservations.model.Vendor;
import com.jmiko.reservations.repository.RoleRepository;
import com.jmiko.reservations.repository.UserRepository;
import com.jmiko.reservations.repository.VendorRepository;
import com.jmiko.reservations.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.fromVendorDTO(vendorDTO);
        vendorRepository.save(vendor);

    }

    @Override
    public void createVendor(RegisterVendorDTO registerVendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setName(registerVendorDTO.getCompanyName());
        vendor.setNip(registerVendorDTO.getNip());
        Vendor savedVendor = vendorRepository.save(vendor);

        User user = new User();
        user.setVendor(savedVendor);
        user.setPassword(passwordEncoder.encode(registerVendorDTO.getPassword()));
        user.setEmail(registerVendorDTO.getEmail());
        user.setName(registerVendorDTO.getFullName());
        user.setPhoneNumber(registerVendorDTO.getPhoneNumber());
        Role role = roleRepository.findByName(UserRole.MANAGER.name())
                .orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
