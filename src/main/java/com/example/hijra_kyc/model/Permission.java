package com.example.hijra_kyc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    private String permissionCategory;
    private String permissionDisplayName;
    private String permissionName;
    private String recordStatus;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    // ✅ Add this manually
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;
        Permission p = (Permission) o;
        return permissionId != null && permissionId.equals(p.permissionId);
    }

    @Override
    public int hashCode() {
        return permissionId != null ? permissionId.hashCode() : 0;
    }
}
