package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionRepository extends JpaRepository<Permission, String> {

    @Query("SELECT p FROM Permission p JOIN RolePermission rp ON p.permissionId = rp.permission.permissionId " +
            "WHERE rp.role.roleId = :roleId AND rp.recordStatus = 'ACTIVE'")
    List<Permission> findPermissionsByRoleId(@Param("roleId") String roleId);
}