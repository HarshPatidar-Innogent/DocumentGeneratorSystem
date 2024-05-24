package com.dgs.service.serviceImpl;

import com.dgs.DTO.PlaceholderDTO;
import com.dgs.entity.Placeholder;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.PlaceholderRepo;
import com.dgs.service.iService.IPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceholderServiceImpl implements IPlaceholderService {

    @Autowired
    public PlaceholderRepo placeholderRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public List<PlaceholderDTO> getAllPlaceholder() {
        List<Placeholder> placeholderList = placeholderRepo.findAll();
        List<PlaceholderDTO> placeholderDTOS = placeholderList.stream().map(mapperConfig::toPlaceholderDto).toList();
        return placeholderDTOS;
    }

    @Override
    public List<PlaceholderDTO> getAllPlaceholderOfTemplate(Long templateId) {
        List<Placeholder> placeholderList = placeholderRepo.getAllPlaceholderByTemplateId(templateId);
        List<PlaceholderDTO> placeholderDTOS = placeholderList.stream().map(mapperConfig::toPlaceholderDto).toList();
        return placeholderDTOS;
    }

    @Override
    public List<PlaceholderDTO> saveAllPlaceholderOfTemplate(List<PlaceholderDTO> placeholderDTOS) {
        List<Placeholder> placeholderList = placeholderDTOS.stream().map(mapperConfig::toPlaceholder).toList();
        List<Placeholder> save = placeholderRepo.saveAll(placeholderList);
        List<PlaceholderDTO> placeholderDTOS1 = save.stream().map(mapperConfig::toPlaceholderDto).toList();
        return placeholderDTOS1;
    }
}
