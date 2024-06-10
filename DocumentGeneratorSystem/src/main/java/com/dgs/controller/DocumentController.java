package com.dgs.controller;

import com.dgs.DTO.DocumentDTO;
import com.dgs.exception.CustomException.DocumentNotFoundException;
import com.dgs.service.iService.IDocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/document")
@CrossOrigin
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @GetMapping("/get-documents/{userId}")
    public ResponseEntity<List<DocumentDTO>> getDocumentByUserId(@PathVariable Long userId) {
        log.info("Get All Documents of users");
        List<DocumentDTO> documentDTOList = documentService.getAllDocumentOfUser(userId);
       return ResponseEntity.ok(documentDTOList);
    }

    @PostMapping(value = "/populate/{id}")
    public String populateDoc(@RequestParam Map<String, String> dynamicData, @PathVariable("id") Long templateId) {
        log.info("Document Populated");
        String document = documentService.populateDocument(dynamicData, templateId);
        System.out.println(document);
        return document;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> createDocument(@RequestBody DocumentDTO documentDTO) {
        DocumentDTO documentDTO1 = documentService.createDocument(documentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentDTO1);
    }

    @GetMapping("/get-document/{id}")
    public ResponseEntity<DocumentDTO> getDocumentForSign(@PathVariable Long id) {
        DocumentDTO document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    @PutMapping("/sign-submit/{id}")
    public ResponseEntity<?> submitSignedDocument(@RequestBody String documentBody, @PathVariable Long id) {
        try {
            documentService.submitSignature(documentBody, id);
            return ResponseEntity.ok("Document Signed");
        } catch (Exception e) {
            throw new DocumentNotFoundException("Document Not found");
        }
    }

    @DeleteMapping("/delete-doc/{id}")
    public void deleteDocumentById(@PathVariable Long id){
        documentService.deleteDocument(id);
    }

}
