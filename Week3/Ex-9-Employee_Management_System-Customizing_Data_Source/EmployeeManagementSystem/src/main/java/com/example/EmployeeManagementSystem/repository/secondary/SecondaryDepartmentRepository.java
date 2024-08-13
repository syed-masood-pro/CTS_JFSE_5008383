package com.example.employeemanagementsystem.repository.secondary;


import com.example.EmployeeManagementSystem.entity.secondary.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryDepartmentRepository extends JpaRepository<Department, Long> {
}
