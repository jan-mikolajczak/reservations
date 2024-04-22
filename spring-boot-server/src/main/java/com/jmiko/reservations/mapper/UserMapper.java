package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.UserDTO;
import com.jmiko.reservations.dto.VendorDTO;
import com.jmiko.reservations.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserMapper {

    public User fromUserDTO(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        if (Objects.nonNull(user.getVendor())) {
            userDTO.setVendor(new VendorDTO(user.getVendor().getVendorId(), user.getVendor().getName(), user.getVendor().getNip()));
        }
        return userDTO;
    }
}
