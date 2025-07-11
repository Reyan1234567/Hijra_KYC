package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.dto.RoleDto.RoleInDto;
import com.example.hijra_kyc.dto.RoleDto.RoleOutDto;
import com.example.hijra_kyc.mapper.RoleMapper;
import com.example.hijra_kyc.mapper.PermissionMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.repository.PermissionRepository;
import com.example.hijra_kyc.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;  // Keep this injected
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    public RoleOutDto createRole(RoleInDto dto) {
        var role = roleMapper.toEntity(dto);
        var savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    public List<RoleOutDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleOutDto getRoleById(Long roleId) {
        var role = roleRepository.findById(roleId).orElse(null);
        return role != null ? roleMapper.toDto(role) : null;
    }

    public List<PermissionOutDto> getPermissionsByRole(Long roleId) {
        List<Permission> permissions = permissionRepository.findPermissionsByRoleId(roleId);
        // Call static method directly on PermissionMapper
        return permissions.stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }
}