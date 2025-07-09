package com.example.hijra_kyc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    
}
