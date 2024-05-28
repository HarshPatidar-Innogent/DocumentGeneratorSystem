package com.dgs.controller;

import com.dgs.DTO.TemplateDTO;
import com.dgs.service.iService.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

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

    @GetMapping("/getTemplate")
    public ResponseEntity<?> getTempById(@RequestParam("templateId") Long templateId,@RequestParam("userId") Long userId) throws AccessDeniedException {
        TemplateDTO templateDTO = templateService.getTemplateDTOById(templateId,userId);
        if(templateDTO!=null){
            return ResponseEntity.status(HttpStatus.OK).body(templateDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template Not Found");
        }
    }
}
