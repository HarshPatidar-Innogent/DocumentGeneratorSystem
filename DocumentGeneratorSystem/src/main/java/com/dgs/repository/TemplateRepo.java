package com.dgs.repository;

import com.dgs.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.temporal.TemporalAccessor;

public interface TemplateRepo extends JpaRepository<Template, Long> {
}
