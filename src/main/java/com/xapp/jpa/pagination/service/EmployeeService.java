package com.xapp.jpa.pagination.service;


import com.xapp.jpa.pagination.EmployeeMapper;
import com.xapp.jpa.pagination.dto.EmployeeDTO;
import com.xapp.jpa.pagination.entity.Employee;
import com.xapp.jpa.pagination.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    //TODO need to remove below unused variable
    Long tempVariable = Long.valueOf(0);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    public Page<Employee> getEmployeesWithPagination(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException(EmployeeService.class, employeeId));
    }

    public Boolean deleteEmployee(long employeeId) {
        if(employeeRepository.findById(employeeId).isPresent()) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDto) {

        var employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> new ResourceNotFoundException(Employee.class, employeeDto.getId()));

        employeeDto.setType(employee.getType());
        employeeMapper.updateEntity(employee, employeeDto);

        Employee employeeEntity = employeeRepository.save(employee);

        return employeeMapper.mapToDto(employeeEntity);
    }
}
