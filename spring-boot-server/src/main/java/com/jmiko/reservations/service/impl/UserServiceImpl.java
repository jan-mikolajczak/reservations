package com.jmiko.reservations.service.impl;

import com.jmiko.reservations.config.UserRole;
import com.jmiko.reservations.dto.EmployeeDTO;
import com.jmiko.reservations.dto.UserDTO;
import com.jmiko.reservations.dto.VendorDTO;
import com.jmiko.reservations.mapper.EmployeeMapper;
import com.jmiko.reservations.mapper.UserMapper;
import com.jmiko.reservations.model.Role;
import com.jmiko.reservations.model.User;
import com.jmiko.reservations.model.Vendor;
import com.jmiko.reservations.repository.RoleRepository;
import com.jmiko.reservations.repository.UserRepository;
import com.jmiko.reservations.repository.VendorRepository;
import com.jmiko.reservations.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EmployeeMapper employeeMapper;
    private final VendorRepository vendorRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper, EmployeeMapper employeeMapper, VendorRepository vendorRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.employeeMapper = employeeMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public String createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName(UserRole.MANAGER.name())
                .orElseThrow(() -> new EntityNotFoundException("Role Not Found"));

        user.getRoles().add(role);

        userRepository.save(user);
        return user.getEmail();
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = userMapper.fromUserDTO(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Vendor vendor = vendorRepository.findByVendorId(userDTO.getVendor().getVendorId()).orElseThrow(() -> new EntityNotFoundException("Vendor Not Found"));
        user.setVendor(vendor);
        userRepository.save(user);
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

    }

    @Override
    public void assignRoleToUser(UserDTO userDTO, UserRole userRole) {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        Role role = roleRepository.findByName(userRole.name())
                .orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public UserDTO getVendorByLogin(String login) {
        return userRepository.findByEmail(login).stream().map(user -> userMapper.fromUser(user)).collect(Collectors.toList()).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("User Not Found " + login));
    }

    @Override
    public List<EmployeeDTO> getVendorUsers(Long vendorId) {
        return userRepository.findByVendor_VendorId(vendorId).stream().map(employeeMapper::fromUser).toList();
    }

}
