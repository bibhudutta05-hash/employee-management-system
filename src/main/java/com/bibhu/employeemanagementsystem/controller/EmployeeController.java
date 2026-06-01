package com.bibhu.employeemanagementsystem.controller;

import com.bibhu.employeemanagementsystem.entity.Employee;
import com.bibhu.employeemanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    @GetMapping("/page")
    @Operation(summary = "Get employees with pagination")
    public Page<EmployeeDTO> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return employeeService.getEmployeesWithPagination(page, size);
    }

    @GetMapping("/{id}") @Operation(summary = "Get employee by ID")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping("/search")
    @Operation(summary = "Search employee by name")
    public List<EmployeeDTO> searchByName(
            @RequestParam String name) {

        return employeeService.searchByName(name);
    }

    @GetMapping("/search/department")
    @Operation(summary = "Search employee by department")
    public List<EmployeeDTO> searchByDepartment(
            @RequestParam String department) {

        return employeeService.searchByDepartment(department);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update employee by ID")
    public EmployeeDTO updateEmployee(@PathVariable Long id,
                                      @Valid @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}") @Operation(summary = "Delete employee by ID")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "Employee deleted successfully!";
    }
}