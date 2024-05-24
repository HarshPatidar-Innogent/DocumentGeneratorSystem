package com.dgs.DTO;

import com.dgs.entity.User;
import com.dgs.enums.TemplateFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class TemplateDTO {
    private Long templateId;

    private String templateName;

    private TemplateFormat templateFormat;

    private String templateBody;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long user;
}
