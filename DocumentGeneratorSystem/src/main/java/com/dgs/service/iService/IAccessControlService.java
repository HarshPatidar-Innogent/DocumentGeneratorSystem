package com.dgs.service.iService;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.enums.DesignationPermission;

public interface IAccessControlService {
    public AccessControlDTO addAccessControl(Long documentId,Long departmentId,Long designationId);
    public boolean hasAccess(Long templateId, Long departmentId, Long designationId, DesignationPermission requiredPermission);
}
