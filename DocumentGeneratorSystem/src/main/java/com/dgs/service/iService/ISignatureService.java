package com.dgs.service.iService;

import com.dgs.DTO.SignatureDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISignatureService {

    public SignatureDTO addSignature(MultipartFile file, SignatureDTO signatureDTO) throws IOException;

    public byte[] getImageDataById(Long id);

    public String deleteSignature(Long id);

    public SignatureDTO getSignatureById(Long id);

    public SignatureDTO updateSignature(Long id, MultipartFile file, SignatureDTO signatureDTO) throws IOException;

    public SignatureDTO addSignatureDrawn(MultipartFile file, SignatureDTO signatureDTO) throws IOException;

    public SignatureDTO addSignatureElectronic(SignatureDTO signatureDTO, String Name) throws IOException;

    public List<SignatureDTO> getAllSignatureOfDocument(Long id);

    public Boolean isSigned(Long documentId, String placeholder);
}
