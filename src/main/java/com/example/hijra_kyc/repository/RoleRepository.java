package com.example.hijra_kyc.repository;


import com.example.hijra_kyc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleRepository extends JpaRepository<Role, String> {
}