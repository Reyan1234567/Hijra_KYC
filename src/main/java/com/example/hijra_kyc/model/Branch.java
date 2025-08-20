package com.example.hijra_kyc.model;

import jakarta.persistence.*;
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
    @Column(unique = true)
    private String name;

    @NotBlank(message = "branch Code is required")
    @Column(unique = true)
    private String branchCode;

    @NotNull(message = "District is required")
    @ManyToOne
    @JoinColumn(name = "district", referencedColumnName = "districtId")
    private District id;
    @Column(nullable = false)
    private Integer status; // 1 = active, 0 = inactive

    @NotBlank(message = "branch phone is required")
    private String phone;

}
