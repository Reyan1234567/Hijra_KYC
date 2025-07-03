package com.example.hijra_kyc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "user id required")
    private String userId;
    @NotNull(message = "branch id required")
    private int branchId;
    @NotBlank(message = "role id required")
    private String roleId;
    @NotBlank(message = "first name required")
    private String firstName;
    @NotBlank(message = "last name required")
    private String lastName;
    @NotBlank(message = "user name required")
    private String userName;
    @NotBlank(message = "gender required")
    private String gender;
    @NotBlank(message = "branch address required")
    private String branchAddress;
    @NotBlank(message = "phone number required")
    private String phoneNumber;

}
