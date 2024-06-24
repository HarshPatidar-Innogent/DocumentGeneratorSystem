package com.dgs.service.iService;

import com.dgs.DTO.DesignationDTO;

import java.util.List;
import java.util.Map;

public interface IDesignationService {
    DesignationDTO createDesignation(DesignationDTO designationDTO);

    List<DesignationDTO> getAll();

    DesignationDTO update(Long id, DesignationDTO designationDTO);


    void delete(Long designationId);

    DesignationDTO getDesignationByName(String name);

    DesignationDTO getDesignationById(Long id);
}
