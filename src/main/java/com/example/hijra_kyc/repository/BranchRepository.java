package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hijra_kyc.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByName(String Name);
}
