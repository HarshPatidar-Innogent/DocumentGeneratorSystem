package com.dgs.service.serviceImpl;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Document;
import com.dgs.entity.Signature;
import com.dgs.entity.User;
import com.dgs.enums.DocumentStatus;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DocumentRepo;
import com.dgs.repository.SignatureRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.ISignatureService;
import com.dgs.signatureGenerator.SignatureGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private EmailService emailService;

    @Override
    public SignatureDTO addSignature(MultipartFile file , SignatureDTO signatureDTO) throws IOException {
        Document document = documentRepo.findById(signatureDTO.getDocumentId()).get();
        Signature sign = new Signature();
        sign.setSignatureData(file.getBytes());
        sign.setSignatureType(signatureDTO.getSignatureType());
        sign.setPlaceholder(signatureDTO.getPlaceholder());
        sign.setDocument(document);
        sign.setSigned(signatureDTO.getSigned());
        sign.setRecipientEmail(signatureDTO.getRecipientEmail());
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
    public SignatureDTO addSignatureDrawn(MultipartFile file , SignatureDTO signatureDTO) throws IOException {
           byte[] signatureData = file.getBytes();
           Document document = documentRepo.findById(signatureDTO.getDocumentId()).get();
           Signature signature1 = new Signature();
           signature1.setSignatureData(signatureData);
           signature1.setSignatureType(signatureDTO.getSignatureType());
           signature1.setDocument(document);
           signature1.setPlaceholder(signatureDTO.getPlaceholder());
//           System.out.println(signature1);
           Signature signature = signatureRepo.save(signature1);
           return mapperConfig.toSignatureDTO(signature);
    }

    @Override
    public SignatureDTO addSignatureElectronic(SignatureDTO signatureDTO, String Name,String recipientEmail,Long documentId) throws IOException {
        Signature esign = signatureRepo.findByRecipientEmail(recipientEmail,documentId);
//        Signature sign = new Signature();
//        System.out.println(esign);
        esign.setSignatureType(signatureDTO.getSignatureType());
        byte[] signatureImage = SignatureGenerator.generateSignatureImage(Name);
        esign.setSignatureData(signatureImage);
        esign.setPlaceholder(signatureDTO.getPlaceholder());
        esign.setSigned(signatureDTO.getSigned());
        Signature signature = signatureRepo.save(esign);
        sendCompleteSignedDocument(documentId);
        return mapperConfig.toSignatureDTO(signature);

    }

    @Override
    public List<SignatureDTO> getAllSignatureOfDocument(Long id) {
        List<Signature> signatures = signatureRepo.getAllSignaturesOfDocument(id);
        List<SignatureDTO> signatureDTOS = signatures.stream().map(signature -> mapperConfig.toSignatureDTO(signature)).toList();
        return signatureDTOS;
    }

    @Override
    public Boolean isSigned(Long documentId, String placeholder) {
        Optional<Signature> signature = signatureRepo.findByDocumentIdAndPlaceholder(documentId, placeholder);
        return signature.map(Signature::getSigned).orElse(false);
    }

    @Override
    public SignatureDTO updateSign(String recipientEmail, Long documentId, MultipartFile file, SignatureDTO signatureDTO) throws IOException {
        Signature sign1 = signatureRepo.findByRecipientEmail(recipientEmail,documentId);
//        System.out.println(file.getBytes());
        if(sign1!=null){
            sign1.setSignatureType(signatureDTO.getSignatureType());
            sign1.setSignatureData(file.getBytes());
            sign1.setPlaceholder(signatureDTO.getPlaceholder());
            sign1.setSigned(signatureDTO.getSigned());
            Signature updateSign = signatureRepo.save(sign1);

            sendCompleteSignedDocument(documentId);


            return mapperConfig.toSignatureDTO(updateSign);

        }
        else{
            return null;
        }
    }


    @Override
    public void sendCompleteSignedDocument(Long documentId) {
        Set<Boolean> signedStatuses = signatureRepo.checkAllSignature(documentId);
        if (signedStatuses.size() == 1 && signedStatuses.contains(true)) {

            String userEmail = "";
            Optional<Document> document = documentRepo.findById(documentId);
            if (document.isPresent()){
                Document document1 = document.get();
                document1.setStatus(DocumentStatus.SIGNED);
                Document savedDocument = documentRepo.save(document1);
                userEmail = savedDocument.getUser().getEmail();
            }

            String encodedDocumentId = encode(String.valueOf(documentId));

            Set<String> emails = signatureRepo.getRecipientEmailsOfDocument(documentId);
            emails.add(userEmail);
            emails.stream().forEach(email->{
                String url = "http://192.168.5.219:3000/final-document/" + encodedDocumentId;
                emailService.sendEmail(email, "Document Completed", url);

            });

        }
    }


    private String encode(String value){
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

}
