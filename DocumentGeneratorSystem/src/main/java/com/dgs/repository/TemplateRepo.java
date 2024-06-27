package com.dgs.repository;

import com.dgs.entity.Template;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateRepo extends JpaRepository<Template, Long> {
    @Query(value = "select * from template where user_id=:userId",nativeQuery = true)
    public List<Template> getAllTemplatesOfUser(@PathParam("userId") Long userId);

}
