package com.dgs.repository;

import com.dgs.entity.AccessControl;
import com.dgs.enums.DesignationPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessControlRepo extends JpaRepository<AccessControl,Long> {

}
