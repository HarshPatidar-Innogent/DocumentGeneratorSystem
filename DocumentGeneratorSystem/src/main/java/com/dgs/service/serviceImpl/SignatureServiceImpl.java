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
import java.util.Optional;

@Service
public class SignatureServiceImpl implements ISignatureService {

    @Autowired
    private SignatureRepo signatureRepo;

    @Autowired
    private MapperConfig mapperConfig;


    @Override
    public SignatureDTO addSignature(MultipartFile file , SignatureDTO signatureDTO) throws IOException {
        Signature sign = new Signature();
        sign.setSignatureData(file.getBytes());
        sign.setRecipientEmail(signatureDTO.getRecipientEmail());
        sign.setSignatureType(signatureDTO.getSignatureType());

        Signature signature = signatureRepo.save(sign);
        return mapperConfig.toSignatureDTO(signature);
    }

  @Override
    public byte[] getImageDataById(Long id) {
        Optional<Signature> optionalImage = signatureRepo.findById(id);
        if (optionalImage.isPresent()) {
            return optionalImage.get().getSignatureData();
        }
        return null; // Handle image not found
    }
//  public byte[] getImageDataById(Long id) {
//      Optional<Signature> optionalImage = signatureRepo.findById(id);
//      return optionalImage.get().getSignatureData();
//  }

    @Override
    public String deleteSignature(Long id) {
          Optional<Signature> signature = signatureRepo.findById(id);
          if(signature.isPresent()){
              signatureRepo.deleteById(id);
              return "Signature deleted successfully";
          }
          else{
              return "Signature Not Found";
          }

    }

    @Override
    public SignatureDTO getSignatureById(Long id) {
         Optional<Signature> getsignature = signatureRepo.findById(id);
         if (getsignature.isPresent()){
                SignatureDTO signatureDTO = mapperConfig.toSignatureDTO(getsignature.get());
                return signatureDTO;
         }
         else{
             return null;
         }
    }

    @Override
    public SignatureDTO updateSignature(Long id, MultipartFile file, SignatureDTO signatureDTO) throws IOException {
        Signature sign = signatureRepo.findById(id).get();
        if(sign!=null){
            sign.setSignatureType(signatureDTO.getSignatureType());
               sign.setRecipientEmail(signatureDTO.getRecipientEmail());
               sign.setSignatureData(file.getBytes());
               Signature updateSignature = signatureRepo.save(sign);
               return mapperConfig.toSignatureDTO(updateSignature);
        }
        else{
            return null;
        }
    }

//    @Override
//    public SignatureDTO updateSignature(Long id, MultipartFile file ,String recipientEmail, SignatureType signatureType) throws IOException {
//        Signature sign = signatureRepo.findById(id).get();
//        if(sign != null){
//               sign.setSignatureType(signatureType);
//               sign.setRecipientEmail(recipientEmail);
//               sign.setSignatureData(file.getBytes());
//               Signature updateSignature = signatureRepo.save(sign);
//               return mapperConfig.toSignatureDTO(updateSignature);
//        }
//        else{
//            return null;
//        }
//    }


}
