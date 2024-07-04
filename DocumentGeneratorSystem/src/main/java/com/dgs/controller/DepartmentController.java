package com.dgs.controller;

import com.dgs.DTO.DepartmentDTO;
import com.dgs.DTO.DesignationDTO;
import com.dgs.exception.CustomException.DepartmentException;
import com.dgs.mapper.MapperConfig;
import com.dgs.service.iService.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/addDept")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try{
            departmentDTO.setDepartmentId(null);
            DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
            return ResponseEntity.ok(createdDepartment);
        }catch (Exception e){
            throw new RuntimeException("Exception occurred in creating a department");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        try{
            List<DepartmentDTO> departments = departmentService.getAll();
            return ResponseEntity.ok(departments);
        }catch (Exception e){
            throw new RuntimeException("Exception occurred in fetching all departments");
        }
    }

    @PutMapping("/update/{id}")

    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        try{
            DepartmentDTO updatedDepartment = departmentService.update(id, departmentDTO);
            return ResponseEntity.ok(updatedDepartment);
        }catch (Exception e){
            throw new RuntimeException("Exception occurred in updating the department");
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<DesignationDTO>delete(@PathVariable long id) {
//        departmentService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
@DeleteMapping("/delete/{id}")
public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    try {
        Boolean result = departmentService.delete(id);
        return ResponseEntity.ok(result);
    } catch (RuntimeException e) {
        throw new RuntimeException("Exception occurred in deleting an department");
    }
}

    @GetMapping("/getByName/{name}")
    public ResponseEntity<DepartmentDTO> getByName(@PathVariable String name) {
        try{
            DepartmentDTO departmentDTO = departmentService.getDepartmentByName(name);
            return ResponseEntity.ok(departmentDTO);
        }catch (Exception e){
            throw new RuntimeException("Exception occurred in fetching department");
        }
    }

    @GetMapping("/getDept/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        try{
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(departmentDTO);
        }catch (Exception e){
            throw new RuntimeException(
                    "Exception occurred in fetching department");
        }
    }

}
