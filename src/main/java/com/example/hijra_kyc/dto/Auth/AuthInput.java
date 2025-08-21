package com.example.hijra_kyc.dto.Auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthInput {
    private String username;
    private String password;
}
