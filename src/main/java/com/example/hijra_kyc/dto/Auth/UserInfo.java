package com.example.hijra_kyc.dto.Auth;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo{
    public String username;
    public Long userId;
    public String role;
}