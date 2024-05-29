package com.dgs.service.iService;

import com.dgs.DTO.PlaceholderDTO;

import java.util.List;

public interface IPlaceholderService {


    public List<PlaceholderDTO> getAllPlaceholderOfTemplate(Long templateId);

}
