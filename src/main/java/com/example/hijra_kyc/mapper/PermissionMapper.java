package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionOutDto;
import com.example.hijra_kyc.model.Permission;

public class PermissionMapper {

    public static Permission toEntity(PermissionInDto dto) {
        return Permission.builder()
                .permissionId(dto.getPermissionId())
                .permissionName(dto.getPermissionName())
                .permissionDisplayName(dto.getPermissionDisplayName())
                .permissionCategory(dto.getPermissionCategory())
                .recordStatus(dto.getRecordStatus())
                .build();
    }

    public static PermissionOutDto toDto(Permission entity) {
        return PermissionOutDto.builder()
                .permissionId(entity.getPermissionId())
                .permissionName(entity.getPermissionName())
                .permissionDisplayName(entity.getPermissionDisplayName())
                .permissionCategory(entity.getPermissionCategory())
                .recordStatus(entity.getRecordStatus())
                .build();
    }
}
