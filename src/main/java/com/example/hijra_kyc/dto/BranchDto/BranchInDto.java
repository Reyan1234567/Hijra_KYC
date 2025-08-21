package com.example.hijra_kyc.dto.BranchDto;

import lombok.Data;

@Data
public class BranchInDto {
    private String name;
    private String branchCode;
    private Long districtId ;
    private Integer status;
    private String phone;
}

