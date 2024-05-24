package com.dgs.service.serviceImpl;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.service.iService.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public List<DocumentDTO> getAllDocument() {
        List<Document> documents = documentRepo.findAll();
        List<DocumentDTO> documentDTOS = documents.stream().map(mapperConfig::toDocumentDTO).toList();
        return documentDTOS;
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Optional<Document> optionalDocument = documentRepo.findById(id);
        Document document;
        if (optionalDocument.isPresent()) {
            document = optionalDocument.get();
            return mapperConfig.toDocumentDTO(document);
        }
        throw new NullPointerException("Document with id not present");
    }

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = mapperConfig.toDocument(documentDTO);
        Document saveDocument = documentRepo.save(document);
        return mapperConfig.toDocumentDTO(saveDocument);
    }

    @Override
    public DocumentDTO updateDocument(Long id) {
        return null;
    }

    @Override
    public String deleteDocument(Long id) {
        Optional<Document> documentOptional = documentRepo.findById(id);
        if (documentOptional.isPresent()) {
            documentRepo.deleteById(id);
            return "Document Deleted";
        }
        return "Document Not Found";
    }
}
