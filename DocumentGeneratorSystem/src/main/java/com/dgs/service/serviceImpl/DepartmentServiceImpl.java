package com.dgs.service.serviceImpl;

import com.dgs.DTO.DepartmentDTO;
import com.dgs.entity.Department;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DepartmentRepo;
import com.dgs.service.iService.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private MapperConfig mapperConfig;

    @Autowired
    private DepartmentRepo departmentRepo;


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
        return departments.stream()
                .map(mapperConfig::toDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override

    public DepartmentDTO update(Long departmentId, DepartmentDTO departmentDTO){
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(()->new RuntimeException("Department not found with id :"+departmentId));

        department.setDepartmentName(departmentDTO.getDepartmentName());

        Department update = departmentRepo.save(department);
        return  mapperConfig.toDepartmentDTO(update);

    }

    @Override
    public void delete(Long departmentId) {
        if (!departmentRepo.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + departmentId);
        }
        departmentRepo.deleteById(departmentId);

    }

    @Override
    public DepartmentDTO getDepartmentByName(String name) {
        Department department = (Department) departmentRepo.findByDepartmentName(name).orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return mapperConfig.toDepartmentDTO(department);
    }
}
