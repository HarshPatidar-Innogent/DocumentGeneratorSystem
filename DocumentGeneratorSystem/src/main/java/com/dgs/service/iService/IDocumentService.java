package com.dgs.service.iService;

import com.dgs.DTO.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IDocumentService {
    public List<DocumentDTO> getAllDocumentOfUser(Long userId);

    public DocumentDTO getDocumentById(Long id);

    public String populateDocument(Map<String, String> dynamicData, Long templateId);

    public DocumentDTO createDocument(DocumentDTO documentDTO);

    public void deleteDocument(Long id);

    public String submitSignature(String templateBody, Long documentId);

    public Integer countDocument(Long userId);

}
