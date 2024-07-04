package com.dgs.service.serviceImpl;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Signature;
import com.dgs.entity.Template;
import com.dgs.exception.CustomException.DocumentNotFoundException;
import com.dgs.exception.CustomException.PopulateTemplateException;
import com.dgs.exception.CustomException.TemplateNotFoundException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.repository.SignatureRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.service.iService.IDocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
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

    @Autowired
    private SignatureRepo signatureRepo;

    private StringBuilder url = new StringBuilder("http://192.168.5.219:3000/sign/");

    @Override
    public List<DocumentDTO> getAllDocumentOfUser(Long userId) {
        List<Document> documents = documentRepo.findAllByUserId(userId);
        if (documents.isEmpty()) {
            log.info("getAllDocumentOfUser");
            throw new DocumentNotFoundException("Document not found", HttpStatus.NOT_FOUND);
        }
        List<DocumentDTO> documentDTOS = documents.stream().map(mapperConfig::toDocumentDTO).toList();
        return documentDTOS;

    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Optional<Document> optionalDocument = documentRepo.findById(id);
        if(optionalDocument.isEmpty()){
            log.info("getDocumentById");
            throw new DocumentNotFoundException("Document not found with id", HttpStatus.NOT_FOUND);
        }
        Document document = optionalDocument.get();
        return mapperConfig.toDocumentDTO(document);

    }

    public String populateDocument(Map<String, String> textData, Long templateId) {
        Optional<Template> templateOptional = templateRepo.findById(templateId);
        if (templateOptional.isEmpty()) {
            throw new TemplateNotFoundException("Template not found with id: " + templateId, HttpStatus.NOT_FOUND);
        }

        Template template = templateOptional.get();
        String templateBody = template.getTemplateBody();

        try{
            for (Map.Entry<String, String> entry : textData.entrySet()) {
                String key = entry.getKey();
                if (!isSignaturePlaceholder(key, template)) {
                    templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
                }
            }
        }catch (Exception e){
            log.info("populateDocument");
            throw new PopulateTemplateException("Exception in Populating Template", HttpStatus.NOT_FOUND);
        }
        return templateBody;
    }

    private boolean isSignaturePlaceholder(String placeholderName, Template template) {
        return template.getPlaceholderList().stream()
                .anyMatch(placeholder -> placeholder.getPlaceholderName().equals(placeholderName) && placeholder.getPlaceholderType().equals("signature"));
    }

    private String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = mapperConfig.toDocument(documentDTO);

        Document savedDocument = documentRepo.save(document);
        DocumentDTO saveDocumentDTO = mapperConfig.toDocumentDTO(savedDocument);

        if (!documentDTO.getSignatureEmails().isEmpty()) {
            documentDTO.getSignatureEmails().forEach((placeholder, email) -> {
                String encodedDocumentId = encode(String.valueOf(document.getDocumentId()));
                String encodedPlaceholder = encode("{{" + placeholder + "}}");
                String encodedEmail = encode(email);
                url.append(encodedDocumentId)
                        .append("/")
                        .append(encodedPlaceholder)
                        .append("/")
                        .append(encodedEmail);
//                String url = "http://192.168.5.219:3000/sign/" + encodedDocumentId + "/" + encodedPlaceholder + "/" + encodedEmail;
                emailService.sendEmail(email, "Document Signature Request", String.valueOf(url));
                Signature signature = new Signature();
                signature.setDocument(documentRepo.findById(document.getDocumentId()).orElseThrow(() -> new IllegalArgumentException("Invalid document ID")));
                signature.setSigned(false);
                signature.setRecipientEmail(email);
                signatureRepo.save(signature);
            });
        }

        return saveDocumentDTO;
    }

    @Override
    public void deleteDocument(Long id) {
        Optional<Document> documentOptional = documentRepo.findById(id);
        if (documentOptional.isPresent()) {
            documentRepo.deleteById(id);
        }
    }

    @Override
    public String submitSignature(String documentBody, Long documentId) {
        Optional<Document> documentOptional = documentRepo.findById(documentId);
        if(documentOptional.isEmpty()){
            throw new DocumentNotFoundException("Document Not found for signature", HttpStatus.NOT_FOUND);
        }
        Document document1 = documentOptional.get();
        document1.setDocumentBody(documentBody);
        Document save = documentRepo.save(document1);
        return "Document Signed";
    }

    @Override
    public Integer countDocument(Long userId) {
        Integer count = documentRepo.countDocument(userId);
        return count;
    }
}
