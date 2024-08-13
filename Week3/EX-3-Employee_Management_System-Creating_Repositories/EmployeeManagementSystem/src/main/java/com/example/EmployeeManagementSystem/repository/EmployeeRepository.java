package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom query method to find employees by name
    List<Employee> findByName(String name);

    // Custom query method to find employees by email
    Employee findByEmail(String email);
}
