package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(name = "Employee.findByname")
    List<Employee> findEmployeesByName(@Param("name") String name);

    @Query(name = "Employee.findByemail")
    Employee findEmployeeByEmail(@Param("email") String email);

    @Query(name = "Employee.findBydepartmentId")
    List<Employee> findEmployeesByDepartmentId(@Param("departmentId") Long departmentId);
}
