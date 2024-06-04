package com.dgs.DTO;

import com.dgs.enums.DocumentStatus;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
public class DocumentDTO {
    private Long documentId;

    private String documentName;

    private String documentBody;

    private DocumentStatus status;

    private LocalDate createdAt;

    private Long templateId;

    private Long userId;

    private List<String> signatureEmails;
}
