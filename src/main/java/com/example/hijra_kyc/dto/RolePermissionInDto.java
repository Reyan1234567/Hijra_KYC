package com.example.hijra_kyc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePermissionInDto {
    private String roleId;
    private String permissionId;
    private String recordStatus;
}
