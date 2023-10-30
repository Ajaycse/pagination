package com.xapp.jpa.pagination.controller;


import com.xapp.jpa.pagination.dto.EmployeeDTO;
import com.xapp.jpa.pagination.entity.Employee;
import com.xapp.jpa.pagination.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("employeeId")  long employeeId, @RequestBody EmployeeDTO employeeDto){
        employeeDto.setId(employeeId);
        return  ResponseEntity.ok(employeeService.updateEmployee(employeeDto));
    }


    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployees(Pageable pageable){
        return ResponseEntity.ok(employeeService.getEmployeesWithPagination(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("employee/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long employeeId){
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @DeleteMapping("employee/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable long employeeId){
        return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
    }
}
