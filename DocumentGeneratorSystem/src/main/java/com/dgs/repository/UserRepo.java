package com.dgs.repository;

import com.dgs.DTO.UserDTO;
import com.dgs.entity.Department;
import com.dgs.entity.Designation;
import com.dgs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository <User, Long>{
    Optional<User> findByEmail(String email);

    boolean existsByDepartment(Department department);

    boolean existsByDesignation(Designation designation);
}
