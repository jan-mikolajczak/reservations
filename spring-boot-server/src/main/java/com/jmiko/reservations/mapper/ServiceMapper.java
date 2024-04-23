package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.dto.ServiceDTO;
import com.jmiko.reservations.model.Service;
import com.jmiko.reservations.model.ServiceCategory;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@org.springframework.stereotype.Service
public class ServiceMapper {
    public Service fromServiceDTO(ServiceDTO serviceDto) {
        Service service = new Service();
        BeanUtils.copyProperties(serviceDto, service);
        if (Objects.nonNull(serviceDto.getServiceCategory())) {
            service.setCategory(new ServiceCategory(serviceDto.getServiceCategory().getId(), serviceDto.getServiceCategory().getCategoryName(), serviceDto.getServiceCategory().getDescription(), serviceDto.getServiceCategory().isActive()));
        }
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
