package com.dgs.service.iService;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import com.dgs.enums.SignatureType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ISignatureService {

   public SignatureDTO addSignature(MultipartFile file, SignatureDTO signatureDTO) throws IOException;
   public byte[] getImageDataById(Long id);
   public String deleteSignature(Long id);
   public SignatureDTO getSignatureById(Long id);
//   public SignatureDTO updateSignature(Long id , MultipartFile file , String recipientEmail, SignatureType signatureType)throws IOException;
   public  SignatureDTO updateSignature (Long id, MultipartFile file , SignatureDTO signatureDTO) throws IOException;
}
