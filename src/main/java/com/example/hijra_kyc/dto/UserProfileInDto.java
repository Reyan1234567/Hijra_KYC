package com.example.hijra_kyc.dto;

import lombok.Data;

@Data
public class UserProfileInDto {
    private String userID;
    private int branchId;
    private String roleId;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String branchAddress;
    private String phoneNumber;
}
