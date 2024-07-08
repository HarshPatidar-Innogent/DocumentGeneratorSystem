package com.dgs.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AccessControlDTO {
    private Long accessControlId;
    private Long template;
    private Long userId;
    private String templateAccess;
    private Long ownerId;
    private String ownerName;
}
