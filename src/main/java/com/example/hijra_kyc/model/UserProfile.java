package com.example.hijra_kyc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long id;
    @NotBlank(message = "user id required")
    @Column(unique = true)
    private String userId;
    @NotNull(message = "commented by is required")
    @ManyToOne
    @JoinColumn(name = "role_Id", referencedColumnName = "role_Id")
    private Role roleId;
    @NotBlank(message = "first name required")
    private String firstName;
    @NotBlank(message = "last name required")
    private String lastName;
    @NotBlank(message = "user name required")
    private String userName;
    @NotBlank(message = "gender required")
    private String gender;
    @NotBlank(message = "phone number required")
    private String phoneNumber;
    @NotBlank(message = "status required")
    private String status;
    @ManyToOne
    @JoinColumn(name="branchId", nullable = false)
    private Branch branch;
}
