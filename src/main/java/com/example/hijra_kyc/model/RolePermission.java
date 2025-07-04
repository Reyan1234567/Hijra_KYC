package com.example.hijra_kyc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    private String rolePermissionId;
    private String roleId;
    private String permissionId;
    private String recordStatus;
}