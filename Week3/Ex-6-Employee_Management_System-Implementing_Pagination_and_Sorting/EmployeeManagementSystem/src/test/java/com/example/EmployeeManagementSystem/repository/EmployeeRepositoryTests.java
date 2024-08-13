package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private com.example.employeemanagementsystem.repository.EmployeeRepository employeeRepository;

    @Autowired
    private com.example.employeemanagementsystem.repository.DepartmentRepository departmentRepository;

    @Test
    public void testFindByEmail() {
        Department department = new Department();
        department.setName("HR");
        departmentRepository.save(department);

        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDepartment(department);
        employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.findByEmail("john.doe@example.com");
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getName()).isEqualTo("John Doe");
    }
}
