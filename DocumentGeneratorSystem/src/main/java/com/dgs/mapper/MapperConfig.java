package com.dgs.mapper;

import com.dgs.DTO.*;
import com.dgs.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {
    private ModelMapper mapper;

    public MapperConfig() {
        mapper = new ModelMapper();
    }

    //convert document entity into dto
    public DocumentDTO toDocumentDTO(Document document){
        return mapper.map(document, DocumentDTO.class);
    }
    //convert documentDto into entity
    public Document toDocument(DocumentDTO documentDTO){
        return mapper.map(documentDTO, Document.class);
    }

    //convert placeholder entity into dto
    public PlaceholderDTO toPlaceholderDto(Placeholder placeholder){
        return mapper.map(placeholder, PlaceholderDTO.class);
    }
    //convert placeholderDto into entity
    public Placeholder toPlaceholder(PlaceholderDTO placeholderDTO){
        return mapper.map(placeholderDTO, Placeholder.class);
    }

    //convert template entity into dto
    public TemplateDTO toTemplateDto(Template template){
        return mapper.map(template, TemplateDTO.class);
    }
    //convert templateDto into entity
    public Template toTemplate(TemplateDTO templateDTO){
        return mapper.map(templateDTO, Template.class);
    }

    public SignatureDTO toSignatureDTO(Signature signature){
        return mapper.map(signature, SignatureDTO.class);
    }
    public Signature toSignature(SignatureDTO signatureDTO){
        return mapper.map(signatureDTO, Signature.class);
    }

    public AccessControlDTO toAccessControlDTO(AccessControl accessControl){
        return mapper.map(accessControl, AccessControlDTO.class);
    }

    public AccessControl toAccessControl(AccessControlDTO accessControlDTO){
        return mapper.map(accessControlDTO, AccessControl.class);
    }

    public AuditTrailDTO toAuditTrailDTO(AuditTrail auditTrail){
        return mapper.map(auditTrail, AuditTrailDTO.class);
    }

    public AuditTrail toAuditTrail(AuditTrailDTO auditTrailDTO){
        return mapper.map(auditTrailDTO, AuditTrail.class);
    }













}
