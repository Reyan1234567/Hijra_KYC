package com.example.hijra_kyc.dto.UserProfileDto;

import lombok.Data;

@Data
public class UserProfileInDto {

    private Long branchId;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private String phoneNumber;
    private String photoUrl;

}
