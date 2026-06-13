package com.bibhu.employeemanagementsystem.service;

import com.bibhu.employeemanagementsystem.dto.EmployeeDTO;
import com.bibhu.employeemanagementsystem.entity.Employee;
import com.bibhu.employeemanagementsystem.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;


    @Test
    void shouldSaveEmployeeSuccessfully() {

        EmployeeDTO employeeDTO = new EmployeeDTO(
                null,
                "Bibhu",
                "bibhu@gmail.com",
                "Engineering"
        );

        Employee savedEmployee = new Employee();

        savedEmployee.setId(1L);
        savedEmployee.setName("Bibhu");
        savedEmployee.setEmail("bibhu@gmail.com");
        savedEmployee.setDepartment("Engineering");

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        EmployeeDTO result =
                employeeService.saveEmployee(employeeDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Bibhu", result.getName());

        verify(employeeRepository, times(1))
                .save(any(Employee.class));
    }

    @Test
    void shouldGetEmployeeById() {

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setName("Bibhu");
        employee.setEmail("bibhu@gmail.com");
        employee.setDepartment("Engineering");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeDTO result =
                employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Bibhu", result.getName());

        verify(employeeRepository, times(1))
                .findById(1L);
    }

    @Test
    void shouldGetAllEmployees() {

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Bibhu");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("John");

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2));

        List<EmployeeDTO> employees =
                employeeService.getAllEmployees();

        assertEquals(2, employees.size());

        verify(employeeRepository, times(1))
                .findAll();
    }
    @Test
    void shouldDeleteEmployee() {

        doNothing()
                .when(employeeRepository)
                .deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1))
                .deleteById(1L);
    }

}