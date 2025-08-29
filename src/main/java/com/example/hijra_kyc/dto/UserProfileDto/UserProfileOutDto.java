package com.example.hijra_kyc.dto.UserProfileDto;

import lombok.Data;

@Data
public class UserProfileOutDto {
    private Long id;

    private Long branchId;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String branchAddress;
    private String phoneNumber;
    private String photoUrl;
    private String roleName;
    private String status;

}
