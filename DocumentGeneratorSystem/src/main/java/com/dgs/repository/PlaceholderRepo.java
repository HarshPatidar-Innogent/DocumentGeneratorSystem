package com.dgs.repository;

import com.dgs.entity.Placeholder;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceholderRepo extends JpaRepository<Placeholder, Long> {

    //    @Query("SELECT p FROM placeholder p WHERE p.templateId = :templateId")
    @Query("SELECT p FROM Placeholder p WHERE p.template = :templateId")
    public List<Placeholder> getAllPlaceholderByTemplateId(@Param("templateId") Long templateId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Placeholder p WHERE p.template = :templateId")
    void deleteAllByTemplateId(@PathParam("templateId") Long templateId);
}
