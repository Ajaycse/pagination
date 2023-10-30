package com.xapp.jpa.pagination.service;

import com.xapp.jpa.pagination.EmployeeMapper;
import com.xapp.jpa.pagination.common.EmployeeType;
import com.xapp.jpa.pagination.dto.EmployeeDTO;
import com.xapp.jpa.pagination.entity.Employee;
import com.xapp.jpa.pagination.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setDept("Engineering");
        employee.setSalary(1000L);
        employee.setType(EmployeeType.PERMANENT);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setDept("Engineering");
        employeeDTO.setSalary(1000L);
        employeeDTO.setType(EmployeeType.PERMANENT);
    }

    // Existing tests...

    @Test
    public void testGetEmployeesWithPaginationWhenEmployeesExistThenReturnPageWithEmployees() {
        // Arrange
        Page<Employee> page = new PageImpl<>(Arrays.asList(employee));
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        Page<Employee> result = employeeService.getEmployeesWithPagination(PageRequest.of(0, 5));

        // Assert
        verify(employeeRepository, times(1)).findAll(any(Pageable.class));
        assertThat(result.getContent()).containsExactly(employee);
    }

    @Test
    public void testGetEmployeesWithPaginationWhenNoEmployeesThenReturnEmptyPage() {
        // Arrange
        Page<Employee> page = new PageImpl<>(Collections.emptyList());
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        Page<Employee> result = employeeService.getEmployeesWithPagination(PageRequest.of(0, 5));

        // Assert
        verify(employeeRepository, times(1)).findAll(any(Pageable.class));
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    public void testGetEmployeesWithPagination() {
        // Arrange
        Page<Employee> page = new PageImpl<>(Arrays.asList(employee));
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        Page<Employee> result = employeeService.getEmployeesWithPagination(Pageable.unpaged());

        // Assert
        verify(employeeRepository, times(1)).findAll(any(Pageable.class));
        assertThat(result.getContent()).containsExactly(employee);
    }

    @Test
    public void testSaveEmployee() {
        // Arrange
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        Employee result = employeeService.saveEmployee(employee);

        // Assert
        verify(employeeRepository, times(1)).save(employee);
        assertThat(result).isEqualTo(employee);
    }

    @Test
    public void testUpdateEmployee() {
        // Arrange
        when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.of(employee));
       // when(employeeMapper.updateEntity(employee, employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);

        // Act
        EmployeeDTO result = employeeService.updateEmployee(employeeDTO);

        // Assert
        verify(employeeRepository, times(1)).findById(employeeDTO.getId());
        verify(employeeMapper, times(1)).updateEntity(employee, employeeDTO);
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).mapToDto(employee);
        assertThat(result).isEqualTo(employeeDTO);
    }

    @Test
    public void testUpdateEmployeeWhenEmployeeDoesNotExistThenThrowException() {
        // Arrange
        when(employeeRepository.findById(employeeDTO.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.updateEmployee(employeeDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Unable to find Employee id=1");
    }

    @Test
    public void testDeleteEmployeeWhenEmployeeExistsThenReturnTrue() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        Boolean result = employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).deleteById(1L);
        assertThat(result).isTrue();
    }

    @Test
    public void testDeleteEmployeeWhenEmployeeDoesNotExistThenReturnFalse() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Boolean result = employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(0)).deleteById(1L);
        assertThat(result).isFalse();
    }

    @Test
    public void testGetEmployeeWhenEmployeeExistsThenReturnEmployee() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        Employee result = employeeService.getEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).findById(1L);
        assertThat(result).isEqualTo(employee);
    }

    @Test
    public void testGetEmployeeWhenEmployeeDoesNotExistThenThrowException() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.getEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Unable to find EmployeeService id=1");
    }

    @Test
    public void testGetAllEmployeesWhenEmployeesExistThenReturnEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        verify(employeeRepository, times(1)).findAll();
        assertThat(result).isEqualTo(employees);
    }

    @Test
    public void testGetAllEmployeesWhenNoEmployeesThenReturnEmptyList() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        verify(employeeRepository, times(1)).findAll();
        assertThat(result).isEmpty();
    }
}