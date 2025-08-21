package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "record_status")
    private Integer recordStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission", // join table
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
