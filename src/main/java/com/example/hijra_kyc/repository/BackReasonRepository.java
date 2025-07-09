package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.BackReason;

public interface BackReasonRepository extends JpaRepository<BackReason, Long> {
    
}
