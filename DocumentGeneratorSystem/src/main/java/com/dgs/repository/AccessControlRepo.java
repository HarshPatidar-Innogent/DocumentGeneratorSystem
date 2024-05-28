package com.dgs.repository;

import com.dgs.entity.AccessControl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessControlRepo extends JpaRepository<AccessControl,Long> {
//        boolean existsByTemplateIdAndDepartmentIdAndDesignationId(Long templateId, Long departmentId , Long designationId);
boolean existsByTemplate_TemplateIdAndDepartment_DepartmentIdAndDesignation_DesignationId(Long templateId, Long departmentId, Long designationId);
}
