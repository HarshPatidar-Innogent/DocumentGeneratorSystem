package com.dgs.repository;

import com.dgs.entity.Placeholder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceholderRepo extends JpaRepository<Placeholder, Long> {
}
