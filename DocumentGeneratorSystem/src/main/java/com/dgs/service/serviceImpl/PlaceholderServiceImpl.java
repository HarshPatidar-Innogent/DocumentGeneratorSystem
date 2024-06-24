package com.dgs.service.serviceImpl;

import com.dgs.DTO.PlaceholderDTO;
import com.dgs.entity.Placeholder;
import com.dgs.entity.Template;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.PlaceholderRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.service.iService.IPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceholderServiceImpl implements IPlaceholderService {

    @Autowired
    public PlaceholderRepo placeholderRepo;

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    private MapperConfig mapperConfig;


    @Override
    public List<PlaceholderDTO> getAllPlaceholderOfTemplate(Long templateId) {
        List<Placeholder> placeholderList = placeholderRepo.getAllPlaceholderByTemplateId(templateId);
        List<PlaceholderDTO> placeholderDTOS = placeholderList.stream().map(MapperConfig::toPlaceholderDto).toList();
        return placeholderDTOS;
    }

}
