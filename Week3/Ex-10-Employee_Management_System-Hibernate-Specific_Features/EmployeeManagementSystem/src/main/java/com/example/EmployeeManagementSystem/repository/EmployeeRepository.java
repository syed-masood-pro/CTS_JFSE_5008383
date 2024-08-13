package com.example.employeemanagementsystem.repository;

//import package com.example.EmployeeManagementSystem.entity.primary.Employee;
import com.example.employeemanagementsystem.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<com.example.employeemanagementsystem.entity.Employee, Long> {

    @Query("SELECT e.id AS id, e.name AS name, e.email AS email FROM Employee e")
    List<EmployeeProjection> findEmployeeProjections();

    @Query("SELECT new com.example.employeemanagementsystem.dto.EmployeeDTO(e.id, e.name, e.email) FROM Employee e")
    List<com.example.employeemanagementsystem.dto.EmployeeDTO> findEmployeeDTOs();
}
