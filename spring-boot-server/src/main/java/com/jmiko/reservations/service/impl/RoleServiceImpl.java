package com.jmiko.reservations.service.impl;

import com.jmiko.reservations.model.Role;
import com.jmiko.reservations.repository.RoleRepository;
import com.jmiko.reservations.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
