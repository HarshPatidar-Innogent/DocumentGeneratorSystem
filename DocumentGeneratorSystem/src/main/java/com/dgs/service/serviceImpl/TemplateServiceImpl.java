package com.dgs.service.serviceImpl;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Placeholder;
import com.dgs.entity.Template;
import com.dgs.entity.User;
import com.dgs.exception.CustomException.ResourceNotFoundException;
import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.exception.CustomException.UserNotFoundException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.AccessControlRepo;
import com.dgs.repository.PlaceholderRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.ITemplateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationServiceNotRegisteredException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Autowired
    private AccessControlRepo accessControlRepo;

    @Autowired
    private PlaceholderRepo placeholderRepo;

    @Override
    public List<TemplateDTO> getAllTemplate(Long userId) {
        List<Template> templateList = templateRepo.getAllTemplatesOfUser(userId);
        if(templateList.isEmpty()){
            throw new ResourceNotFoundException("Templates Not Found", HttpStatus.NOT_FOUND);
        }
        List<TemplateDTO> templateDTOS = templateList.stream().map(MapperConfig::toTemplateDto).toList();
        return templateDTOS;
    }

    @Override
    public TemplateDTO getTemplateById(Long id) {
        Optional<Template> template = templateRepo.findById(id);
        if (template.isEmpty()) {
            throw new TemplateNotFoundException("Template not Found",HttpStatus.NOT_FOUND);
        }
        TemplateDTO dto = MapperConfig.toTemplateDto(template.get());
        return dto;
    }

    @Override
    public TemplateDTO createTemplate(TemplateDTO templateDTO) {
        User existingUser = getUser(templateDTO.getUserId());
        Template template = MapperConfig.toTemplate(templateDTO, existingUser);
        Template savedTemplate = templateRepo.save(template);
        return MapperConfig.toTemplateDto(savedTemplate);
    }

    @Override
    public TemplateDTO getTemplateDTOById(Long templateId, Long userId) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        User user = getUser(userId);

        Template template = templateRepo.findById(templateId).orElseThrow(() -> new EntityNotFoundException("Template Not Found"));
        return mapperConfig.toTemplateDto(template);

    }

    @Override
    public void deleteTemplateById(Long id) {
        templateRepo.deleteById(id);
    }

    @Override
    public TemplateDTO updateTemplate(TemplateDTO templateDTO, Long templateId) {
        Template existingTemplate = getTemplate(templateId);
        User existingUser = getUser(templateDTO.getUserId());
        //existing placeholders
//        List<Placeholder> existingPlaceholders = existingTemplate.getPlaceholderList();

        //all placeholder including new and old
        List<Placeholder> newPlaceholders = templateDTO.getPlaceholderDTOS().stream()
                .map((placeholder) -> mapperConfig.toPlaceholder(placeholder, existingTemplate))
                .collect(Collectors.toList());

        //delete existing placeholders of template by templateId
        placeholderRepo.deleteAllByTemplateId(existingTemplate.getTemplateId());

        existingTemplate.setTemplateFormat(templateDTO.getTemplateFormat());
        existingTemplate.setTemplateBody(templateDTO.getTemplateBody());
        existingTemplate.setUser(existingUser);
        existingTemplate.setTemplateName(templateDTO.getTemplateName());
        existingTemplate.setPlaceholderList(newPlaceholders);
        Template save = templateRepo.save(existingTemplate);
        return MapperConfig.toTemplateDto(save);
    }

    @Override
    public Integer countTemplate(Long userId) {
        return templateRepo.countTemplate(userId);
    }

    private User getUser(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        return userOptional.get();
    }

    private Template getTemplate(Long templateId){
        Optional<Template> templateOptional = templateRepo.findById(templateId);
        if(templateOptional.isEmpty()){
            throw new TemplateNotFoundException("Template Not found", HttpStatus.NOT_FOUND);
        }
        return templateOptional.get();
     }

}
