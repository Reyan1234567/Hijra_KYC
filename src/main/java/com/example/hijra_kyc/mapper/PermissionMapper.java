package com.example.hijra_kyc.mapper;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.PermissionDto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.model.Permission;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PermissionMapper {

    public Permission toEntity(PermissionInDto dto) {
        return Permission.builder()
                .permissionName(dto.getPermissionName())
                .permissionDisplayName(dto.getPermissionDisplayName())
                .permissionCategory(dto.getPermissionCategory())
                .recordStatus(dto.getRecordStatus())
                .build();
    }

    public  PermissionOutDto toDto(Permission entity) {
        return PermissionOutDto.builder()
                .permissionId(entity.getPermissionId())
                .permissionName(entity.getPermissionName())
                .permissionDisplayName(entity.getPermissionDisplayName())
                .permissionCategory(entity.getPermissionCategory())
                .recordStatus(entity.getRecordStatus())
                .build();
    }
}
