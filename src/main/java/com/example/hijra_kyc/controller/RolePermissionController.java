package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.RolePermissionInDto;
import com.example.hijra_kyc.dto.RolePermissionOutDto;
import com.example.hijra_kyc.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PostMapping("/assign")
    public ResponseEntity<RolePermissionOutDto> assignPermissionToRole(@RequestBody RolePermissionInDto dto) {
        return ResponseEntity.ok(rolePermissionService.assignPermissionToRole(dto));
    }

    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<List<RolePermissionOutDto>> getPermissionsByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(rolePermissionService.getAllPermissionsByRole(roleId));
    }

    @GetMapping("/by-permission/{permissionId}")
    public ResponseEntity<String> getRolesByPermission(@PathVariable String permissionId) {
        return ResponseEntity.ok(rolePermissionService.getAllRolesByPermission(permissionId));
    }

}