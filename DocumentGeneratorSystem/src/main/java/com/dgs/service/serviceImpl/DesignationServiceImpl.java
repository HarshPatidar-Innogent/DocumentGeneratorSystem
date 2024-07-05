package com.dgs.service.serviceImpl;

import com.dgs.DTO.DesignationDTO;
import com.dgs.entity.Designation;
import com.dgs.exception.CustomException.DesignationException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DesignationRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IDesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return mapperConfig.toDesignationDTO(savedDesignation);
    }

    @Override
    public List<DesignationDTO> getAll() {
        List<Designation> designations = designationRepo.findAll();
        if(designations.isEmpty()){
            throw new DesignationException("Designations Not Found", HttpStatus.NOT_FOUND);
        }
        return designations.stream()
                    .map(mapperConfig::toDesignationDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public DesignationDTO update(Long designationId, DesignationDTO designationDTO){
        Optional<Designation> optionalDesignation = designationRepo.findById(designationId);
        if(optionalDesignation.isEmpty()){
            throw new DesignationException("Designation Not Found",HttpStatus.NOT_FOUND);
        }
        Designation designation = optionalDesignation.get();
        designation.setDesignationName(designationDTO.getDesignationName());
        Designation update = designationRepo.save(designation);
        return  mapperConfig.toDesignationDTO(update);
    }

    @Override
    public DesignationDTO getDesignationById(Long id) {
        Optional<Designation> designationOptional = designationRepo.findById(id);
        if (designationOptional.isEmpty()) {
            throw new DesignationException("Designation not found with id: " + id,HttpStatus.NOT_FOUND);
        }
        return mapperConfig.toDesignationDTO(designationOptional.get());
    }

    @Override
    public DesignationDTO getDesignationByName(String name) {
        Optional<Designation> optionalDesignation = designationRepo.findByDesignationName(name);
        if(optionalDesignation.isEmpty()){
            throw new DesignationException("Error in Fetching Designation By Name",HttpStatus.NOT_FOUND);
        }
        Designation designation = optionalDesignation.get();
        return mapperConfig.toDesignationDTO(designation);
    }

    @Override
    public Boolean delete(Long designationId) {
        if (!designationRepo.existsById(designationId)) {
            throw new DesignationException("Designation not found with id: " + designationId,HttpStatus.NOT_FOUND);
        }
        if (userRepo.existsByDesignation(designationRepo.findById(designationId).get())) {
            return false;
        }
        designationRepo.deleteByDesignationId(designationId);
        return true;
    }
}

