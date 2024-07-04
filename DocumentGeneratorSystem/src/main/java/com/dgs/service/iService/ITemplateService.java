package com.dgs.service.iService;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Template;
import com.dgs.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ITemplateService {
    public List<TemplateDTO> getAllTemplate(Long userId);

    public TemplateDTO getTemplateById(Long id);

    public TemplateDTO createTemplate(TemplateDTO templateDTO);

    public TemplateDTO getTemplateDTOById(Long templateId,Long userId) throws AccessDeniedException;

    public void deleteTemplateById(Long id);

    public TemplateDTO updateTemplate(TemplateDTO templateDTO, Long templateId);

    public Integer countTemplate(Long userId);
}
