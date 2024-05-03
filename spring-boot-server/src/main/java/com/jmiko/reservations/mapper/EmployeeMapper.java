package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.EmployeeDTO;
import com.jmiko.reservations.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

    public EmployeeDTO fromUser(User user) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(user, employeeDTO);
        return employeeDTO;
    }
}
