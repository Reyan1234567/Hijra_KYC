package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {
    List<RolePermission> findByRoleId(String roleId);
    List<RolePermission> findByPermissionId(String permissionId);
}