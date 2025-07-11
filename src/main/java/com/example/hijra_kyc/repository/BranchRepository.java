package com.example.hijra_kyc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.Branch;
import org.springframework.stereotype.Service;

@Service
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    
}
