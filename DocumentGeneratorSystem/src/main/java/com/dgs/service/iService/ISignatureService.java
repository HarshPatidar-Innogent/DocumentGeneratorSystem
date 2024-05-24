package com.dgs.service.iService;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import com.dgs.enums.SignatureType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ISignatureService {
   public SignatureDTO addSignature(MultipartFile file, String recipientEmail, SignatureType signatureType) throws IOException;
}
