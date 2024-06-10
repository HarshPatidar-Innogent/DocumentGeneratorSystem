package com.dgs.controller;

import com.dgs.DTO.SignatureDTO;
import com.dgs.enums.SignatureType;
import com.dgs.service.iService.ISignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/signature")
@CrossOrigin
public class SignatureController {

    @Autowired
    ISignatureService signatureService;

    @PostMapping("/addSignature")
    public ResponseEntity<?> addSignature(@RequestParam("signatureData") MultipartFile[] files, @RequestParam("signatureType") SignatureType signatureType, @RequestParam Long documentId, @RequestParam("placeholder") String placeholder) throws IOException {
        List<SignatureDTO> signatureDTOS = new ArrayList<>();

        for (MultipartFile file : files) {
            SignatureDTO signatureDTO = new SignatureDTO();
            signatureDTO.setSignatureType(signatureType);
            signatureDTO.setDocumentId(documentId);
            signatureDTO.setPlaceholder(placeholder);

            SignatureDTO sign = this.signatureService.addSignature(file, signatureDTO);
            signatureDTOS.add(sign);
        }
        return ResponseEntity.status(HttpStatus.OK).body(signatureDTOS);
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        byte[] imageData = signatureService.getImageDataById(id);
        if (imageData != null) {
            return ResponseEntity.ok().body(imageData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSignature(@PathVariable Long id) {
        String signature = signatureService.deleteSignature(id);
        if (signature != null) {
            return ResponseEntity.ok().body(signature);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/getSignatureById/{id}")
    public ResponseEntity<?> getSignatureById(@PathVariable Long id) {
        SignatureDTO signatureDTO = signatureService.getSignatureById(id);
        if (signatureDTO != null) {
            return ResponseEntity.ok().body(signatureDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Signature not found");
        }
    }

    @PutMapping("/updateSignature/{id}")
    public ResponseEntity<?> updateSign(@PathVariable Long id, @RequestParam("signatureData") MultipartFile file, @RequestParam("recipientEmail") String recipientEmail, @RequestParam("signatureType") SignatureType signatureType) throws IOException {
        SignatureDTO signatureDTO = new SignatureDTO();
        signatureDTO.setSignatureType(signatureType);
        SignatureDTO signatureDTO1 = signatureService.updateSignature(id, file, signatureDTO);
        return ResponseEntity.status(HttpStatus.OK).body(signatureDTO1);
    }

    @PostMapping("/addSignatureDrawn")
    public ResponseEntity<?> addSignatureDrawn(@RequestParam("signatureData") MultipartFile file, @RequestParam("signatureType") SignatureType signatureType, @RequestParam Long documentId, @RequestParam("placeholder") String placeholder) throws IOException {
        SignatureDTO signatureDTO2 = new SignatureDTO();
        signatureDTO2.setSignatureType(signatureType);
        signatureDTO2.setDocumentId(documentId);
        signatureDTO2.setPlaceholder(placeholder);
        System.out.println(documentId);
        SignatureDTO signatureDTO = signatureService.addSignatureDrawn(file , signatureDTO2);
        return ResponseEntity.status(HttpStatus.OK).body(signatureDTO);
    }

    @PostMapping("/addSignatureElectronic")
    public ResponseEntity<?> addSignatureElectronic(@RequestBody SignatureDTO signatureDTO, @RequestParam("name") String Name) throws IOException {
        SignatureDTO signatureDTO1 = signatureService.addSignatureElectronic(signatureDTO,Name);
        return ResponseEntity.status(HttpStatus.OK).body(signatureDTO1);
    }

}
