package com.dgs.service.serviceImpl;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.TemplateDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.entity.AccessControl;
import com.dgs.entity.Template;
import com.dgs.entity.User;
import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.exception.CustomException.UserNotFoundException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.AccessControlRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IAccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public AccessControlDTO addAccess(AccessControlDTO accessControlDTO) {

        Long userId = accessControlDTO.getUserId();
        User user = userRepo.findById(userId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Template template =templateRepo.findById(accessControlDTO.getTemplate()).orElseThrow(()->new TemplateNotFoundException("Template Not Found"));
        User ownerId = userRepo.findById(accessControlDTO.getOwnerId()).orElseThrow(()->new UserNotFoundException("User Not Found"));

        AccessControl accessControl = new AccessControl();
        accessControl.setTemplate(template);
        accessControl.setUser(user);
        accessControl.setOwnerId(ownerId);
        accessControl.setTemplateAccess(com.dgs.enums.AccessControl.valueOf(accessControlDTO.getTemplateAccess()));
        accessControl.setOwnerName(accessControlDTO.getOwnerName());

        AccessControl save = accessControlRepo.save(accessControl);
        return mapperConfig.toAccessControlDTO(save);

    }

    @Override
    public List<UserDTO> getAllAccessOfTemplate(Long templateId) {
        List<AccessControl> accessControls = accessControlRepo.findByTemplateId(templateId);
        List<User> users = accessControls.stream().map(AccessControl::getUser).toList();
        return users.stream().map(user -> mapperConfig.toUserDTO(user)).toList();
    }

    @Override
    public List<AccessControlDTO> getAllAccessDetails(Long templateId) {
        List<AccessControl> accessControls = accessControlRepo.findByTemplateId(templateId);
        return accessControls.stream().map(access->mapperConfig.toAccessControlDTO(access)).toList();
    }

    @Override
    public void deleteAccessById(Long accessId) {
        accessControlRepo.deleteById(accessId);
    }

    @Override
    public List<TemplateDTO> getAccessTemplateOfUser(Long userId) {
        List<Long> templatesId = accessControlRepo.findTemplateIdByUserId(userId);
        List<Template> templates = templatesId.stream().map(id->templateRepo.findById(id).get()).toList();
        return templates.stream().map(template -> mapperConfig.toTemplateDto(template)).toList();
    }

    @Override
    public List<AccessControlDTO> getAccessOfUser(Long userId) {
        List<AccessControl> accessControls = accessControlRepo.findAllByUserId(userId);
        return accessControls.stream().map(access->mapperConfig.toAccessControlDTO(access)).toList();
    }

    @Override
    public Integer countAccessTemplate(Long userId) {
        Integer countAccessTemplate = accessControlRepo.countAccessTemplate(userId);
        System.out.println(countAccessTemplate);
        return countAccessTemplate;
    }


}
