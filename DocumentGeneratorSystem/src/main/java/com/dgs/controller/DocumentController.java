package com.dgs.controller;

import com.dgs.DTO.DocumentDTO;
import com.dgs.exception.CustomException.DocumentCreationException;
import com.dgs.exception.CustomException.DocumentNotFoundException;
import com.dgs.exception.CustomException.DocumentPopulationException;
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
        try{
            List<DocumentDTO> documentDTOList = documentService.getAllDocumentOfUser(userId);
            return ResponseEntity.ok(documentDTOList);
        }catch (Exception e){
            throw new DocumentNotFoundException("Error Occurred in Fetching the documents");
        }
    }

    @PostMapping(value = "/populate/{id}")
    public String populateDoc(@RequestParam Map<String, String> dynamicData, @PathVariable("id") Long templateId) {
        log.info("Document Populated");
        try{
            String document = documentService.populateDocument(dynamicData, templateId);
            System.out.println(document);
            return document;
        }catch (Exception e){
            throw new DocumentPopulationException("Exception Occurred in Populating the Document");
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> createDocument(@RequestBody DocumentDTO documentDTO) {
        log.info("Document Creation");
        try{
            DocumentDTO documentDTO1 = documentService.createDocument(documentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(documentDTO1);
        }catch (Exception e){
            throw new DocumentCreationException("Document Not Crated");
        }
    }

    @GetMapping("/get-document/{id}")
    public ResponseEntity<DocumentDTO> getDocumentForSign(@PathVariable Long id) {
        try{
            DocumentDTO document = documentService.getDocumentById(id);
            return ResponseEntity.ok(document);
        }catch (Exception e){
            throw new DocumentNotFoundException("Document Not found for getting an document for Signature");
        }
    }

    @GetMapping("/getDocument/{id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long id){
        try{
            DocumentDTO documentDTO = documentService.getDocumentById(id);
            return ResponseEntity.ok(documentDTO);
        }catch (Exception e){
            throw new DocumentNotFoundException("Document not found with id "+id);
        }
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
        try{
            documentService.deleteDocument(id);
        }catch (Exception e){
            throw new DocumentNotFoundException("Document not found for delete");
        }
    }

    @GetMapping("/countDocument/{id}")
    public ResponseEntity<?> countDepartment(@PathVariable Long id){
        Integer count = documentService.countDocument(id);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }



}
