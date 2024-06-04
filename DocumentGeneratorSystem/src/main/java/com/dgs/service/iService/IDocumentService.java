package com.dgs.service.iService;

import com.dgs.DTO.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IDocumentService {
    public List<DocumentDTO> getAllDocumentOfUser(Long userId);

    public DocumentDTO getDocumentById(Long id);

    public String populateDocument(Map<String, String> dynamicData, Long templateId);
//public String populateDocument(Map<String, MultipartFile> fileData, Map<String, String> dynamicData, Long templateId);


    public DocumentDTO createDocument(DocumentDTO documentDTO);

    public DocumentDTO updateDocument(Long id);

    public String deleteDocument(Long id);

    public String submitSignature(String documentBody, Long documentId);
}
