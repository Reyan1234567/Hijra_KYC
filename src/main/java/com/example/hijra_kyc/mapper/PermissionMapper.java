package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionOutDto;
import com.example.hijra_kyc.model.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    // Convert PermissionInDto -> Permission (Entity)
    public Permission toModel(PermissionInDto dto) {
        return Permission.builder()
                .permissionId(dto.getPermissionId())
                .permissionCategory(dto.getPermissionCategory())
                .permissionDisplayName(dto.getPermissionDisplayName())
                .permissionName(dto.getPermissionName())
                .recordStatus(dto.getRecordStatus())
                .build();
    }

    // Convert Permission (Entity) -> PermissionOutDto
    public PermissionOutDto toOutDto(Permission permission) {
        return PermissionOutDto.builder()
                .permissionId(permission.getPermissionId())
                .permissionCategory(permission.getPermissionCategory())
                .permissionDisplayName(permission.getPermissionDisplayName())
                .permissionName(permission.getPermissionName())
                .recordStatus(permission.getRecordStatus())
                .build();
    }
}
