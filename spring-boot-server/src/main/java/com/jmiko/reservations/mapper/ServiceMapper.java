package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.dto.ServiceDTO;
import com.jmiko.reservations.model.Service;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@org.springframework.stereotype.Service
public class ServiceMapper {
    public Service fromServiceDTO(ServiceDTO serviceDto) {
        Service service = new Service();
        BeanUtils.copyProperties(serviceDto, service);
        return service;
    }

    public ServiceDTO fromService(Service service) {
        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtils.copyProperties(service, serviceDTO);
        if (Objects.nonNull(service.getCategory())) {
            serviceDTO.setServiceCategory(new ServiceCategoryDTO(service.getCategory().getId(), service.getCategory().getCategoryName(), service.getCategory().getDescription(), service.getCategory().isActive()));
        }
        return serviceDTO;
    }
}
