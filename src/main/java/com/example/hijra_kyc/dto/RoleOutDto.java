package com.example.hijra_kyc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleOutDto {
    private Long roleId;
    private String roleName;
    private String recordStatus;
}

