package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.BackReason;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface BackReasonRepository extends JpaRepository<BackReason, Long> {
    @Query("select b from BackReason b where b.makeId.id=:id")
    BackReason findByMakeId(@Param("id") Long id);
}
