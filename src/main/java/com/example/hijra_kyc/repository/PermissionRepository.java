package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}