package com.dgs.service.serviceImpl;

import com.dgs.DTO.DepartmentDTO;
import com.dgs.entity.Department;
import com.dgs.exception.CustomException.DepartmentException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DepartmentRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private MapperConfig mapperConfig;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
            departmentDTO.setDepartmentId(null);
            Department department = mapperConfig.toDepartment(departmentDTO);
            Department savedDepartment = departmentRepo.save(department);
            return mapperConfig.toDepartmentDTO(savedDepartment);
    }

    @Override
    public List<DepartmentDTO> getAll() {
        List<Department> departments = departmentRepo.findAll();
        if(departments.isEmpty()){
            throw new DepartmentException("Departments not Found", HttpStatus.NOT_FOUND);
        }
        return departments.stream()
                .map(mapperConfig::toDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override

    public DepartmentDTO update(Long departmentId, DepartmentDTO departmentDTO){
        Optional<Department> optionalDepartment = departmentRepo.findById(departmentId);
        if(optionalDepartment.isEmpty()){
            throw new DepartmentException("Department Not Found By Id",HttpStatus.NOT_FOUND);
        }
        Department department = optionalDepartment.get();
        department.setDepartmentName(departmentDTO.getDepartmentName());
        Department update = departmentRepo.save(department);
        return  mapperConfig.toDepartmentDTO(update);

    }

    @Override
    public DepartmentDTO getDepartmentByName(String name) {
        Optional<Department> departmentOptional = departmentRepo.findByDepartmentName(name);
        if(departmentOptional.isEmpty()){
            throw new DepartmentException("Department not found By Name",HttpStatus.NOT_FOUND);
        }
        Department department = departmentOptional.get();
        return mapperConfig.toDepartmentDTO(department);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Optional<Department> departmentOptional = departmentRepo.findById(id);
        if (departmentOptional.isEmpty()) {
            throw new DepartmentException("department not found with id: " + id,HttpStatus.NOT_FOUND);
        }
        return mapperConfig.toDepartmentDTO(departmentOptional.get());
    }

    public Boolean delete(Long departmentId) {
        if (!departmentRepo.existsById(departmentId)) {
            throw new DepartmentException("Department not found with id: " + departmentId,HttpStatus.NOT_FOUND);
        }
        if (userRepo.existsByDepartment(departmentRepo.findById(departmentId).get())) {
            return false;
        }
        departmentRepo.deleteByDepartmentId(departmentId);
        return true;
    }
}
