package com.example.hijra_kyc.dto.UserProfileDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDto {
    @NotNull
    private Long id;
    @NotNull
    private String base64;
}
