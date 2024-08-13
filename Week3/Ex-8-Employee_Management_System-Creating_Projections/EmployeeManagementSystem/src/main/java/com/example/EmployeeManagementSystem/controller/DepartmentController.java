package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.DepartmentDTO;
import com.example.employeemanagementsystem.projection.DepartmentProjection;
import com.example.employeemanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/projections")
    public List<DepartmentProjection> getDepartmentProjections() {
        return departmentService.getDepartmentProjections();
    }

    @GetMapping("/dto")
    public List<DepartmentDTO> getDepartmentDTOs() {
        return departmentService.getDepartmentDTOs();
    }
}
