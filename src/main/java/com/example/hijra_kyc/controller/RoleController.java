package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.PermissionOutDto;
import com.example.hijra_kyc.dto.RoleInDto;
import com.example.hijra_kyc.dto.RoleOutDto;
import com.example.hijra_kyc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // Create a new role
    @PostMapping
    public ResponseEntity<RoleOutDto> createRole(@RequestBody RoleInDto dto) {
        RoleOutDto created = roleService.createRole(dto);
        return ResponseEntity.ok(created);
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleOutDto>> getAllRoles() {
        List<RoleOutDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Get one role by ID
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleOutDto> getRole(@PathVariable String roleId) {
        RoleOutDto role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }
    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<
            Set<PermissionOutDto>> getRolePermissions(@PathVariable String roleId) {
        Set<PermissionOutDto> permissions = roleService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

}
