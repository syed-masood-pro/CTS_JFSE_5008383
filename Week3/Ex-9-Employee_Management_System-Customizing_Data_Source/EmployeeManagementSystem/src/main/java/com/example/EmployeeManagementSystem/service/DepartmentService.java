package com.example.employeemanagementsystem.service;


import com.example.EmployeeManagementSystem.entity.secondary.Department;
import com.example.employeemanagementsystem.repository.secondary.SecondaryDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private SecondaryDepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

}
