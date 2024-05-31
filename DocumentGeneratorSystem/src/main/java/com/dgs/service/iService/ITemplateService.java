package com.dgs.service.iService;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Template;
import com.dgs.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ITemplateService {
    public List<TemplateDTO> getAllTemplate();

    public TemplateDTO getTemplateById(Long id);

    public TemplateDTO createTemplate(TemplateDTO templateDTO);

    public TemplateDTO updateTemplate(Long id);

    public String deleteTemplate(Long id);

    public TemplateDTO getTemplateDTOById(Long templateId,Long userId) throws AccessDeniedException;
}
