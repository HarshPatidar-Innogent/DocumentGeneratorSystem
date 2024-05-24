package com.dgs.repository;

import com.dgs.entity.Placeholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceholderRepo extends JpaRepository<Placeholder, Long> {

    //    @Query("SELECT p FROM placeholder p WHERE p.templateId = :templateId")
    @Query(value = "select * from placeholder where templateId = :templateId", nativeQuery = true)
    public List<Placeholder> getAllPlaceholderByTemplateId(@Param("templateId") Long templateId);
}
