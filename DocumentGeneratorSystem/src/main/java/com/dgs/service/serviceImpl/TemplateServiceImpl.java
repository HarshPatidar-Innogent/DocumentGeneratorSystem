package com.dgs.service.serviceImpl;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Template;
import com.dgs.entity.User;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.AccessControlRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.ITemplateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<TemplateDTO> getAllTemplate(Long userId) {
        List<Template> templateList = templateRepo.findAll();
        List<TemplateDTO> templateDTOS = templateList.stream().map(MapperConfig::toTemplateDto).toList();
        return templateDTOS;
    }

    @Override
    public TemplateDTO getTemplateById(Long id) {
        Optional<Template> template = templateRepo.findById(id);
        if (template.isPresent()) {
//            TemplateDTO templateDTO = mapperConfig.toTemplateDto(template.get());
            TemplateDTO dto = MapperConfig.toTemplateDto(template.get());
            return dto;
        }
        throw new NullPointerException("Template do not exist");
    }

    @Override
    public TemplateDTO createTemplate(TemplateDTO templateDTO) {
        User existingUser = userRepo.findById(templateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Template template = MapperConfig.toTemplate(templateDTO, existingUser);
        Template savedTemplate = templateRepo.save(template);
        return MapperConfig.toTemplateDto(savedTemplate);
    }

    @Override
    public TemplateDTO updateTemplate(Long id) {
        return null;
    }

    @Override
    public String deleteTemplate(Long id) {
        Optional<Template> template = templateRepo.findById(id);
        if (template.isPresent()) {
            templateRepo.deleteById(id);
            return "Template Deleted";
        }
        return "Template not found";
    }

    @Override
    public TemplateDTO getTemplateDTOById(Long templateId, Long userId) throws AccessDeniedException {
        User user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        Long departmentId = user.getDepartment().getDepartmentId();
        Long designationId = user.getDesignation().getDesignationId();

        boolean hasAccess = accessControlRepo.existsByTemplate_TemplateIdAndDepartment_DepartmentIdAndDesignation_DesignationId(templateId, departmentId, designationId);

        if (hasAccess) {
            Template template = templateRepo.findById(templateId).orElseThrow(() -> new EntityNotFoundException("Template Not Found"));
            return mapperConfig.toTemplateDto(template);
        } else {
            throw new AccessDeniedException("User does not have Access to this Document");
        }
    }

    @Override
    public void deleteTemplateById(Long id) {
        templateRepo.deleteById(id);
    }

}
