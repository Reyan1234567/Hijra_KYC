package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionOutDto {
    private String rolePermissionId;
    private String roleId;
    private String permissionId;
    private String recordStatus;
}