package com.dgs.controller;

import com.dgs.DTO.DepartmentDTO;
import com.dgs.DTO.DesignationDTO;
import com.dgs.mapper.MapperConfig;
import com.dgs.service.iService.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private MapperConfig mapperConfig;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addDept")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        departmentDTO.setDepartmentId(null);
        DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(createdDepartment);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAll();
        return ResponseEntity.ok(departments);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.update(id, departmentDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<DepartmentDTO> delete(@PathVariable long id) {
//        departmentService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getByName/{name}")
    public ResponseEntity<DepartmentDTO> getByName(@PathVariable String name) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentByName(name);
        return ResponseEntity.ok(departmentDTO);
    }

    @GetMapping("/getDept/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(departmentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            Boolean result = departmentService.delete(id);
           return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}