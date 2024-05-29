package com.dgs.repository;

import com.dgs.entity.Document;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DocumentRepo extends JpaRepository<Document, Long> {

    @Query(nativeQuery = true, value = "SELECT d FROM DOCUMENT d WHERE d.user.userId = :userId")
    public List<Document> findAllByUserId(@PathParam("userId") Long userId);
}
