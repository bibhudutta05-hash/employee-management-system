package com.bibhu.employeemanagementsystem.controller;

import com.bibhu.employeemanagementsystem.entity.Employee;
import com.bibhu.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.bibhu.employeemanagementsystem.dto.EmployeeDTO;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Create Employee
    @PostMapping @Operation(summary = "Create new employee")
    public EmployeeDTO saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.saveEmployee(employeeDTO);
    }


    // Get All Employees
    @GetMapping
    @Operation(summary = "Get all employees")
    public List<EmployeeDTO> getAllEmployees() {

        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}") @Operation(summary = "Get employee by ID")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}") @Operation(summary = "Update employee by ID")
    public Employee updateEmployee(@PathVariable Long id,
                                   @RequestBody Employee employee) {

        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}") @Operation(summary = "Delete employee by ID")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "Employee deleted successfully!";
    }
}