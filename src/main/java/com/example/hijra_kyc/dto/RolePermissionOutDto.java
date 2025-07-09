package com.example.hijra_kyc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolePermissionOutDto {
    private Long rolePermissionId;  // String because your entity uses String IDs
    private Long roleId;
    private String roleName;          // optional, for better API clarity
    private Long permissionId;
    private String permissionName;    // optional, for better API clarity
    private String recordStatus;
}
