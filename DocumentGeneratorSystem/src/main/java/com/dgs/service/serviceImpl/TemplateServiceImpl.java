package com.dgs.service.serviceImpl;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Template;
import com.dgs.entity.User;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.TemplateRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public List<TemplateDTO> getAllTemplate() {
        List<Template> templateList = templateRepo.findAll();
        List<TemplateDTO>  templateDTOS = templateList.stream().map(mapperConfig::toTemplateDto).toList();
        return templateDTOS;
    }

    @Override
    public TemplateDTO getTemplateById(Long id) {
        Optional<Template> template = templateRepo.findById(id);
        if(template.isPresent()){
            TemplateDTO templateDTO = mapperConfig.toTemplateDto(template.get());
            return templateDTO;
        }
        throw new NullPointerException("Template do not exist");
    }

    @Override
    public TemplateDTO createTemplate(TemplateDTO templateDTO) {
        Long userId = templateDTO.getUser();
        User existingUser = userRepo.findById(userId).get();
        Template template = mapperConfig.toTemplate(templateDTO);
        template.setUser(existingUser);
        return mapperConfig.toTemplateDto(templateRepo.save(template));

//        Template template = templateRepo.save(mapperConfig.toTemplate(templateDTO));
//        return mapperConfig.toTemplateDto(template);
    }

    @Override
    public TemplateDTO updateTemplate(Long id) {
        return null;
    }

    @Override
    public String deleteTemplate(Long id) {
        Optional<Template> template = templateRepo.findById(id);
        if(template.isPresent()){
            templateRepo.deleteById(id);
            return "Template Deleted";
        }
        return "Template not found";
    }
}
