package com.example.hijra_kyc.model;

import com.example.hijra_kyc.KycUserProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kyc_make_forms")
public class MakeForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "maker_user_id", referencedColumnName = "id")
    private KycUserProfile maker;

    @ManyToOne
    @JoinColumn(name = "ho_user_id", referencedColumnName = "id")
    private KycUserProfile ho;

    @Size(max = 50)
    @Column(name = "cif", length = 50)
    private String cif;

    @Size(max = 100)
    @Column(name = "customer_account", length = 100)
    private String customerAccount;

    @Size(max = 20)
    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    @Size(max = 100)
    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(name = "make_time")
    private Instant makeTime;

    @Column(name = "ho_assign_time")
    private Instant hoAssignTime;

    @Column(name = "ho_action_time")
    private Instant hoActionTime;


    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "imageMake")
    private List<Image> images=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName="branch_id")
    private Branch branchId;
}