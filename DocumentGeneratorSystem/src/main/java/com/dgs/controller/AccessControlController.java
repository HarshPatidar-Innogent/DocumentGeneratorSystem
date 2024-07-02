package com.dgs.controller;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.TemplateDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.entity.AccessControl;
import com.dgs.entity.User;
import com.dgs.enums.DesignationPermission;
import com.dgs.exception.CustomException.AccessControlException;
import com.dgs.exception.CustomException.UserNotFoundException;
import com.dgs.service.iService.IAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accessControl")
@CrossOrigin
public class AccessControlController {

    @Autowired
    private IAccessControlService accessControlService;

    @PostMapping("/addAccess")
    public ResponseEntity<?> addAccess(@RequestBody AccessControlDTO accessControlDTO){
         AccessControlDTO accessControlDTO1 = accessControlService.addAccess(accessControlDTO);
         try{
             return ResponseEntity.ok(accessControlDTO);
         }catch (Exception e){
             throw new AccessControlException("Error in adding an access to Template "+ accessControlDTO.getTemplate());
//            return ResponseEntity.badRequest().build();
         }
    }

    @GetMapping("/template/access/user/{templateId}")
    public ResponseEntity<?> getAllAccessOfTemplate(@PathVariable Long templateId){
        try {
            List<UserDTO> allAccessOfTemplate = accessControlService.getAllAccessOfTemplate(templateId);
            return ResponseEntity.ok(allAccessOfTemplate);
        }catch (Exception e){
            throw new AccessControlException("Exception Occurred in fetching access of template "+templateId);
        }
    }

    @GetMapping("/template/access/{templateId}")
    public ResponseEntity<?> getAllAccessDetails(@PathVariable Long templateId){
        try{
            List<AccessControlDTO> accessControlDTOS = accessControlService.getAllAccessDetails(templateId);
            return ResponseEntity.ok(accessControlDTOS);
        }catch (Exception e){
            throw new AccessControlException("Exception Occurred in fetching access of template "+templateId);
        }
    }

    @DeleteMapping("/delete/access/{accessId}")
    public void deleteAccess(@PathVariable Long accessId){
        try{
            accessControlService.deleteAccessById(accessId);
        }catch (Exception e){
            throw new AccessControlException("Access Not deleted");
        }
    }

    @GetMapping("/access-template/{userId}")
    public ResponseEntity<List<TemplateDTO>> getAccessTemplateOfUser(@PathVariable Long userId){
        try{
            List<TemplateDTO> accessTemplateOfUser = accessControlService.getAccessTemplateOfUser(userId);
            return ResponseEntity.ok(accessTemplateOfUser);
        }catch (Exception e){
            throw new AccessControlException("Access Template Not found");
        }
    }

    @GetMapping("/access/{userId}")
    public ResponseEntity<List<AccessControlDTO>> getAccess(@PathVariable Long userId){
        try{
            List<AccessControlDTO> accessControlDTOS = accessControlService.getAccessOfUser(userId);
//            System.out.println("Here");
            return ResponseEntity.ok(accessControlDTOS);
        }catch (Exception e){
            throw new AccessControlException("Access Template Not found");
        }
    }

    @GetMapping("/countAccessTemplate/{id}")
    public ResponseEntity<?> countAccessTemplate(@PathVariable Long id){
        try {
            Integer countAccessTemplate = accessControlService.countAccessTemplate(id);
            return ResponseEntity.status(HttpStatus.OK).body(countAccessTemplate);
        }
        catch (Exception e){
            throw new UserNotFoundException("User not found");
        }
    }

    @GetMapping("/all/access/{userId}/templateId")
    public ResponseEntity<List<Long>> getAccessTemplateIdByUserId(@PathVariable Long userId){
        try{
            return ResponseEntity.ok(accessControlService.getAccessTemplateIdByUserId(userId));
        }catch (Exception e){
            throw new AccessControlException("Exception on fetching templateId");
        }
    }
}
