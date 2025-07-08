package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.RolePermissionInDto;
import com.example.hijra_kyc.dto.RolePermissionOutDto;
import com.example.hijra_kyc.mapper.RolePermissionMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.model.Role;
import com.example.hijra_kyc.model.RolePermission;
import com.example.hijra_kyc.repository.PermissionRepository;
import com.example.hijra_kyc.repository.RolePermissionRepository;
import com.example.hijra_kyc.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionOutDto assignPermissionToRole(RolePermissionInDto dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(dto.getPermissionId())
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        RolePermission rolePermission = RolePermissionMapper.toEntity(dto, role, permission);
        RolePermission saved = rolePermissionRepository.save(rolePermission);
        return RolePermissionMapper.toDto(saved);
    }

    public List<RolePermissionOutDto> getAllPermissionsByRole(String roleId) {
        return rolePermissionRepository.findAll().stream()
                .filter(rp -> rp.getRole().getRoleId().equals(roleId))
                .map(RolePermissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RolePermissionOutDto> getAllRolesByPermission(String permissionId) {
        return rolePermissionRepository.findAll().stream()
                .filter(rp -> rp.getPermission().getPermissionId().equals(permissionId))
                .map(RolePermissionMapper::toDto)
                .collect(Collectors.toList());
    }
}
