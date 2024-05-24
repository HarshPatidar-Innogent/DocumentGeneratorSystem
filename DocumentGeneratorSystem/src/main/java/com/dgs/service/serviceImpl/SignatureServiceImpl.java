package com.dgs.service.serviceImpl;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import com.dgs.enums.SignatureType;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.SignatureRepo;
import com.dgs.service.iService.ISignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SignatureServiceImpl implements ISignatureService {

    @Autowired
    private SignatureRepo signatureRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public SignatureDTO addSignature(MultipartFile file , String recipientEmail,SignatureType signatureType) throws IOException {
        Signature sign = new Signature();
        sign.setSignatureData(file.getBytes());
        sign.setRecipientEmail(recipientEmail);
        sign.setSignatureType(signatureType);

        Signature signature = signatureRepo.save(sign);
        return mapperConfig.toSignatureDTO(signature);
    }
}
