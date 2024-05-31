package com.dgs.controller;

import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.User;
import com.dgs.exception.TemplateNotFoundException;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllTemplate(){
        List<TemplateDTO> templateDTOS = templateService.getAllTemplate();
        return ResponseEntity.status(HttpStatus.OK).body(templateDTOS);
    }

    @PostMapping("/create")
    public TemplateDTO createTemplate(@RequestBody TemplateDTO templateDTO){
        System.out.println(templateDTO.getUserId());
        return templateService.createTemplate(templateDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable Long id){
//        return templateService.getTemplateById(id);
        TemplateDTO dto = templateService.getTemplateById(id);
        if(dto != null){
            return ResponseEntity.ok(dto);
        }else{
            throw new TemplateNotFoundException("Resource not Found with id "+ id);
        }
    }

    @GetMapping("/getTemplate")
    public ResponseEntity<?> getTempById(@RequestParam("templateId") Long templateId,@RequestParam("userId") Long userId) throws AccessDeniedException {
        TemplateDTO templateDTO = templateService.getTemplateDTOById(templateId,userId);
        if(templateDTO!=null){
            return ResponseEntity.ok(templateDTO);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
