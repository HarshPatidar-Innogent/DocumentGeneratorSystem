package com.dgs.service.iService;

import com.dgs.DTO.DesignationDTO;

import java.util.List;
import java.util.Map;

public interface IDesignationService {
    DesignationDTO createDesignation(DesignationDTO designationDTO);

    List<DesignationDTO> getAll();

    DesignationDTO update(Long id, DesignationDTO designationDTO);

    DesignationDTO getDesignationByName(String name);
    DesignationDTO getDesignationById(Long id);

    Boolean delete(Long designationId);
}
