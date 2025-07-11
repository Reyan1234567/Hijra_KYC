package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.SystemLog;
import org.springframework.stereotype.Service;

@Service
public interface SystemLogRepository extends JpaRepository<SystemLog, Integer>{
    
}
