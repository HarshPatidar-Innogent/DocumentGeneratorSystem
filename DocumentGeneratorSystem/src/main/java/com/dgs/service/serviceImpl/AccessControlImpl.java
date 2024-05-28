package com.dgs.service.serviceImpl;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.*;
import com.dgs.enums.DesignationPermission;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.*;
import com.dgs.service.iService.IAccessControlService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessControlImpl implements IAccessControlService {
    @Autowired
    private AccessControlRepo accessControlRepo;

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public AccessControlDTO addAccessControl(Long templateId, Long departmentId, Long designationId) {
        Template template = templateRepo.findById(templateId).orElseThrow(()->new EntityNotFoundException("Template Not Found"));
        Department department = departmentRepo.findById(departmentId).orElseThrow(()->new EntityNotFoundException("Department Not Found"));
        Designation designation = designationRepo.findById(designationId).orElseThrow(()->new EntityNotFoundException("Designation Not Found"));

        AccessControl accessControl = new AccessControl();
        accessControl.setDepartment(department);
        accessControl.setDesignation(designation);
        accessControl.setTemplate(template);

        AccessControl accessControl1 =  accessControlRepo.save(accessControl);
        return mapperConfig.toAccessControlDTO(accessControl1);
    }

    public boolean hasAccess(Long templateId, Long departmentId, Long designationId, DesignationPermission requiredPermission) {
        Designation designation = designationRepo.findById(designationId).get();
        if(designation!=null && designation.getPermission()==requiredPermission){
            return  accessControlRepo.existsByTemplate_TemplateIdAndDepartment_DepartmentIdAndDesignation_DesignationId(templateId,departmentId,designationId);
        }
        return false;
    }


}
