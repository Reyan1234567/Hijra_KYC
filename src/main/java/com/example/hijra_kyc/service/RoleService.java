package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.PermissionDto.PermissionOutDto;
import com.example.hijra_kyc.dto.RoleDto.RoleInDto;
import com.example.hijra_kyc.dto.RoleDto.RoleOutDto;
import com.example.hijra_kyc.mapper.RoleMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    // Write operation - must NOT be readOnly
    @Transactional
    public RoleOutDto createRole(RoleInDto dto) {
        Role role = roleMapper.toModel(dto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toOutDto(savedRole);
    }

    // Read operation - readOnly = true for optimization
    @Transactional(readOnly = true)
    public List<RoleOutDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toOutDto)
                .collect(Collectors.toList());
    }

    // Read operation - readOnly = true
    @Transactional(readOnly = true)
    public RoleOutDto getRole(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        return roleMapper.toOutDto(role);
    }

    // Read operation for permissions - readOnly = true
    @Transactional(readOnly = true)
    public Set<PermissionOutDto> getPermissionsByRoleId(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        // Initialize the lazy collection safely
        Set<Permission> permissions = new HashSet<>(role.getPermissions());
        return permissions.stream()
                .map(roleMapper::mapPermission)
                .collect(Collectors.toSet());
    }
}
