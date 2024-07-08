package com.dgs.repository;

import com.dgs.entity.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    Optional<Department> findByDepartmentName(String name);

    @Modifying
    @Transactional
//    @Query(value = "DELETE FROM department WHERE department_id = :departmentId", nativeQuery = true)
    @Query("DELETE from Department d WHERE d.departmentId =:departmentId")
    void deleteByDepartmentId( @Param("departmentId") Long departmentId);
}
