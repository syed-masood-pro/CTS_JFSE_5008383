package com.example.employeemanagementsystem.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = "Employee.findbyName", query = "SELECT e FROM Employee e WHERE e.name = :name"),
        @NamedQuery(name = "Employee.findByemail", query = "SELECT e FROM Employee e WHERE e.email = :email"),
        @NamedQuery(name = "Employee.findBydepartmentId", query = "SELECT e FROM Employee e WHERE e.department.id = :departmentId")
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "department_id")
    private com.example.employeemanagementsystem.entity.Department department;



    // Getters and setters
}
