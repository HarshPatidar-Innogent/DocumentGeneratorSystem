package com.dgs.service.serviceImpl;

import com.dgs.DTO.AccessControlDTO;
import com.dgs.DTO.TemplateDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.entity.AccessControl;
import com.dgs.entity.Template;
import com.dgs.entity.User;
import com.dgs.exception.CustomException.AccessControlException;
import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.exception.CustomException.UserNotFoundException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.AccessControlRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IAccessControlService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AccessControlImpl implements IAccessControlService {

    //    private static final Logger log = LoggerFactory.getLogger(AccessControlImpl.class);
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
        User user = getUser(userId);
        User owner = getUser(accessControlDTO.getOwnerId());
        Template template = getTemplate(accessControlDTO.getTemplate());
        AccessControl accessControl = mapperConfig.toAccessControl(accessControlDTO, user, owner, template);
        AccessControl save = accessControlRepo.save(accessControl);
        return mapperConfig.toAccessControlDTO(save);
    }

    @Override
    public List<UserDTO> getAllAccessOfTemplate(Long templateId) {
        try {
            List<AccessControl> accessControls = accessControlRepo.findByTemplateId(templateId);
            List<User> users = accessControls.stream().map(AccessControl::getUser).toList();
            return users.stream().map(user -> mapperConfig.toUserDTO(user)).toList();
        } catch (Exception e) {
            throw new AccessControlException("Exception in fetching AccessControl", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<AccessControlDTO> getAllAccessDetails(Long templateId) {
        List<AccessControl> accessControls = accessControlRepo.findByTemplateId(templateId);
//        if (accessControls.isEmpty()) {
//            log.info("getAllAccessDetails");
//            throw new AccessControlException("AccessControl not found with Template Id");
//        }
        return accessControls.stream().map(access -> mapperConfig.toAccessControlDTO(access)).toList();
    }

    @Override
    public void deleteAccessById(Long accessId) {
        accessControlRepo.deleteById(accessId);
    }

    @Override
    public List<TemplateDTO> getAccessTemplateOfUser(Long userId) {
        List<Long> templatesIds = accessControlRepo.findTemplateIdByUserId(userId);

        if (templatesIds.isEmpty()) {
            throw new AccessControlException("Access Control not found", HttpStatus.NOT_FOUND);
        }
        return templatesIds.stream()
                .map(this::getTemplate)
//                .filter(Objects::nonNull)
                .map(template -> MapperConfig.toTemplateDto(template))
                .collect(Collectors.toList()
                );
    }

    @Override
    public List<AccessControlDTO> getAccessOfUser(Long userId) {
        List<AccessControl> accessControls = accessControlRepo.findAllByUserId(userId);
        if (accessControls.isEmpty()) {
            throw new AccessControlException("Access Control not found with userId " + userId, HttpStatus.NOT_FOUND);
        }
        return accessControls.stream().map(access -> mapperConfig.toAccessControlDTO(access)).toList();

    }

    @Override
    public List<Long> getAccessTemplateIdByUserId(Long userId) {
        return accessControlRepo.getAllAccessTemplateId(userId);
    }

    @Override
    public Integer countAccessTemplate(Long userId) {
        Integer countAccessTemplate = accessControlRepo.countAccessTemplate(userId);
        return countAccessTemplate;
    }

    private User getUser(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with userId " + userId, HttpStatus.NOT_FOUND);
        }
        return userOptional.get();
    }

    private Template getTemplate(Long templateId) {
        Optional<Template> templateOptional = templateRepo.findById(templateId);
        if (templateOptional.isEmpty()) {
            throw new TemplateNotFoundException("Template Not Found", HttpStatus.NOT_FOUND);
        }
        return templateOptional.get();
    }

}
