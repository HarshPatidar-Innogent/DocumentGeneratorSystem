package com.dgs.controller;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.entity.AccessControl;
import com.dgs.enums.DesignationPermission;
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

//    @PostMapping("/addAccess")
//    public ResponseEntity<?> addAccess(@RequestBody AccessControlDTO accessControlDTO){
//
//    }
}
