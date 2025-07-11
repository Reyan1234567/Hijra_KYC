package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //Optional<Role> findByRoleId(Long roleId);
}

