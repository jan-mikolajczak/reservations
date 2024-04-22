package com.jmiko.reservations.mapper;

import com.jmiko.reservations.dto.ServiceCategoryDTO;
import com.jmiko.reservations.dto.ServiceDTO;
import com.jmiko.reservations.model.Service;
import com.jmiko.reservations.model.ServiceCategory;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceCategoryMapper {

    public ServiceCategory fromServiceCategoryDTO(ServiceCategoryDTO serviceCategoryDTO) {
        ServiceCategory serviceCategory = new ServiceCategory();
        BeanUtils.copyProperties(serviceCategoryDTO, serviceCategory);
        return serviceCategory;
    }

    public ServiceCategoryDTO fromServiceCategory(ServiceCategory serviceCategory) {
        ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();
        BeanUtils.copyProperties(serviceCategory, serviceCategoryDTO);

        if (Objects.nonNull(serviceCategory.getServices())) {
            Set<ServiceDTO> serviceDTOS = serviceCategory.getServices().stream()
                    .map(this::mapServiceToDTO)
                    .collect(Collectors.toSet());
            serviceCategoryDTO.setServiceDTO(serviceDTOS);
        }
        return serviceCategoryDTO;
    }

    private ServiceDTO mapServiceToDTO(Service service) {
        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtils.copyProperties(service, serviceDTO);
        return serviceDTO;
    }
}
