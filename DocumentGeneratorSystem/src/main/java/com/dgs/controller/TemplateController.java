package com.dgs.controller;

import com.dgs.DTO.TemplateDTO;
import com.dgs.service.iService.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    private ITemplateService templateService;

    @PostMapping("/create")
    public TemplateDTO createTemplate(@RequestBody TemplateDTO templateDTO){
        return templateService.createTemplate(templateDTO);
    }

    @GetMapping("/get/{id}")
    public TemplateDTO getTemplateById(@PathVariable Long id){
        return templateService.getTemplateById(id);
    }
}
