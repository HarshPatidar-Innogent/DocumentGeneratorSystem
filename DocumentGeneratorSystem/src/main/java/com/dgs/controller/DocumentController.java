package com.dgs.controller;

import com.dgs.DTO.DocumentDTO;
import com.dgs.exception.CustomException.DocumentNotFoundException;
import com.dgs.service.iService.IDocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //@PreAuthorize("hasAnyRole('USER',ADMIN)")
    @GetMapping("/get-documents/{userId}")
    public ResponseEntity<List<DocumentDTO>> getDocumentByUserId(@PathVariable Long userId) {
        log.info("Get All Documents of users");
        List<DocumentDTO> documentDTOList = documentService.getAllDocumentOfUser(userId);
       return ResponseEntity.ok(documentDTOList);
    }
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(value = "/populate/{id}")
    public String populateDoc(@RequestParam Map<String, String> dynamicData, @PathVariable("id") Long templateId) {
        log.info("Document Populated");
        String document = documentService.populateDocument(dynamicData, templateId);
        System.out.println(document);
        return document;
    }
    //@PreAuthorize("hasRole('ADMIN','USER')")
    @PostMapping(value = "/save")
    public ResponseEntity<?> createDocument(@RequestBody DocumentDTO documentDTO) {
        DocumentDTO documentDTO1 = documentService.createDocument(documentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentDTO1);
    }
   // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/get-document/{id}")
    public ResponseEntity<DocumentDTO> getDocumentForSign(@PathVariable Long id) {
        DocumentDTO document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/getDocument/{id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long id){
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        return ResponseEntity.ok(documentDTO);
    }
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/sign-submit/{id}")
    public ResponseEntity<?> submitSignedDocument(@RequestBody String documentBody, @PathVariable Long id) {
        try {
            documentService.submitSignature(documentBody, id);
            return ResponseEntity.ok("Document Signed");
        } catch (Exception e) {
            throw new DocumentNotFoundException("Document Not found");
        }
    }
  //  @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-doc/{id}")
    public void deleteDocumentById(@PathVariable Long id){
        documentService.deleteDocument(id);
    }

}
