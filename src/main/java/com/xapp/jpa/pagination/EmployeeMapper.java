package com.xapp.jpa.pagination;

import com.xapp.jpa.pagination.config.SharedMapperConfig;
import com.xapp.jpa.pagination.dto.EmployeeDTO;
import com.xapp.jpa.pagination.entity.Employee;
import org.mapstruct.*;

@Mapper(config = SharedMapperConfig.class)
public abstract class EmployeeMapper {

    public abstract EmployeeDTO mapToDto(Employee employee);

    public abstract Employee mapToEntity(EmployeeDTO employeeDTO);

    public abstract void updateEntity(@MappingTarget Employee employee, EmployeeDTO employeeDTO);
}
