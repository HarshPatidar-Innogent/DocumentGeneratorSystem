package com.dgs.service.serviceImpl;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Placeholder;
import com.dgs.entity.Template;
import com.dgs.entity.User;
import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.AccessControlRepo;
import com.dgs.repository.PlaceholderRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.ITemplateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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


            Template template = templateRepo.findById(templateId).orElseThrow(() -> new EntityNotFoundException("Template Not Found"));
            return mapperConfig.toTemplateDto(template);

    }

    @Override
    public void deleteTemplateById(Long id) {
        templateRepo.deleteById(id);
    }

    @Override
    public TemplateDTO updateTemplate(TemplateDTO templateDTO, Long templateId) {
        Template existingTemplate = templateRepo.findById(templateId).orElseThrow(()->{throw new TemplateNotFoundException("Template Not found");});
        User existingUser = userRepo.findById(templateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //existing placeholders
//        List<Placeholder> existingPlaceholders = existingTemplate.getPlaceholderList();

        //all placeholder including new and old
        List<Placeholder> newPlaceholders = templateDTO.getPlaceholderDTOS().stream()
                .map((placeholder) -> mapperConfig.toPlaceholder(placeholder, existingTemplate))
                .collect(Collectors.toList());

        //filter out placeholders that already exists
//        List<Placeholder> placeholderToAdd = newPlaceholders.stream()
//                        .filter(newPlaceholder->
//                            existingPlaceholders.stream()
//                                    .noneMatch(existingPlaceholder->
//                                            existingPlaceholder.getPlaceholderName().equals(newPlaceholder.getPlaceholderName()) &&
//                                            existingPlaceholder.getPlaceholderType().equals(newPlaceholder.getPlaceholderType()))
//                        ).collect(Collectors.toList());

//        existingPlaceholders.addAll(placeholderToAdd);

        //delete existing placeholders of template by templateId
        placeholderRepo.deleteAllByTemplateId(existingTemplate.getTemplateId());



        existingTemplate.setTemplateFormat(templateDTO.getTemplateFormat());
        existingTemplate.setTemplateBody(templateDTO.getTemplateBody());
        existingTemplate.setUser(existingUser);
        existingTemplate.setTemplateName(templateDTO.getTemplateName());
        existingTemplate.setPlaceholderList(newPlaceholders);

        System.out.println("Existing Template Before Save: " + existingTemplate); // Debug


        Template save = templateRepo.save(existingTemplate);
        if(save==null){
            throw new RuntimeException("Template not updated");
        }
        return MapperConfig.toTemplateDto(save);
    }

    @Override
    public Integer countTemplate(Long userId) {
        return templateRepo.countTemplate(userId);
    }

}
