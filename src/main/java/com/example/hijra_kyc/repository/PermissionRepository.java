package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    Optional<Permission> findByPermissionId(String permissionId);
}
