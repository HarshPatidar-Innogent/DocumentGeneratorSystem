package com.dgs.controller;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.enums.DesignationPermission;
import com.dgs.service.iService.IAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccessControlController {

    @Autowired
    IAccessControlService accessControlService;

    @PostMapping("/addAccessControl")
    public ResponseEntity<?> addAccessControl(@RequestParam Long templateId , @RequestParam Long departmentId , @RequestParam Long designationId){
        AccessControlDTO accessControlDTO = accessControlService.addAccessControl(templateId, departmentId, designationId);
        return ResponseEntity.status(HttpStatus.OK).body(accessControlDTO);
    }

    @GetMapping("/checkAccess")
        public ResponseEntity<Boolean> checkAccess(
            @RequestParam Long templateId,
            @RequestParam Long departmentId,
            @RequestParam Long designationId,
            @RequestParam DesignationPermission requiredPermission) {
        boolean hasAccess = accessControlService.hasAccess(templateId, departmentId, designationId, requiredPermission);
        return ResponseEntity.ok().body(hasAccess);
    }

    @GetMapping("/getAllTemplateAccess/{id}")
    public ResponseEntity<?> getAllAccessByTemplateId(@PathVariable Long id){
        List<AccessControlDTO> accessControlDTOList =  accessControlService.findAllByTemplateId(id);
        if(accessControlDTOList!=null){
            return ResponseEntity.status(HttpStatus.OK).body(accessControlDTOList);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Templates Not Found");
        }
    }
}
