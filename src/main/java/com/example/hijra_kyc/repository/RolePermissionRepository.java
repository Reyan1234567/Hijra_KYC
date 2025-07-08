package com.example.hijra_kyc.repository;

import com.example.hijra_kyc.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {


    List<RolePermission> findByRole_RoleId(String roleId);


    List<RolePermission> findByPermission_PermissionId(String permissionId);


    RolePermission findByRole_RoleIdAndPermission_PermissionId(String roleId, String permissionId);


    void deleteByRole_RoleIdAndPermission_PermissionId(String roleId, String permissionId);
}