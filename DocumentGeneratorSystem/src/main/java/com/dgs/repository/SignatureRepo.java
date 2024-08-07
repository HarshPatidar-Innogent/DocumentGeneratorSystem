package com.dgs.repository;

import com.dgs.entity.Signature;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SignatureRepo extends JpaRepository<Signature, Long> {
//    @Query(value = "select * from signature where document_id=:id", nativeQuery = true)
    @Query("SELECT s FROM Signature s WHERE s.document.documentId =:id")
    List<Signature> getAllSignaturesOfDocument(@PathParam("id") Long id);

//    @Query(value = "select * from signature where recipient_email=:recipientEmail and document_id=:documentId", nativeQuery = true)
    @Query("SELECT s FROM Signature s WHERE s.recipientEmail =:recipientEmail AND s.document.documentId =:documentId")
    Signature findByRecipientEmail(@PathParam("recipientEmail") String recipientEmail, @PathParam("documentId") Long documentId);

//    @Query(value = "select * from signature where document_id=:documentId and placeholder=:placeholder", nativeQuery = true)
    @Query("SELECT s FROM Signature s WHERE s.document.documentId =:documentId AND s.placeholder =:placeholder")
    Optional<Signature> findByDocumentIdAndPlaceholder(@PathParam("documentId") Long documentId, @PathParam("placeholder") String placeholder);

//    @Query(value = "SELECT DISTINCT(signed) FROM signature WHERE document_id = :documentId", nativeQuery = true)
    @Query("SELECT DISTINCT(s.signed) FROM Signature s WHERE s.document.documentId =:documentId")
    Set<Boolean> checkAllSignature(@PathParam("documentId") Long documentId);

//    @Query(value = "select recipient_email from signature where document_id=:documentId", nativeQuery = true)
    @Query("SELECT s.recipientEmail FROM Signature s WHERE s.document.documentId =:documentId")
    Set<String> getRecipientEmailsOfDocument(@PathParam("documentID") Long documentId);
}
