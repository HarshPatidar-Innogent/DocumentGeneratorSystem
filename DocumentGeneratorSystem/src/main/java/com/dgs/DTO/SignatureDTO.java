package com.dgs.DTO;

import com.dgs.enums.SignatureType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class SignatureDTO {
    private Long signatureId;
    private SignatureType signatureType;
    private byte[] signatureData;
    private LocalDateTime signedAt;
    private String recipientEmail;
    private Long document;
    private Long user;



}
