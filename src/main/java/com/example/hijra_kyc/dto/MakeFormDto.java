package com.example.hijra_kyc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
public class MakeFormDto {
    private Integer makerId;
    private String cif;
    private String customerAccount;
    private String customerPhone;
    private String customerName;
}


