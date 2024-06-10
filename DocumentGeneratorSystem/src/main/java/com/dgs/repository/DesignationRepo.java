package com.dgs.repository;

import com.dgs.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignationRepo extends JpaRepository<Designation,Long> {
    Optional<Object> findByDesignationName(String name);
}
