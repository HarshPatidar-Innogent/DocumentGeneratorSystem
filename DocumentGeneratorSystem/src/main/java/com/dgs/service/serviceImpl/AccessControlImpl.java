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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

        User user = userRepo.findById(userId).orElse(null);
        if(user==null){
            throw new UserNotFoundException("User Not Found with userid "+userId);
        }

        Template template =templateRepo.findById(accessControlDTO.getTemplate()).orElseThrow(null);
        if(template == null){
            throw new TemplateNotFoundException(accessControlDTO.getTemplate());
        }

        User owner = userRepo.findById(accessControlDTO.getOwnerId()).orElse(null);
        if(owner==null){
            throw new UserNotFoundException("User Not Found with userid "+accessControlDTO.getOwnerId());
        }

        AccessControl accessControl = mapperConfig.toAccessControl(accessControlDTO, user, owner, template.orElse(null));
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

        List<Long> templatesIds;

        try{
            templatesIds = accessControlRepo.findTemplateIdByUserId(userId);
        }catch (Exception e){
            throw new AccessControlException("Failed to retrieve template IDs for user: " + userId);
        }
//        List<Template> templates = templatesIds.stream().map(id->templateRepo.findById(id).get()).toList();
//        return templates.stream().map(template -> mapperConfig.toTemplateDto(template)).toList();
        return templatesIds.stream()
                .map(id->{
                    try{
                        return templateRepo.findById(id).orElseThrow(()->new TemplateNotFoundException(id));
                    }catch(TemplateNotFoundException e){
                        System.err.println(e.getMessage());
                        return null;
                    }catch (Exception e){
                        System.err.println(e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull).map(template->mapperConfig.toTemplateDto(template)).collect(Collectors.toList()
                );
    }

    @Override
    public List<AccessControlDTO> getAccessOfUser(Long userId) {
        List<AccessControl> accessControls = accessControlRepo.findAllByUserId(userId);
        return accessControls.stream().map(access->mapperConfig.toAccessControlDTO(access)).toList();
    }

    @Override
    public List<Long> getAccessTemplateIdByUserId(Long userId) {
        return accessControlRepo.getAllAccessTemplateId(userId);
    }

    @Override
    public Integer countAccessTemplate(Long userId) {
        Integer countAccessTemplate = accessControlRepo.countAccessTemplate(userId);
        System.out.println(countAccessTemplate);
        return countAccessTemplate;
    }


}
