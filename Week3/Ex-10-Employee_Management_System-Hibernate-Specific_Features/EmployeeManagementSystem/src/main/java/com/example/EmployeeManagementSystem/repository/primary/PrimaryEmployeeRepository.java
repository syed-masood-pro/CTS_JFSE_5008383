package com.example.employeemanagementsystem.repository.primary;


//import package com.example.EmployeeManagementSystem.entity.primary.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryEmployeeRepository extends JpaRepository<com.example.employeemanagementsystem.entity.Employee, Long> {
}
