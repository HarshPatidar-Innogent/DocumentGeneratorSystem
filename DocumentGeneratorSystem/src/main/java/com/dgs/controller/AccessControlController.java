package com.dgs.controller;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.TemplateDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.service.iService.IAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessControl")
@CrossOrigin
public class AccessControlController {

    @Autowired
    private IAccessControlService accessControlService;

    @PostMapping("/addAccess")
    public ResponseEntity<?> addAccess(@RequestBody AccessControlDTO accessControlDTO) {
        AccessControlDTO controlDTO = accessControlService.addAccess(accessControlDTO);
        return ResponseEntity.ok(controlDTO);
    }

    @GetMapping("/template/access/user/{templateId}")
    public ResponseEntity<?> getAllAccessOfTemplate(@PathVariable Long templateId) {
        List<UserDTO> allAccessOfTemplate = accessControlService.getAllAccessOfTemplate(templateId);
        return ResponseEntity.ok(allAccessOfTemplate);

    }

    @GetMapping("/template/access/{templateId}")
    public ResponseEntity<?> getAllAccessDetails(@PathVariable Long templateId) {
        List<AccessControlDTO> accessControlDTOS = accessControlService.getAllAccessDetails(templateId);
        return ResponseEntity.ok(accessControlDTOS);

    }

    @DeleteMapping("/delete/access/{accessId}")
    public void deleteAccess(@PathVariable Long accessId) {
        accessControlService.deleteAccessById(accessId);

    }

    @GetMapping("/access-template/{userId}")
    public ResponseEntity<List<TemplateDTO>> getAccessTemplateOfUser(@PathVariable Long userId) {
        List<TemplateDTO> accessTemplateOfUser = accessControlService.getAccessTemplateOfUser(userId);
        return ResponseEntity.ok(accessTemplateOfUser);

    }

    @GetMapping("/access/{userId}")
    public ResponseEntity<List<AccessControlDTO>> getAccess(@PathVariable Long userId) {
        List<AccessControlDTO> accessControlDTOS = accessControlService.getAccessOfUser(userId);
        return ResponseEntity.ok(accessControlDTOS);

    }

    @GetMapping("/countAccessTemplate/{id}")
    public ResponseEntity<?> countAccessTemplate(@PathVariable Long id) {
        Integer countAccessTemplate = accessControlService.countAccessTemplate(id);
        return ResponseEntity.status(HttpStatus.OK).body(countAccessTemplate);
    }

    @GetMapping("/all/access/{userId}/templateId")
    public ResponseEntity<List<Long>> getAccessTemplateIdByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accessControlService.getAccessTemplateIdByUserId(userId));
    }
}
