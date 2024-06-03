package com.dgs.controller;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.service.iService.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.events.Event;

import java.awt.*;
import java.util.Map;

@RestController
@RequestMapping("/document")
@CrossOrigin
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

//    @PostMapping(value = "/populate/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public String populateDoc(@RequestParam Map<String, String> dynamicData, @PathVariable("id") Long templateId){
//        String document =  documentService.populateDocument(dynamicData, templateId);
//        return document;
//    }

    @PostMapping(value = "/populate/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String populateDoc(@RequestParam Map<String, MultipartFile> fileData,
                              @RequestParam Map<String, String> textData,
                              @PathVariable("id") Long templateId) {
        String document = documentService.populateDocument(fileData, textData, templateId);
        return document;
    }



    @PostMapping(value = "/add")
    public ResponseEntity<?> createDocument(@RequestBody DocumentDTO documentDTO){
        DocumentDTO documentDTO1 = documentService.createDocument(documentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentDTO1);
    }
}
