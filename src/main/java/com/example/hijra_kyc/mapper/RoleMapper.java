package com.example.hijra_kyc.mapper;

import com.example.hijra_kyc.dto.*;
import com.example.hijra_kyc.model.*;
import com.example.hijra_kyc.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final PermissionRepository permissionRepository;

    public Role toModel(RoleInDto dto) {
        Set<Permission> permissions = dto.getPermissionIds().stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + id)))
                .collect(Collectors.toSet());

        return Role.builder()
                .roleId(dto.getRoleId())
                .roleName(dto.getRoleName())
                .recordStatus(dto.getRecordStatus())
                .permissions(permissions)
                .build();
    }

    public RoleOutDto toOutDto(Role role) {
        Set<PermissionOutDto> permissionDtos = role.getPermissions().stream()
                .map(this::mapPermission)
                .collect(Collectors.toSet());

        return new RoleOutDto(
                role.getRoleId(),
                role.getRoleName(),
                role.getRecordStatus(),
                permissionDtos
        );
    }

    public PermissionOutDto mapPermission(Permission p) {
        return new PermissionOutDto(
                p.getPermissionId(),
                p.getPermissionCategory(),
                p.getPermissionDisplayName(),
                p.getPermissionName(),
                p.getRecordStatus()
        );
    }
}
