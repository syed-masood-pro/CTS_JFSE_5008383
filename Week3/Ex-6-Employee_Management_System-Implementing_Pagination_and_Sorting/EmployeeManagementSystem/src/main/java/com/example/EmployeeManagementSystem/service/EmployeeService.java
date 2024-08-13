package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findEmployeesByName(name);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> getEmployeesByName(String name, Pageable pageable) {
        return employeeRepository.findByName(name, pageable);
    }

    public Page<Employee> getEmployeesByDepartmentId(Long departmentId, Pageable pageable) {
        return employeeRepository.findByDepartmentId(departmentId, pageable);
    }


}
