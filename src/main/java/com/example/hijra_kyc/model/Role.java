package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id", length = 10)
    private String roleId;  // e.g., "R001"

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "record_status")
    private String recordStatus;
}
