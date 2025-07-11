package com.example.hijra_kyc.mapper;

import org.springframework.stereotype.Service;

import com.example.hijra_kyc.dto.RoleDto.RoleInDto;
import com.example.hijra_kyc.dto.RoleDto.RoleOutDto;
import com.example.hijra_kyc.model.Role;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RoleMapper {

    public Role toEntity(RoleInDto dto) {
        return Role.builder()
                .roleName(dto.getRoleName())
                .recordStatus(dto.getRecordStatus())
                .build();
    }

    public RoleOutDto toDto(Role entity) {
        RoleOutDto dto = new RoleOutDto();
        dto.setRoleId(entity.getRoleId());
        dto.setRoleName(entity.getRoleName());
        dto.setRecordStatus(entity.getRecordStatus());
        return dto;
    }
}
