package com.xapp.jpa.pagination.controller;


import com.xapp.jpa.pagination.entity.Employee;
import com.xapp.jpa.pagination.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public void saveEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
    }

    @GetMapping
    public Page<Employee> getEmployees(Pageable pageable){
        return employeeService.getEmployeesWithPagination(pageable);
    }
}
