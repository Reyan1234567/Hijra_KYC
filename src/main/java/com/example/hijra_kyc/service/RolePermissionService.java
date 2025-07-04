package com.example.hijra_kyc.service;

import com.example.hijra_kyc.dto.RolePermissionInDto;
import com.example.hijra_kyc.dto.RolePermissionOutDto;
import com.example.hijra_kyc.mapper.RolePermissionMapper;
import com.example.hijra_kyc.model.RolePermission;
import com.example.hijra_kyc.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {

    private final RolePermissionRepository repository;

    public RolePermissionService(RolePermissionRepository repository) {
        this.repository = repository;
    }

    public RolePermissionOutDto create(RolePermissionInDto dto) {
        RolePermission entity = RolePermissionMapper.toEntity(dto);
        return RolePermissionMapper.toDto(repository.save(entity));
    }

    public List<RolePermissionOutDto> getAll() {
        return repository.findAll().stream().map(RolePermissionMapper::toDto).collect(Collectors.toList());
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public RolePermissionOutDto update(String id, RolePermissionInDto dto) {
        Optional<RolePermission> optional = repository.findById(id);
        if (optional.isPresent()) {
            RolePermission updated = RolePermissionMapper.toEntity(dto);
            updated.setRolePermissionId(id);
            return RolePermissionMapper.toDto(repository.save(updated));
        }
        return null;
    }

    public List<RolePermissionOutDto> filterByRoleId(String roleId) {
        return repository.findByRoleId(roleId).stream().map(RolePermissionMapper::toDto).collect(Collectors.toList());
    }

    public List<RolePermissionOutDto> filterByPermissionId(String permissionId) {
        return repository.findByPermissionId(permissionId).stream().map(RolePermissionMapper::toDto).collect(Collectors.toList());
    }
}