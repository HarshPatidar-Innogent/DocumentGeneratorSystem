package com.dgs.DTO;

import lombok.Data;

@Data
public class PlaceholderDTO {
    private Long placeholderId;

    private String placeholderName;

    private String placeholderType;

    private Long template;
}
