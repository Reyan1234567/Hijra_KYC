package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.RoleInDto;
import com.example.hijra_kyc.dto.RoleOutDto;
import com.example.hijra_kyc.dto.PermissionOutDto;
import com.example.hijra_kyc.mapper.RoleMapper;
import com.example.hijra_kyc.mapper.PermissionMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.repository.PermissionRepository;
import com.example.hijra_kyc.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;  // Keep this injected

    // Remove PermissionMapper from constructor
    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public RoleOutDto createRole(RoleInDto dto) {
        var role = RoleMapper.toEntity(dto);
        var savedRole = roleRepository.save(role);
        return RoleMapper.toDto(savedRole);
    }

    public List<RoleOutDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleOutDto getRoleById(String roleId) {
        var role = roleRepository.findById(roleId).orElse(null);
        return role != null ? RoleMapper.toDto(role) : null;
    }

    public List<PermissionOutDto> getPermissionsByRole(String roleId) {
        List<Permission> permissions = permissionRepository.findPermissionsByRoleId(roleId);
        // Call static method directly on PermissionMapper
        return permissions.stream()
                .map(PermissionMapper::toDto)
                .collect(Collectors.toList());
    }
}