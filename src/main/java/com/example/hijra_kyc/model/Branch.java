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
  
    @NotNull(message="District Name is required")
    @ManyToOne
    @JoinColumn(name = "district_Name", referencedColumnName = "districtName ")
    private District districtName;
    @NotNull(message="District Code is required")
    @ManyToOne
    @JoinColumn(name = "district_Code", referencedColumnName = "districtCode ")
    private District districtCode;

    @Column(columnDefinition = "int default 1")
    private Integer status; // 1 = active, 0 = inactive

    @NotBlank(message = "branch phone is required")
    private String phone;
}
