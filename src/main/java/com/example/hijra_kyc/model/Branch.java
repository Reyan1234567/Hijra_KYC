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
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    @NotBlank(message = "name is required")
    @Column(unique=true)
    private String name;
    @NotBlank(message = "branch Code is required")
    private String branchCode;
    @NotNull(message="District Code is required")
    @ManyToOne
    @JoinColumn(name = "district_code", referencedColumnName = "id")
    private District districtCode;
    
}
