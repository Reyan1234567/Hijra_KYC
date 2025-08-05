package com.example.hijra_kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hijra_kyc.model.user.Users;

public interface UserRepo extends JpaRepository<Users,Long>{
    Users findByUsername(String username);

} 