package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.RoleInDto;
import com.example.hijra_kyc.dto.RoleOutDto;
import com.example.hijra_kyc.model.Role;

public class RoleMapper {

    public static Role toEntity(RoleInDto dto) {
        return Role.builder()
                .roleId(dto.getRoleId())
                .roleName(dto.getRoleName())
                .recordStatus(dto.getRecordStatus())
                .build();
    }

    public static RoleOutDto toDto(Role entity) {
        RoleOutDto dto = new RoleOutDto();
        dto.setRoleId(entity.getRoleId());
        dto.setRoleName(entity.getRoleName());
        dto.setRecordStatus(entity.getRecordStatus());
        return dto;
    }
}
