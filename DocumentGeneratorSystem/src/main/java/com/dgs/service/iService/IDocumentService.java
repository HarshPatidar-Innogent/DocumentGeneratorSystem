package com.dgs.service.iService;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;

import java.util.List;
import java.util.Map;

public interface IDocumentService {
    public List<DocumentDTO> getAllDocumentOfUser(Long userId);

    public DocumentDTO getDocumentById(Long id);

    public String createDocument(Map<String, String> dynamicData, Long templateId);

    public DocumentDTO updateDocument(Long id);

    public String deleteDocument(Long id);
}
