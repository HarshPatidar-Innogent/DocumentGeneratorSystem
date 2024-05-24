package com.dgs.DTO;

import com.dgs.enums.SignatureType;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDateTime;

@Data
public class SignatureDTO {
    private Long signatureId;
    private SignatureType signatureType;
    private Blob signatureData;
    private LocalDateTime signedAt;
    private String recipientEmail;
    private Long document;
    private Long user;



}
