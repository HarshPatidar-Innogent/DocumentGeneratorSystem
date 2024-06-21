package com.dgs.service.serviceImpl;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.*;
import com.dgs.enums.DesignationPermission;
import com.dgs.enums.TemplateAccess;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.*;
import com.dgs.service.iService.IAccessControlService;
import jakarta.persistence.Access;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private UserRepo userRepo;

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public String addAccess(AccessControlDTO accessControlDTO) {
        String email = accessControlDTO.getEmail();
        User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        Template template =templateRepo.findById(accessControlDTO.getTemplate()).get();
        User ownerId = userRepo.findById(accessControlDTO.getOwnerId()).get();

        AccessControl accessControl = new AccessControl();
        accessControl.setTemplate(template);
        accessControl.setUserId(user);
        accessControl.setOwnerId(ownerId);
        accessControl.setTemplateAccess(TemplateAccess.valueOf(accessControlDTO.getTemplateAccess()));

        AccessControl save = accessControlRepo.save(accessControl);
        if(save!=null){
            return "Saved";
        }else{
            return "Not Saved";
        }
    }
}
