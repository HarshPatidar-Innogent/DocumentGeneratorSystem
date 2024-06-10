package com.dgs.repository;

import com.dgs.DTO.SignatureDTO;
import com.dgs.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepo extends JpaRepository<Signature,Long> {

}
