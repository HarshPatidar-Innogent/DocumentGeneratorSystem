package com.dgs.repository;

import com.dgs.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    Optional<Object> findByDepartmentName(String name);
}
