package com.qiluhospital.chemotherapy.mdtbackground.service;

import com.qiluhospital.chemotherapy.mdtbackground.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Boolean AddDepartment(String departmentName);
    Department findById(Long id);
    Boolean updateById(Long id, String name);


}
