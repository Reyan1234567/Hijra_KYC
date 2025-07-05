package com.example.hijra_kyc;

import com.example.hijra_kyc.model.Branch;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kyc_user_profile")
public class KycUserProfile {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "user_profile_id", length = 100)
    private String userProfileId;

    @Size(max = 100)
    @Column(name = "username", length = 100)
    private String username;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;

    @Lob
    @Column(name = "address")
    private String address;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 255)
    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "branch_id")
    private Branch branch;

}