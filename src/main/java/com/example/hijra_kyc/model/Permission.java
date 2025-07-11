package com.example.hijra_kyc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @Column(name = "permission_id", length = 10)
    private Long permissionId;  

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_category")
    private String permissionCategory;

    @Column(name = "permission_display_name")
    private String permissionDisplayName;

    @Column(name = "record_status")
    private String recordStatus;
}
