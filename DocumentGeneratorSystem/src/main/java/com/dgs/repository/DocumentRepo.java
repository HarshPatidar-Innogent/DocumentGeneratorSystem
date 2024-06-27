package com.dgs.repository;

import com.dgs.entity.Document;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface DocumentRepo extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE d.user.id = :userId")
    public List<Document> findAllByUserId(@PathParam("userId") Long userId);

    @Query(value = "select count(*) from Document where user_id = :userId",nativeQuery = true)
    public Integer countDocument(@PathParam("userId") Long userId);
}
