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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
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

    @Override
    public boolean hasAccess(Long templateId, Long departmentId, Long designationId, DesignationPermission requiredPermission) {
        Designation designation = designationRepo.findById(designationId).get();
        if(designation!=null && designation.getPermission()==requiredPermission){
           return  accessControlRepo.existsByTemplate_TemplateIdAndDepartment_DepartmentIdAndDesignation_DesignationId(templateId,departmentId,designationId);
        }
        return false;
    }

    @Override
    public List<AccessControlDTO> findAllByTemplateId(Long templateId) {
         List<AccessControl> accessControlList = accessControlRepo.findAllByTemplateId(templateId);
         List<AccessControlDTO> accessControlDTOS = accessControlList.stream().map(mapperConfig::toAccessControlDTO).toList();
         return accessControlDTOS;
    }

    @Override
    public String deleteAccessControl(Long accessControlId) {
        Optional<AccessControl> accessControl = accessControlRepo.findById(accessControlId);
        if(accessControl.isPresent()){
              accessControlRepo.deleteById(accessControlId);
              return "Access Control is Successfully deleted";
        }
        else{
            return "Access Control Not Found";
        }
    }


}
