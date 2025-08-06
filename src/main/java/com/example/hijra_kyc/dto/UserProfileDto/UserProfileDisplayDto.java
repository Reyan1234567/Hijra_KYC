package com.example.hijra_kyc.dto.UserProfileDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDisplayDto {
    Long id;
    String name;
    String username;
    String userId;
    String  branch;
    String role;
    String phoneNumber;
    int status;
    int PresentStatus;
    String profilePicture;
}
