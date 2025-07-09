package com.example.hijra_kyc.dto.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleInDto {
    private String roleName;
    private String recordStatus;
}

