package com.dgs.service.iService;

import com.dgs.DTO.PlaceholderDTO;

import java.util.List;

public interface IPlaceholderService {
    public List<PlaceholderDTO> getAllPlaceholder();

    public List<PlaceholderDTO> getAllPlaceholderOfTemplate(Long templateId);

    public List<PlaceholderDTO> saveAllPlaceholderOfTemplate(List<PlaceholderDTO> placeholderDTOS);

}
