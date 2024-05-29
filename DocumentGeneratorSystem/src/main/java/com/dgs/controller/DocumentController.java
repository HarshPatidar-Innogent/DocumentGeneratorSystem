package com.dgs.controller;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.service.iService.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.awt.*;
import java.util.Map;

@RestController
@RequestMapping("/document")
@CrossOrigin
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @PostMapping(value = "/add/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String uploadDocument(@RequestParam Map<String, String> dynamicData, @PathVariable("id") Long templateId){
//        System.out.println(templateId);
        String document =  documentService.createDocument(dynamicData, templateId);
//        System.out.println(document);
        return document;
    }
}
