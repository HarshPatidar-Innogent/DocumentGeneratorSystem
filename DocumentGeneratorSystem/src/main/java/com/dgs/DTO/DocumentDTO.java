package com.dgs.DTO;

import com.dgs.enums.DocumentStatus;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
public class DocumentDTO {
    private Long documentId;

    private String documentName;

    private DocumentStatus status;

    private LocalDate createdAt;

    private TemplateDTO template;

    private UserDTO user;
}
