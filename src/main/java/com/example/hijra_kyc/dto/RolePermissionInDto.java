package com.example.hijra_kyc.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePermissionInDto {
    private Long roleId;
    private Long permissionId;
    private String recordStatus;
}
