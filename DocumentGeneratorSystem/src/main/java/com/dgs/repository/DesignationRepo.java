package com.dgs.repository;

import com.dgs.entity.Designation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DesignationRepo extends JpaRepository<Designation,Long> {
    Optional<Designation> findByDesignationName(String name);
    @Modifying
    @Transactional
//    @Query(value = "DELETE FROM designation WHERE designation_id = :designationId", nativeQuery = true)
    @Query("DELETE FROM Designation d WHERE d.designationId =:designationId")
    void deleteByDesignationId( @Param("designationId") Long designationId);
}
