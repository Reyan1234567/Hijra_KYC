package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

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
    @Column(unique = true)
    private String userName;

    @NotBlank(message = "gender required")
    private String gender;

    @NotBlank(message = "phone number required")
    @Column(unique = true)
    private String phoneNumber;

    @NotNull(message = "status required")
    @Column(columnDefinition = "int default 1")
    private int status;

    @ManyToOne
    @JoinColumn(name="branchId", nullable = false)
    private Branch branch;

    @Column(columnDefinition = "int default 0")
    private int loginStatus;

    @OneToMany(mappedBy = "maker")
    List<MakeForm> makeFormList;

    private String photoUrl;
}
