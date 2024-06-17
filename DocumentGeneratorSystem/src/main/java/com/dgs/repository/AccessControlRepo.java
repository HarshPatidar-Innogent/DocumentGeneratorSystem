package com.dgs.repository;

import com.dgs.entity.AccessControl;
import com.dgs.enums.DesignationPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessControlRepo extends JpaRepository<AccessControl,Long> {
   boolean existsByTemplate_TemplateIdAndDepartment_DepartmentIdAndDesignation_DesignationId(Long templateId, Long departmentId, Long designationId);

   @Query(value = "select * from access_control where template_id=:templateId",nativeQuery = true)
   List<AccessControl> findAllByTemplateId(Long templateId);
}
