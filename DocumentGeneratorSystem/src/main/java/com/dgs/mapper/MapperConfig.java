package com.dgs.mapper;

import com.dgs.DTO.DocumentDTO;
import com.dgs.DTO.PlaceholderDTO;
import com.dgs.DTO.SignatureDTO;
import com.dgs.DTO.TemplateDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Placeholder;
import com.dgs.entity.Signature;
import com.dgs.entity.Template;
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






}
