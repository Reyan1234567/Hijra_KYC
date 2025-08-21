package com.example.hijra_kyc.dto.Auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RefreshResponse {
    String accessToken;
    String refreshToken;
}
