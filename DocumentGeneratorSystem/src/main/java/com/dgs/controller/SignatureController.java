package com.dgs.controller;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import com.dgs.enums.SignatureType;
import com.dgs.service.iService.ISignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/signature")
public class SignatureController {

    @Autowired
    private ISignatureService signService;

    @PostMapping("/addSignature")
    public ResponseEntity<?> addSignature(@RequestParam("signatureData")MultipartFile file, @RequestParam("recipientEmail") String recipientEmail, @RequestParam("signatureType")SignatureType signatureType) throws IOException {
        SignatureDTO signatureDTO = this.signService.addSignature(file,recipientEmail,signatureType);
        return ResponseEntity.status(HttpStatus.OK).body(signatureDTO);
    }
}
