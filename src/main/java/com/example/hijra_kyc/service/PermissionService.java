package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.PermissionInDto;
import com.example.hijra_kyc.dto.PermissionOutDto;
import com.example.hijra_kyc.mapper.PermissionMapper;
import com.example.hijra_kyc.model.Permission;
import com.example.hijra_kyc.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public PermissionOutDto createPermission(PermissionInDto dto) {
        Permission permission = PermissionMapper.toEntity(dto);
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.toDto(savedPermission);
    }

    public List<PermissionOutDto> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::toDto)
                .collect(Collectors.toList());
    }

    public PermissionOutDto getPermissionById(String permissionId) {
        Permission permission = permissionRepository.findById(permissionId).orElse(null);
        return permission != null ? PermissionMapper.toDto(permission) : null;
    }
}
