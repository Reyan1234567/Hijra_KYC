package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.RolePermissionInDto;
import com.example.hijra_kyc.dto.RolePermissionOutDto;
import com.example.hijra_kyc.model.RolePermission;

public class RolePermissionMapper {

    public static RolePermission toEntity(RolePermissionInDto dto) {
        return RolePermission.builder()
                .rolePermissionId(dto.getRolePermissionId())
                .roleId(dto.getRoleId())
                .permissionId(dto.getPermissionId())
                .recordStatus(dto.getRecordStatus())
                .build();
    }

    public static RolePermissionOutDto toDto(RolePermission entity) {
        return RolePermissionOutDto.builder()
                .rolePermissionId(entity.getRolePermissionId())
                .roleId(entity.getRoleId())
                .permissionId(entity.getPermissionId())
                .recordStatus(entity.getRecordStatus())
                .build();
    }
}
