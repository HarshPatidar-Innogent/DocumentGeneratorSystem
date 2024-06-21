package com.dgs.service.iService;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.entity.AccessControl;
import com.dgs.enums.DesignationPermission;

import java.util.List;

public interface IAccessControlService {
    public String addAccess(AccessControlDTO accessControlDTO);
}
