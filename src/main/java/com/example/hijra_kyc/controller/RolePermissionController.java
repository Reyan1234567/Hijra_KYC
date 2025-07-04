package com.example.hijra_kyc.controller;

import com.example.hijra_kyc.dto.RolePermissionInDto;
import com.example.hijra_kyc.dto.RolePermissionOutDto;
import com.example.hijra_kyc.service.RolePermissionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final RolePermissionService service;

    public RolePermissionController(RolePermissionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<RolePermissionOutDto> create(@Valid @RequestBody RolePermissionInDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RolePermissionOutDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RolePermissionOutDto> update(@PathVariable String id, @Valid @RequestBody RolePermissionInDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/filter-by-role/{roleId}")
    public ResponseEntity<List<RolePermissionOutDto>> filterByRoleId(@PathVariable String roleId) {
        return ResponseEntity.ok(service.filterByRoleId(roleId));
    }

    @GetMapping("/filter-by-permission/{permissionId}")
    public ResponseEntity<List<RolePermissionOutDto>> filterByPermissionId(@PathVariable String permissionId) {
        return ResponseEntity.ok(service.filterByPermissionId(permissionId));
    }
}
