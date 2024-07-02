package com.dgs.repository;

import com.dgs.entity.AccessControl;
import com.dgs.enums.DesignationPermission;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessControlRepo extends JpaRepository<AccessControl,Long> {

//    @Query(value = "select * from access_control where template_id=:templateId", nativeQuery = true)
    @Query("SELECT a FROM AccessControl a WHERE a.template.templateId =:templateId")
    List<AccessControl> findByTemplateId(@PathParam("templateId") Long templateId);

//    @Query(value = "select template_id from access_control where user_id=:userId", nativeQuery = true)
    @Query("SELECT a.template.templateId FROM AccessControl a WHERE a.user.userId =:userId")
    List<Long> findTemplateIdByUserId(@PathParam("userId") Long userId);

//    @Query(value = "select * from access_control where user_id=:userId", nativeQuery = true)
    @Query("SELECT a FROM AccessControl a WHERE a.user.userId =:userId")
    List<AccessControl> findAllByUserId(@PathParam("userId") Long userId);

    @Query("SELECT a.template.templateId FROM AccessControl a WHERE a.owner.userId =:userId")
    List<Long> getAllAccessTemplateId(@PathParam("userId") Long userId);
}
