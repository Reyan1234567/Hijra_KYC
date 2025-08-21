package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Long>{
    User findByUsername(String username);
}