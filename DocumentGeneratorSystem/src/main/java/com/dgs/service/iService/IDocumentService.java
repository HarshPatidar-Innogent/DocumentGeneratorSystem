package com.dgs.service.iService;

import com.dgs.DTO.DocumentDTO;
import com.dgs.entity.Document;

import java.util.List;

public interface IDocumentService {
    public List<DocumentDTO> getAllDocument();

    public DocumentDTO getDocumentById(Long id);

    public DocumentDTO createDocument(DocumentDTO documentDTO);

    public DocumentDTO updateDocument(Long id);

    public String deleteDocument(Long id);
}
