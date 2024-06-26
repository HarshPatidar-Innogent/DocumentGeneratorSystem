package com.dgs.service.iService;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.TemplateDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.entity.AccessControl;
import com.dgs.entity.User;
import com.dgs.enums.DesignationPermission;

import java.util.List;

public interface IAccessControlService {
    public AccessControlDTO addAccess(AccessControlDTO accessControlDTO);

    List<UserDTO> getAllAccessOfTemplate(Long templateId);

    List<AccessControlDTO> getAllAccessDetails(Long templateId);

    void deleteAccessById(Long accessId);

    List<TemplateDTO> getAccessTemplateOfUser(Long userId);

    List<AccessControlDTO> getAccessOfUser(Long userId);
}
