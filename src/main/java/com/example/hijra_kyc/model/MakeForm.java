package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kyc_make_forms")
public class MakeForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maker_user_id", referencedColumnName = "id")
    private UserProfile maker;

    @ManyToOne
    @JoinColumn(name = "ho_user_id", referencedColumnName = "id")
    private UserProfile ho;

    @Size(max = 50)
    @Column(name = "cif", length = 50)
    private String cif;

    @Size(max = 100)
    @Column(name = "customer_account", length = 100, unique=true)
    private String customerAccount;

    @Size(max = 20)
    @Column(name = "customer_phone", length = 20, unique=true)
    private String customerPhone;

    @Size(max = 100)
    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(name = "make_time", columnDefinition = "timestamp default current_timestamp")
    private Instant makeTime;

    @Column(name = "ho_assign_time")
    private Instant hoAssignTime;

    @Column(name = "ho_action_time")
    private Instant hoActionTime;

    @Column(name = "status", columnDefinition = "int default 0")
    private int status;

    @OneToMany(mappedBy = "imageMake", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Image> images=new ArrayList<>();

    @OneToOne(mappedBy = "makeId", cascade = CascadeType.ALL,  orphanRemoval = true, optional = true)
    private BackReason backReason;
}
