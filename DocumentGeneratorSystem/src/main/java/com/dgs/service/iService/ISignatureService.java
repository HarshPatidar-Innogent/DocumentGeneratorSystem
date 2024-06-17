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

    public SignatureDTO addSignatureDrawn(MultipartFile file, SignatureDTO signatureDTO) throws IOException;

    public SignatureDTO addSignatureElectronic(SignatureDTO signatureDTO, String Name,String recipientEmail,Long documentId) throws IOException;

    public List<SignatureDTO> getAllSignatureOfDocument(Long id);

    public Boolean isSigned(Long documentId, String placeholder);

    public SignatureDTO updateSign(String recipientEmail,Long documentId , MultipartFile file,SignatureDTO signatureDTO) throws IOException;

    public void sendCompleteSignedDocument(Long documentId);
}
