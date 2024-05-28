package com.dgs.service.iService;

import com.dgs.DTO.AccessControlDTO;

public interface IAccessControlService {
    public AccessControlDTO addAccessControl(Long documentId,Long departmentId,Long designationId);
}
