package com.dgs.DTO;

import com.dgs.enums.ActionPerformed;
import com.dgs.enums.SignatureType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class AuditTrailDTO {
    private Long auditTrailId;
    private ActionPerformed actionPerformed;
    private String details;
    private LocalDateTime timestamp;
    private String ipAddress;
    private Long document;
    private Long user;
}
