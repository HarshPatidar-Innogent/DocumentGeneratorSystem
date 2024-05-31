package com.dgs.mapper;

import com.dgs.DTO.*;
import com.dgs.entity.*;
import com.dgs.enums.Role;
import com.dgs.repository.DepartmentRepo;
import com.dgs.repository.DesignationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperConfig {
    private ModelMapper mapper;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DesignationRepo designationRepo;

    public MapperConfig() {
        mapper = new ModelMapper();
    }

    //convert placeholder entity into dto
    public static PlaceholderDTO toPlaceholderDto(Placeholder placeholder) {
//        return mapper.map(placeholder, PlaceholderDTO.class);
        PlaceholderDTO dto = new PlaceholderDTO();
        dto.setPlaceholderId(placeholder.getPlaceholderId());
        dto.setPlaceholderName(placeholder.getPlaceholderName());
        dto.setPlaceholderType(placeholder.getPlaceholderType());
        return dto;
    }

    //convert placeholderDto into entity
    public static Placeholder toPlaceholder(PlaceholderDTO dto, Template template) {
//        return mapper.map(placeholderDTO, Placeholder.class);

        Placeholder placeholder = new Placeholder();
        placeholder.setPlaceholderId(dto.getPlaceholderId());
        placeholder.setPlaceholderName(dto.getPlaceholderName());
        placeholder.setPlaceholderType(dto.getPlaceholderType());
        placeholder.setTemplate(template); // Set the template reference
        // We need to fetch the template entity separately if needed
        return placeholder;
    }

    //convert template entity into dto
    public static TemplateDTO toTemplateDto(Template template) {
//        return mapper.map(template, TemplateDTO.class);

        TemplateDTO dto = new TemplateDTO();
        dto.setTemplateId(template.getTemplateId());
        dto.setTemplateName(template.getTemplateName());
        dto.setTemplateFormat(template.getTemplateFormat());
        dto.setTemplateBody(template.getTemplateBody());
        dto.setCreatedAt(template.getCreatedAt());
        dto.setUpdatedAt(template.getUpdatedAt());
        dto.setUserId(template.getUser().getUserId());
        dto.setPlaceholderDTOS(template.getPlaceholderList().stream()
                                       .map(MapperConfig::toPlaceholderDto)
                                       .collect(Collectors.toList()));
        return dto;
    }

    //convert templateDto into entity
    public static Template toTemplate(TemplateDTO dto, User user) {
//        return mapper.map(templateDTO, Template.class);

        Template template = new Template();
        template.setTemplateId(dto.getTemplateId());
        template.setTemplateName(dto.getTemplateName());
        template.setTemplateFormat(dto.getTemplateFormat());
        template.setTemplateBody(dto.getTemplateBody());
        template.setCreatedAt(dto.getCreatedAt());
        template.setUpdatedAt(dto.getUpdatedAt());
        template.setUser(user);
        template.setPlaceholderList(dto.getPlaceholderDTOS().stream()
                                            .map(placeholderDTO -> toPlaceholder(placeholderDTO, template))
                                            .collect(Collectors.toList()));
        return template;
    }

    //convert document entity into dto
    public DocumentDTO toDocumentDTO(Document document) {
        return mapper.map(document, DocumentDTO.class);
    }

    //convert documentDto into entity
    public Document toDocument(DocumentDTO documentDTO) {
        return mapper.map(documentDTO, Document.class);
    }

    public SignatureDTO toSignatureDTO(Signature signature) {
        return mapper.map(signature, SignatureDTO.class);
    }

    public Signature toSignature(SignatureDTO signatureDTO) {
        return mapper.map(signatureDTO, Signature.class);
    }

    public AccessControlDTO toAccessControlDTO(AccessControl accessControl) {
        return mapper.map(accessControl, AccessControlDTO.class);
    }

    public AccessControl toAccessControl(AccessControlDTO accessControlDTO) {
        return mapper.map(accessControlDTO, AccessControl.class);
    }

    public AuditTrailDTO toAuditTrailDTO(AuditTrail auditTrail) {
        return mapper.map(auditTrail, AuditTrailDTO.class);
    }

    public AuditTrail toAuditTrail(AuditTrailDTO auditTrailDTO) {
        return mapper.map(auditTrailDTO, AuditTrail.class);
    }

    public UserDTO toUserDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public User toUser(UserDTO userDTO) {
//        return mapper.map(userDTO, User.class);
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDepartment(departmentRepo.findById(userDTO.getDepartmentId()).get());
        user.setDesignation(designationRepo.findById(userDTO.getDesignationId()).get());
        user.setRole(Role.USER);
        return user;
    }

}
