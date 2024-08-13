package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.projection.DepartmentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d.id AS id, d.name AS name FROM Department d")
    List<DepartmentProjection> findDepartmentProjections();

    @Query("SELECT new com.example.employeemanagementsystem.dto.DepartmentDTO(d.id, d.name) FROM Department d")
    List<com.example.employeemanagementsystem.dto.DepartmentDTO> findDepartmentDTOs();
}
