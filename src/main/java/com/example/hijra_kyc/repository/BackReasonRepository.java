package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.BackReason;
import org.springframework.stereotype.Service;

@Service
public interface BackReasonRepository extends JpaRepository<BackReason, Long> {
    
}
