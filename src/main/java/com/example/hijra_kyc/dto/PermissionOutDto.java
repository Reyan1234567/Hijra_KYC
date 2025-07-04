package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionOutDto {
    private String permissionId;
    private String permissionName;
    private String permissionDisplayName;
    private String permissionCategory;
    private String recordStatus;
}
