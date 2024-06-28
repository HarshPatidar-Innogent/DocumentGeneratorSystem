package com.dgs.service.iService;

import com.dgs.DTO.DepartmentDTO;

import java.util.List;

public interface IDepartmentService {
    //    List<DepartmentDTO> getAllDepartment();
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    List<DepartmentDTO> getAll();

    DepartmentDTO update(Long id, DepartmentDTO departmentDTO);

    Boolean delete(Long departmentId);

    DepartmentDTO getDepartmentByName(String name);

    DepartmentDTO getDepartmentById(Long id);
}
