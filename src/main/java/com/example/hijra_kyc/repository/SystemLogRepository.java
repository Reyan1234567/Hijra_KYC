package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.SystemLog;

public interface SystemLogRepository extends JpaRepository<SystemLog, Long>{
    
}
