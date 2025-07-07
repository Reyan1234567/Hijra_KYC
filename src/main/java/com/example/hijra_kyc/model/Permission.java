package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @Column(name = "permission_id", length = 10)
    private String permissionId;  // e.g., "P001"

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_category")
    private String permissionCategory;

    @Column(name = "permission_display_name")
    private String permissionDisplayName;

    @Column(name = "record_status")
    private String recordStatus;
}
