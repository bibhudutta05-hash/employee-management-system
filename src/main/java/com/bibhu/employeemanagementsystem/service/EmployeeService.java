package com.bibhu.employeemanagementsystem.service;
import com.bibhu.employeemanagementsystem.dto.EmployeeDTO;
import com.bibhu.employeemanagementsystem.entity.Employee;
import com.bibhu.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Save Employee
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        Employee employee = convertToEntity(employeeDTO);

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    // Get All Employees
// Get All Employees
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }    public EmployeeDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            return convertToDTO(employee);
        }

        return null;
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

        if (existingEmployee != null) {

            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());

            return employeeRepository.save(existingEmployee);
        }

        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    private EmployeeDTO convertToDTO(Employee employee) {

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment()
        );
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());

        return employee;
    }
}