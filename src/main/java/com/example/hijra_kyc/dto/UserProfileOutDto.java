package com.example.hijra_kyc.dto;

import lombok.Data;

@Data
public class UserProfileOutDto {
    private Long id;
    private String userID;
    private Long branchId;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String branchAddress;
    private String phoneNumber;
}
