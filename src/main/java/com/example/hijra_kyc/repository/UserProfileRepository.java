package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{
    
}
