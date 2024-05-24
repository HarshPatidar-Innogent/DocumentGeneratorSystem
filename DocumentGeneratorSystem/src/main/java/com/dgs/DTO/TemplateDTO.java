package com.dgs.DTO;

import com.dgs.enums.TemplateFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TemplateDTO {
    private Long templateId;

    private String templateName;

    private TemplateFormat templateFormat;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private UserDTO user;
}
