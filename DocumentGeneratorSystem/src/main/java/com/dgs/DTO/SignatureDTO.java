package com.dgs.DTO;

import com.dgs.enums.SignatureType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
public class SignatureDTO {
    private Long signatureId;
    private SignatureType signatureType;
    private byte[] signatureData;
    private LocalDateTime signedAt;
    private Long documentId;
    private Long userId;
    private String placeholder;
    private Boolean signed;
    private String recipientEmail;
}
