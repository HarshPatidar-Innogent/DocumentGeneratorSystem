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

    //    public String populateDocument(Map<String, String> dynamicData, Long templateId) {
//        Template template = templateRepo.findById(templateId).get();
//        String templateBody = template.getTemplateBody();
//        System.out.println(dynamicData);
//        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
//            templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
//        }
//        return templateBody;
//    }
    private Set<String> emails;

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

    public String populateDocument(Map<String, String> dynamicData, Long templateId) {
        Template template = templateRepo.findById(templateId).get();
        String templateBody = template.getTemplateBody();
//        System.out.println(dynamicData);
        emails = new HashSet<>();
        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
//            templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
            String key = entry.getKey();
            String value = entry.getValue();

            if (isSignaturePlaceholder(key, template)) {
                emails.add(value);
            }
            templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());

        }
        return templateBody;
    }

    private boolean isSignaturePlaceholder(String placeholderName, Template template) {
        return template.getPlaceholderList().stream()
                .anyMatch(placeholder -> placeholder.getPlaceholderName().equals(placeholderName) && placeholder.getPlaceholderType().equals("signature"));
    }

//    public String populateDocument(Map<String, MultipartFile> fileData, Map<String, String> textData, Long templateId) {
//        Optional<Template> templateOptional = templateRepo.findById(templateId);
//        if (templateOptional.isEmpty()) {
//            throw new IllegalArgumentException("Template not found with id: " + templateId);
//        }
//
//        Template template = templateOptional.get();
//        String templateBody = template.getTemplateBody();

//        for (Map.Entry<String, MultipartFile> entry : fileData.entrySet()) {
//            MultipartFile file = entry.getValue();
//            if (!file.isEmpty()) {
//                try {
//                    // Read the bytes of the image file
//                    byte[] fileBytes = file.getBytes();
//
//                    // Encode the bytes to base64
//                    String base64Image = Base64.getEncoder().encodeToString(fileBytes);
//
//                    // Embed the base64-encoded image into the template body
//                    String imageDataUri = "data:" + file.getContentType() + ";base64," + base64Image;
//                    templateBody = templateBody.replace("{{" + entry.getKey() + "}}", "<img src='" + imageDataUri + "' alt='signature'/>");
//                } catch (IOException e) {
//                    // Handle IOException
//                    e.printStackTrace();
//                    throw new RuntimeException("Failed to process signature image", e);
//                }
//            }
//        }
//
//        for (Map.Entry<String, String> entry : textData.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
////            System.out.println(template.getPlaceholderList().get(1).getPlaceholderType());
//            // Check if value is base64 image data or plain text (initials)
//            if (value.startsWith("data:image")) {
//                // Handle the image replacement logic
//                templateBody = templateBody.replace("{{" + key + "}}", "<img src='" + value + "' alt='signature1'/>");
//            } else {
//                // Handle initials replacement
//                templateBody = templateBody.replace("{{" + key + "}}", value);
//            }
//        }
//
//        return templateBody;
//    }

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = mapperConfig.toDocument(documentDTO);
        Document savedDocument = documentRepo.save(document);
        DocumentDTO saveDocumentDTO = mapperConfig.toDocumentDTO(savedDocument);

        if (!documentDTO.getSignatureEmails().isEmpty()) {
            System.out.println("HEre");
            documentDTO.getSignatureEmails().forEach(email -> {
                emailService.sendEmail(email, "Document Signature Request", "http://192.168.5.228:3000/sign-document/"+document.getDocumentId()+"/"+email);
            });
        }
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
