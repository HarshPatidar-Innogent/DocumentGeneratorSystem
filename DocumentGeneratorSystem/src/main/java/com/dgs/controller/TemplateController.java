package com.dgs.controller;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Template;
import com.dgs.exception.CustomException.TemplateException;
import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.service.iService.ITemplateService;
import com.dgs.service.iService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/template")
@CrossOrigin
public class TemplateController {
    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getAllTemplate(@PathVariable Long id) {
        try {
            List<TemplateDTO> templateDTOS = templateService.getAllTemplate(id);
            return ResponseEntity.status(HttpStatus.OK).body(templateDTOS);
        } catch (Exception e) {
            throw new TemplateException("Exception in getting all templates of user");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO templateDTO) {
        TemplateDTO template = templateService.createTemplate(templateDTO);
        try {
            return ResponseEntity.ok(template);
        } catch (Exception e) {
            throw new RuntimeException("Error Creating an Template");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable Long id) {
//        TemplateDTO dto = templateService.getTemplateById(id);
//        if(dto != null){
//            return ResponseEntity.ok(dto);
//        }else{
//            throw new TemplateNotFoundException("Template not found");
//        }
        try{
            TemplateDTO dto = templateService.getTemplateById(id);
            return ResponseEntity.ok(dto);
        }catch (Exception e){
            throw new TemplateNotFoundException("Template not found");
        }
    }

    @GetMapping("/getTemplate")
    public ResponseEntity<?> getTempById(@RequestParam("templateId") Long templateId, @RequestParam("userId") Long userId) throws AccessDeniedException {
//        TemplateDTO templateDTO = templateService.getTemplateDTOById(templateId, userId);
//        if (templateDTO != null) {
//            return ResponseEntity.ok(templateDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        try{
            TemplateDTO templateDTO = templateService.getTemplateDTOById(templateId, userId);
            return ResponseEntity.ok(templateDTO);
        }catch (Exception e) {
//            return ResponseEntity.notFound().build();
            throw new TemplateException("Template Not found");
        }
    }

    @DeleteMapping("/deleteTemplate/{id}")
    public ResponseEntity<?> deleteTemplateById(@PathVariable Long id) {
        if (templateService.getTemplateById(id) != null) {
            templateService.deleteTemplateById(id);
            return ResponseEntity.ok("Templated Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-template/{templateId}")
    public ResponseEntity<TemplateDTO> updateTemplate(@RequestBody TemplateDTO templateDTO, @PathVariable Long templateId) {
        TemplateDTO dto = templateService.updateTemplate(templateDTO, templateId);
        try {
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            throw new RuntimeException("Exception Updating an Template" + templateId);
        }
    }

    @GetMapping("/countTemplate/{id}")
    public ResponseEntity<?> countTemplate(@PathVariable Long id) {
        try{
            Integer count = templateService.countTemplate(id);
            return ResponseEntity.status(HttpStatus.OK).body(count);
        }catch (Exception e){
            throw new TemplateException("Template in counting templates");
        }
    }
}
