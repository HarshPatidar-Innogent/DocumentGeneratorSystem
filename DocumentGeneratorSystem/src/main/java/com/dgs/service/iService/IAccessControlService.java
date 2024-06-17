package com.dgs.service.iService;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.entity.AccessControl;
import com.dgs.enums.DesignationPermission;

import java.util.List;

public interface IAccessControlService {
    public AccessControlDTO addAccessControl(Long documentId,Long departmentId,Long designationId);
    public boolean hasAccess(Long templateId, Long departmentId, Long designationId, DesignationPermission requiredPermission);
    List<AccessControlDTO> findAllByTemplateId(Long templateId);
    public String deleteAccessControl(Long accessControlId);

}
