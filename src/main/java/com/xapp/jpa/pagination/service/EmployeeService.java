package com.xapp.jpa.pagination.service;


import com.xapp.jpa.pagination.entity.Employee;
import com.xapp.jpa.pagination.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    public Page<Employee> getEmployeesWithPagination(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
