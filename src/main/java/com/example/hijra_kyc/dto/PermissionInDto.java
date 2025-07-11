package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionInDto {
    private String permissionId;
    private String permissionCategory;
    private String permissionDisplayName;
    private String permissionName;
    private String recordStatus;
}
