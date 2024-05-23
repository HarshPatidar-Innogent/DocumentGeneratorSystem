package com.dgs.repository;

import com.dgs.entity.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepo extends JpaRepository<AuditTrail,Long> {
}
