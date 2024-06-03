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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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

//    public String populateDocument(Map<String, String> dynamicData, Long templateId) {
//        Template template = templateRepo.findById(templateId).get();
//        String templateBody = template.getTemplateBody();
//        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
//            templateBody = templateBody.replace("{{" + entry.getKey() + "}}", entry.getValue());
//        }
//        return templateBody;
//    }

    public String populateDocument(Map<String, MultipartFile> fileData, Map<String, String> textData, Long templateId) {
        Optional<Template> templateOptional = templateRepo.findById(templateId);
        if (templateOptional.isEmpty()) {
            throw new IllegalArgumentException("Template not found with id: " + templateId);
        }

        Template template = templateOptional.get();
        String templateBody = template.getTemplateBody();

        for (Map.Entry<String, MultipartFile> entry : fileData.entrySet()) {
            MultipartFile file = entry.getValue();
            if (!file.isEmpty()) {
                try {
                    // Read the bytes of the image file
                    byte[] fileBytes = file.getBytes();

                    // Encode the bytes to base64
                    String base64Image = Base64.getEncoder().encodeToString(fileBytes);

                    // Embed the base64-encoded image into the template body
                    String imageDataUri = "data:" + file.getContentType() + ";base64," + base64Image;
                    templateBody = templateBody.replace("{{" + entry.getKey() + "}}", "<img src='" + imageDataUri + "' alt='signature'/>");
                } catch (IOException e) {
                    // Handle IOException
                    e.printStackTrace();
                    throw new RuntimeException("Failed to process signature image", e);
                }
            }
        }

        for (Map.Entry<String, String> entry : textData.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // Check if value is base64 image data or plain text (initials)
            if (value.startsWith("data:image")) {
                // Handle the image replacement logic
                templateBody = templateBody.replace("{{" + key + "}}", "<img src='" + value + "' alt='signature'/>");
            } else {
                // Handle initials replacement
                templateBody = templateBody.replace("{{" + key + "}}", value);
            }
        }

        return templateBody;
    }


    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = mapperConfig.toDocument(documentDTO);
        return mapperConfig.toDocumentDTO(documentRepo.save(document));
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
