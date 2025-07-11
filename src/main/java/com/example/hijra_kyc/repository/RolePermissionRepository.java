package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {


    List<RolePermission> findByRole_RoleId(Long roleId);


    List<RolePermission> findByPermission_PermissionId(Long permissionId);


    RolePermission findByRole_RoleIdAndPermission_PermissionId(Long roleId, Long permissionId);


    void deleteByRole_RoleIdAndPermission_PermissionId(Long roleId, Long permissionId);
}