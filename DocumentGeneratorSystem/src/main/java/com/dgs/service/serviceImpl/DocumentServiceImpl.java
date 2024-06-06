package com.dgs.service.serviceImpl;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Template;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.service.iService.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private EmailService emailService;

    @Autowired
    private MapperConfig mapperConfig;

    private Map<String, String> emails = new HashMap<>();
    ;

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

//    public String populateDocument(Map<String, String> dynamicData, Long templateId) {
//        Template template = templateRepo.findById(templateId).get();
//        String templateBody = template.getTemplateBody();
//        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
//            templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
//        }
//        return templateBody;
//    }

    public String populateDocument(Map<String, String> textData, Long templateId) {
        Optional<Template> templateOptional = templateRepo.findById(templateId);
        if (templateOptional.isEmpty()) {
            throw new IllegalArgumentException("Template not found with id: " + templateId);
        }

        Template template = templateOptional.get();
        String templateBody = template.getTemplateBody();

        for (Map.Entry<String, String> entry : textData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (isSignaturePlaceholder(key, template)) {
                emails.put(value, key);
            } else {
                templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
        }
        return templateBody;
    }

    private boolean isSignaturePlaceholder(String placeholderName, Template template) {
        return template.getPlaceholderList().stream()
                .anyMatch(placeholder -> placeholder.getPlaceholderName().equals(placeholderName) && placeholder.getPlaceholderType().equals("signature"));
    }

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = mapperConfig.toDocument(documentDTO);
        Document savedDocument = documentRepo.save(document);
        DocumentDTO saveDocumentDTO = mapperConfig.toDocumentDTO(savedDocument);

        if (!documentDTO.getSignatureEmails().isEmpty()) {
            documentDTO.getSignatureEmails().forEach(email -> {
                emailService.sendEmail(email, "Document Signature Request", "http://192.168.5.228:3000/sign/" + document.getDocumentId() + "/{{" + emails.get(email)+"}}");
            });
        }
        emails.clear();
        return saveDocumentDTO;
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

    @Override
    public String submitSignature(String documentBody, Long documentId) {
        Optional<Document> document = documentRepo.findById(documentId);
        Document document1 = document.get();
        document1.setDocumentBody(documentBody);
        Document save = documentRepo.save(document1);
        return "Document Signed";
    }
}
