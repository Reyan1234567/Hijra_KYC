package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleInDto {
    private String roleId;
    private String roleName;
    private String recordStatus;
    private Set<PermissionOutDto> permissions;
    private Set<String> permissionIds;

}

