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
        List<DocumentDTO> documentDTOList = documentService.getAllDocumentOfUser(userId);
        return ResponseEntity.ok(documentDTOList);
    }

    @PostMapping(value = "/populate/{id}")
    public ResponseEntity<String> populateDoc(@RequestParam Map<String, String> dynamicData, @PathVariable("id") Long templateId) {
        String document = documentService.populateDocument(dynamicData, templateId);
        return ResponseEntity.ok(document);
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

    @GetMapping("/getDocument/{id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long id) {
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        return ResponseEntity.ok(documentDTO);
    }

    @PutMapping("/sign-submit/{id}")
    public ResponseEntity<?> submitSignedDocument(@RequestBody String documentBody, @PathVariable Long id) {
        documentService.submitSignature(documentBody, id);
        return ResponseEntity.ok("Document Signed");
    }

    @DeleteMapping("/delete-doc/{id}")
    public void deleteDocumentById(@PathVariable Long id) {
        documentService.deleteDocument(id);
    }

    @GetMapping("/countDocument/{id}")
    public ResponseEntity<?> countDepartment(@PathVariable Long id) {
        Integer count = documentService.countDocument(id);
        return ResponseEntity.status(HttpStatus.OK).body(count);

    }
}
