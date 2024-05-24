package com.dgs.mapper;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {
    private ModelMapper mapper;

    public void mapper() {
        mapper = new ModelMapper();
    }

    public DocumentDTO toDocumentDTO(Document document){
        return mapper.map(document, DocumentDTO.class);
    }

    public Document toDocument(DocumentDTO documentDTO){
        return mapper.map(documentDTO, Document.class);
    }
}
