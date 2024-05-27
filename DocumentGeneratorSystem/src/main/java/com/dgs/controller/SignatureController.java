package com.dgs.controller;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import com.dgs.enums.SignatureType;
import com.dgs.repository.SignatureRepo;
import com.dgs.service.iService.ISignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/signature")
public class SignatureController {

    @Autowired
    ISignatureService signatureService;

    @PostMapping("/addSignature")
    public ResponseEntity<?> addSignature(@RequestParam("signatureData")MultipartFile file, @RequestParam("recipientEmail") String recipientEmail, @RequestParam("signatureType")SignatureType signatureType) throws IOException {
        SignatureDTO signatureDTO = new SignatureDTO();
        signatureDTO.setSignatureType(signatureType);
        signatureDTO.setRecipientEmail(recipientEmail);

        SignatureDTO sign = this.signatureService.addSignature(file,signatureDTO);
        return ResponseEntity.status(HttpStatus.OK).body(sign);
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
    public ResponseEntity<String> deleteSignature(@PathVariable Long id){
        String signature = signatureService.deleteSignature(id);
        if (signature !=null){
            return ResponseEntity.ok().body(signature);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/getSignatureById/{id}")
    public ResponseEntity<?> getSignatureById(@PathVariable Long id){
        SignatureDTO signatureDTO = signatureService.getSignatureById(id);
        if(signatureDTO!=null){
            return ResponseEntity.ok().body(signatureDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Signature not found");
        }
    }

    @PutMapping("/updateSignature/{id}")
    public ResponseEntity<?> updateSign(@PathVariable Long id, @RequestParam("signatureData")MultipartFile file, @RequestParam("recipientEmail") String recipientEmail, @RequestParam("signatureType")SignatureType signatureType) throws IOException{
        SignatureDTO signatureDTO = new SignatureDTO();
        signatureDTO.setSignatureType(signatureType);
        signatureDTO.setRecipientEmail(recipientEmail);
        SignatureDTO signatureDTO1 = signatureService.updateSignature(id,file,signatureDTO);
        return ResponseEntity.status(HttpStatus.OK).body(signatureDTO1);
    }



}
