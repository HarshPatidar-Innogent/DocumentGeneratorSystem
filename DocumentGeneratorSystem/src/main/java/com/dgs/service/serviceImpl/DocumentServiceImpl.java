package com.dgs.service.serviceImpl;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Signature;
import com.dgs.entity.Template;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.repository.SignatureRepo;
import com.dgs.repository.TemplateRepo;
import com.dgs.service.iService.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

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


    private Map<String, String> emails;



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


    public String populateDocument(Map<String, String> textData, Long templateId) {
        emails = new HashMap<>();
        Optional<Template> templateOptional = templateRepo.findById(templateId);
        if (templateOptional.isEmpty()) {
            throw new IllegalArgumentException("Template not found with id: " + templateId);
        }

        Template template = templateOptional.get();
        String templateBody = template.getTemplateBody();

        for (Map.Entry<String, String> entry : textData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!isSignaturePlaceholder(key, template)) {
//                emails.put(value, key);
//            } else {
                templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
        }
        return templateBody;
    }

    private boolean isSignaturePlaceholder(String placeholderName, Template template) {
        return template.getPlaceholderList().stream()
                .anyMatch(placeholder -> placeholder.getPlaceholderName().equals(placeholderName) && placeholder.getPlaceholderType().equals("signature"));
    }

    private String encode(String value){
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = mapperConfig.toDocument(documentDTO);
        System.out.println(documentDTO);
        Document savedDocument = documentRepo.save(document);
        DocumentDTO saveDocumentDTO = mapperConfig.toDocumentDTO(savedDocument);

        Map<String, String> documentEmails = documentDTO.getSignatureEmails();
        for (Map.Entry<String, String> entry : documentEmails.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            emails.put(key, value);
        }


        if (!documentDTO.getSignatureEmails().isEmpty()) {
            documentDTO.getSignatureEmails().forEach((placeholder, email) -> {
                String encodedDocumentId = encode(String.valueOf(document.getDocumentId()));
                String encodedPlaceholder = encode("{{"+placeholder+"}}");
                String encodedEmail = encode(email);
//                String url = "http://192.168.5.215:3000/sign/" + encodedDocumentId + "/" + encodedPlaceholder ;
                String url = "http://192.168.5.219:3000/sign/" + encodedDocumentId + "/" + encodedPlaceholder +"/"+encodedEmail;
                emailService.sendEmail(email, "Document Signature Request", url);
                Signature signature = new Signature();
//                signature.setPlaceholder("{{"+emails.get(email)+"}}");
                signature.setDocument(documentRepo.findById(document.getDocumentId()).orElseThrow(() -> new IllegalArgumentException("Invalid document ID")));
                signature.setSigned(false);
                signature.setRecipientEmail(email);
                signatureRepo.save(signature);
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
    public void deleteDocument(Long id) {
        Optional<Document> documentOptional = documentRepo.findById(id);
        if (documentOptional.isPresent()) {
            documentRepo.deleteById(id);
//            return "Document Deleted";
        }
//        return "Document Not Found";
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
