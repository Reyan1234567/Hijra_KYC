package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.RolePermissionInDto;
import com.example.hijra_kyc.dto.RolePermissionOutDto;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.RolePermission;

public final class RolePermissionMapper {

    private RolePermissionMapper() {}

    // Convert DTO + Role + Permission entities to RolePermission entity
    public static RolePermission toEntity(RolePermissionInDto dto, Role role, Permission permission) {
        if (dto == null || role == null || permission == null) {
            throw new IllegalArgumentException("DTO, Role, and Permission must not be null");
        }
        return RolePermission.builder()
                // Don't set rolePermissionId here - let the service or DB handle it
                .role(role)
                .permission(permission)
                .recordStatus(dto.getRecordStatus())
                .build();
    }


    // Convert RolePermission entity to output DTO
    public static RolePermissionOutDto toDto(RolePermission entity) {
        if (entity == null || entity.getRole() == null || entity.getPermission() == null) {
            throw new IllegalArgumentException("RolePermission entity and its Role and Permission must not be null");
        }
        return RolePermissionOutDto.builder()
                .rolePermissionId(String.valueOf(entity.getRolePermissionId()))
                .roleId(entity.getRole().getRoleId())
                .roleName(entity.getRole().getRoleName())               // Include for better info
                .permissionId(entity.getPermission().getPermissionId())
                .permissionName(entity.getPermission().getPermissionName()) // Include for better info
                .recordStatus(entity.getRecordStatus())
                .build();
    }
}