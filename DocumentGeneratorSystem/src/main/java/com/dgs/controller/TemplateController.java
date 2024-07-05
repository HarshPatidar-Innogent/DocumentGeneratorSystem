package com.dgs.controller;

import com.dgs.DTO.TemplateDTO;
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

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllTemplate(@PathVariable Long userId) {
//        System.out.println(userId);
        List<TemplateDTO> templateDTOS = templateService.getAllTemplate(userId);
        return ResponseEntity.status(HttpStatus.OK).body(templateDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO templateDTO) {
        TemplateDTO template = templateService.createTemplate(templateDTO);
        return ResponseEntity.ok(template);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable Long id) {
        TemplateDTO dto = templateService.getTemplateById(id);
        return ResponseEntity.ok(dto);
    }

//    @GetMapping("/getTemplate")
//    public ResponseEntity<?> getTempById(@RequestParam("templateId") Long templateId, @RequestParam("userId") Long userId) {
//        TemplateDTO templateDTO = templateService.getTemplateDTOById(templateId, userId);
//        return ResponseEntity.ok(templateDTO);
//    }

    @DeleteMapping("/deleteTemplate/{id}")
    public ResponseEntity<?> deleteTemplateById(@PathVariable Long id) {
        templateService.deleteTemplateById(id);
        return ResponseEntity.ok("Templated Deleted");
    }

    @PutMapping("/update-template/{templateId}")
    public ResponseEntity<TemplateDTO> updateTemplate(@RequestBody TemplateDTO templateDTO, @PathVariable Long templateId) {
        TemplateDTO dto = templateService.updateTemplate(templateDTO, templateId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/countTemplate/{id}")
    public ResponseEntity<?> countTemplate(@PathVariable Long id) {
        Integer count = templateService.countTemplate(id);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}
