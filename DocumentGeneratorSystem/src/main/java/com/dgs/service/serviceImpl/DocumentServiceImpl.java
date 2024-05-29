package com.dgs.service.serviceImpl;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Template;
import com.dgs.enums.DocumentStatus;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.service.iService.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private TemplateRepo templateRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public List<DocumentDTO> getAllDocumentOfUser(Long userId) {
        List<Document> documents = documentRepo.findAllByUserId(userId);
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

    public String createDocument(Map<String, String> dynamicData, Long templateId) {
        Template template = templateRepo.findById(templateId).get();
        String templateBody = template.getTemplateBody();
        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
            templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        Document document = new Document();
        document.setDocumentName("Order Confirmation");
        document.setDocumentBody(templateBody);
        document.setTemplate(template);
        document.setStatus(DocumentStatus.PENDING);
        documentRepo.save(document);
        return templateBody;
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
