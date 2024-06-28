package com.dgs.service.serviceImpl;

import com.dgs.DTO.DepartmentDTO;
import com.dgs.DTO.DesignationDTO;
import com.dgs.entity.Department;
import com.dgs.entity.Designation;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DesignationRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IDesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DesignationServiceImpl implements IDesignationService {

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Autowired
    private UserRepo userRepo;

    @Override
    public DesignationDTO createDesignation(DesignationDTO designationDTO) {
        Designation designation = new Designation();
        designation.setDesignationName(designationDTO.getDesignationName());
        Designation savedDesignation = designationRepo.save(designation);
        System.out.println(savedDesignation);
        return mapperConfig.toDesignationDTO(savedDesignation);
    }

    @Override
    public List<DesignationDTO> getAll() {
        List<Designation> designations = designationRepo.findAll();
        return designations.stream()
                .map(mapperConfig::toDesignationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DesignationDTO update(Long designationId, DesignationDTO designationDTO) {
        Designation designation = designationRepo.findById(designationId)
                .orElseThrow(() -> new RuntimeException("Designation not found with id :" + designationId));

        designation.setDesignationName(designationDTO.getDesignationName());
        designation.setDescription(designationDTO.getDescription());
        designation.setPermission(designation.getPermission());

        Designation update = designationRepo.save(designation);
        return mapperConfig.toDesignationDTO(update);

    }

//    @Override
//    public void delete(Long designationId) {
//        if (!designationRepo.existsById(designationId)) {
//            throw new RuntimeException("Designation not found with id: " + designationId);
//        }
//        designationRepo.deleteById(designationId);
//    }

    @Override
    public DesignationDTO getDesignationByName(String name) {
        Designation designation = (Designation) designationRepo.findByDesignationName(name).orElseThrow(() -> new IllegalArgumentException("Designation not found"));
        return mapperConfig.toDesignationDTO(designation);
    }

    @Override
    public DesignationDTO getDesignationById(Long id) {
        Optional<Designation> designationOptional = designationRepo.findById(id);
        if (designationOptional.isPresent()) {
            return mapperConfig.toDesignationDTO(designationOptional.get());
        } else {
            throw new RuntimeException("designation not found with id: " + id);
        }
    }


    @Override
    public Boolean delete(Long designationId) {
        if (!designationRepo.existsById(designationId)) {
            throw new RuntimeException("Designation not found with id: " + designationId);
        }
        if (userRepo.existsByDesignation(designationRepo.findById(designationId).get())) {
            return false;
        }
        try {
            designationRepo.deleteByDesignationId(designationId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    }

