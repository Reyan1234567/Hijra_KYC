package com.example.hijra_kyc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{
    Optional<UserProfile> findByUserId(String userId);

}
