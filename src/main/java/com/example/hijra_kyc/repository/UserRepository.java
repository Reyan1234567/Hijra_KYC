package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.KycUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<KycUserProfile, Long> {
}
