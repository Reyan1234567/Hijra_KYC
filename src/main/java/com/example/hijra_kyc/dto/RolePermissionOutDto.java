package com.example.hijra_kyc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolePermissionOutDto {
    private String rolePermissionId;  // String because your entity uses String IDs
    private String roleId;
    private String roleName;          // optional, for better API clarity
    private String permissionId;
    private String permissionName;    // optional, for better API clarity
    private String recordStatus;
}
