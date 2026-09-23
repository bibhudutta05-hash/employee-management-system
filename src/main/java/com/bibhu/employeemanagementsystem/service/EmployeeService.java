package com.bibhu.employeemanagementsystem.service;

import com.bibhu.employeemanagementsystem.dto.EmployeeDTO;
import com.bibhu.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bibhu.employeemanagementsystem.dto.DepartmentDTO;
import com.bibhu.employeemanagementsystem.dto.EmployeeResponseDTO;
import com.bibhu.employeemanagementsystem.entity.Employee;
import com.bibhu.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;

    public EmployeeService(EmployeeRepository employeeRepository,
                           RestTemplate restTemplate) {

        this.employeeRepository = employeeRepository;
        this.restTemplate = restTemplate;
    }

    // Save Employee
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        Employee employee = convertToEntity(employeeDTO);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    // Get All Employees
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Get Employee By Id
    public EmployeeDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            return convertToDTO(employee);
        }

        return null;
    }
    public EmployeeResponseDTO getEmployeeWithDepartment(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElse(null);

        if (employee == null) {
            return null;
        }

        DepartmentDTO department =
                restTemplate.getForObject(
                        "http://localhost:8081/departments/" + employee.getDepartmentId(),
                        DepartmentDTO.class
                );

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                department
        );

    }

    // Update Employee
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee != null) {

            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setDepartmentId(employeeDTO.getDepartmentId());

            Employee updatedEmployee =
                    employeeRepository.save(existingEmployee);

            return convertToDTO(updatedEmployee);
        }

        return null;
    }

    // Delete Employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // Pagination
    public Page<EmployeeDTO> getEmployeesWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return employeeRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // Search By Name
    public List<EmployeeDTO> searchByName(String name) {

        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Search By Department Id
    public List<EmployeeDTO> searchByDepartment(Long departmentId) {

        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Convert Entity -> DTO
    private EmployeeDTO convertToDTO(Employee employee) {

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartmentId()
        );
    }

    // Convert DTO -> Entity
    private Employee convertToEntity(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartmentId(employeeDTO.getDepartmentId());

        return employee;
    }
}