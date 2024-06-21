package com.dgs.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AccessControlDTO {
    private Long accessControlId;
    private Long template;
    private String email;
    private String templateAccess;
    private Long ownerId;
}
