package com.dgs.service.serviceImpl;
import java.util.Base64;
import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Signature;
import com.dgs.entity.User;
import com.dgs.enums.SignatureType;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.repository.SignatureRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.ISignatureService;
import com.dgs.signatureGenerator.SignatureGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class SignatureServiceImpl implements ISignatureService {

    @Autowired
    private SignatureRepo signatureRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public SignatureDTO addSignature(MultipartFile file , SignatureDTO signatureDTO) throws IOException {
        Document document = documentRepo.findById(signatureDTO.getDocumentId()).get();
        User user = userRepo.findById(signatureDTO.getUserId()).get();
        if(document !=null){
            boolean emailExists = signatureRepo.existsByRecipientEmail(signatureDTO.getRecipientEmail());

            Signature sign = new Signature();
            sign.setSignatureData(file.getBytes());
            sign.setRecipientEmail(signatureDTO.getRecipientEmail());
            sign.setSignatureType(signatureDTO.getSignatureType());

            sign.setDocument(document);
            sign.setUser(user);

            Signature signature = signatureRepo.save(sign);
            return mapperConfig.toSignatureDTO(signature);
        }
        else{
            return null;
        }


    }
    private String generateElectronicSignature(String email, LocalDateTime dateTime) {
        // Implement your logic to generate an electronic signature
        // For example, creating a hash or any digital representation
        String data = email + dateTime.toString();
        return Base64.getEncoder().encodeToString(data.getBytes());
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

    @Override
    public SignatureDTO addSignatureDrawn(MultipartFile file , SignatureDTO signatureDTO) throws IOException {

           byte[] signatureData = file.getBytes();
           Document document = documentRepo.findById(signatureDTO.getDocumentId()).get();
           User user = userRepo.findById(signatureDTO.getUserId()).get();
           Signature signature1 = new Signature();
           signature1.setSignatureData(signatureData);
           signature1.setSignatureType(signatureDTO.getSignatureType());
           signature1.setRecipientEmail(signatureDTO.getRecipientEmail());
           signature1.setUser(user);
           signature1.setDocument(document);
           System.out.println(signature1);
           Signature signature = signatureRepo.save(signature1);
           return mapperConfig.toSignatureDTO(signature);


    }

    @Override
    public SignatureDTO addSignatureElectronic(SignatureDTO signatureDTO) throws IOException {
        Document document = documentRepo.findById(signatureDTO.getDocumentId()).get();
        User user = userRepo.findById(signatureDTO.getUserId()).get();

        Signature sign = new Signature();
        sign.setRecipientEmail(signatureDTO.getRecipientEmail());
        sign.setSignatureType(signatureDTO.getSignatureType());

        byte[] signatureImage = SignatureGenerator.generateSignatureImage(signatureDTO.getRecipientEmail());
        sign.setSignatureData(signatureImage);
        sign.setDocument(document);
        sign.setUser(user);
        Signature signature = signatureRepo.save(sign);
        return mapperConfig.toSignatureDTO(signature);

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
