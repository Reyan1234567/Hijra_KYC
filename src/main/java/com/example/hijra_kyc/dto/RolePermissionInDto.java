package com.example.hijra_kyc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionInDto {
    @NotBlank(message = "RolePermissionId is required")
    private String rolePermissionId;

    @NotBlank(message = "RoleId is required")
    private String roleId;

    @NotBlank(message = "PermissionId is required")
    private String permissionId;

    @NotBlank(message = "RecordStatus is required")
    private String recordStatus;
}