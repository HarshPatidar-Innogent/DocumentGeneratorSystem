package com.dgs.repository;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SignatureRepo extends JpaRepository<Signature,Long> {
    @Query(value = "select * from signature where document_id=:id", nativeQuery = true)
    List<Signature> getAllSignaturesOfDocument(@PathParam("id") Long id);

    @Query(value = "select * from signature where document_id=:documentId and placeholder=:placeholder", nativeQuery = true)
    Optional<Signature> findByDocumentIdAndPlaceholder(@PathParam("documentId") Long documentId,@PathParam("placeholder") String placeholder);

}
