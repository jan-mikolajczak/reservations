package com.jmiko.reservations.service;

import com.jmiko.reservations.config.UserRole;
import com.jmiko.reservations.dto.LoginDTO;
import com.jmiko.reservations.dto.UserDTO;
import com.jmiko.reservations.model.User;
import com.jmiko.reservations.response.LoginResponse;

public interface UserService {

    String createUser(User user);

    void createUser(UserDTO userDTO);

    User loadUserByEmail(String email);

    void assignRoleToUser(UserDTO userDTO, UserRole userRole);

    UserDTO getVendorByLogin(String login);
}
