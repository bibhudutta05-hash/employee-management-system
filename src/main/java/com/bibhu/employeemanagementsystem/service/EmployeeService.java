package com.bibhu.employeemanagementsystem.service;
import com.bibhu.employeemanagementsystem.dto.EmployeeDTO;
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
    public Page<EmployeeDTO> getEmployeesWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return employeeRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
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
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee != null) {

            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setDepartment(employeeDTO.getDepartment());

            Employee updatedEmployee =
                    employeeRepository.save(existingEmployee);

            return convertToDTO(updatedEmployee);
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

    public List<EmployeeDTO> searchByName(String name) {

        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<EmployeeDTO> searchByDepartment(String department) {

        return employeeRepository.findByDepartmentContainingIgnoreCase(department)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
}